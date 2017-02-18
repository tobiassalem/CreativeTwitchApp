package com.tobiassalem.mytwitchapp.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tobiassalem.mytwitchapp.model.game.TopGamesResultModel;
import com.tobiassalem.mytwitchapp.model.stream.TopStreamsResultModel;
import com.tobiassalem.mytwitchapp.presenter.TopGamesResultListener;
import com.tobiassalem.mytwitchapp.presenter.TopStreamsResultListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.framed.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Interactor implementation for the Twitch API
 *
 * @author Tobias
 */
public class TwitchAPIInteractorImpl implements TwitchAPIInteractor {

    private final Logger logger = LoggerFactory.getLogger(TwitchAPIInteractorImpl.class);
    private TwitchAPIConfig config;

    public TwitchAPIInteractorImpl(TwitchAPIConfig config) {
        this.config = config;
    }

    @Override
    public void getTopGames(final TopGamesResultListener listener) {
        TwitchApi apiService = buildApiService();
        Call<TopGamesResultModel> call = apiService.getTopGames(config.getLimitNrOfGames());
        logger.info("Making call for getTopGames with headers: " +getHeaderInfo(call));

        call.enqueue(new Callback<TopGamesResultModel>() {
            @Override
            public void onResponse(Call<TopGamesResultModel> call, retrofit2.Response<TopGamesResultModel> response) {
                logTopGamesResponse(response);
                TopGamesResultModel resultModel = response.body();
                listener.onGameResultSuccess(resultModel);
            }

            @Override
            public void onFailure(Call<TopGamesResultModel> call, Throwable t) {
                listener.onGameResultError();
            }
        });
    }

    @Override
    public void getTopStreams(final String gameTitle, final TopStreamsResultListener listener) {

        TwitchApi apiService = buildApiService();
        Call<TopStreamsResultModel> call = apiService.getTopStreamsForGame(gameTitle, config.getLimitNrOfStreams());
        logger.info("Making call for getTopStreams with headers: " +getHeaderInfo(call));

        call.enqueue(new Callback<TopStreamsResultModel>() {
            @Override
            public void onResponse(Call<TopStreamsResultModel> call, retrofit2.Response<TopStreamsResultModel> response) {
                logTopStreamsResponse(response);
                TopStreamsResultModel resultModel = response.body();
                listener.onStreamResultSuccess(resultModel);
            }

            @Override
            public void onFailure(Call<TopStreamsResultModel> call, Throwable t) {
                listener.onStreamResultError();
            }
        });
    }

    private TwitchApi buildApiService() {
        OkHttpClient httpClient = buildHttpClientWithNeededHeaders(config.getClientId(), config.getJsonVersion());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.getServerBaseUrl())
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(TwitchApi.class);
    }

    /**
     * Builds our OKHttpClient with the needed headers for the Twitch API.
     * 1) Client-id: for the Twitch registration
     * 2) Accept: application/vnd.twitchtv[.version]+json
     *
     * @param clientId
     * @param jsonVersion
     * @return
     */
    private OkHttpClient buildHttpClientWithNeededHeaders(final String clientId, final String jsonVersion) {
        final String mimeType = "application/vnd.twitchtv." +jsonVersion+ "+json";
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept (Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("Client-id", clientId)
                                .addHeader("Accept", mimeType)
                                .build();
                        return chain.proceed(request);
                    }
                }).build();
        return httpClient;
    }

    /* ========================== [Private help methods] ======================================= */

    private void logTopGamesResponse(retrofit2.Response<TopGamesResultModel> response) {
        logger.info("---> Top Games response: " +response.toString() + ", code: " +response.code()+ ", response.body: " +response.body()+ ", response.message: " +response.message()+ ", errorBody: " +response.errorBody()+
                ", isSuccessful: " +response.isSuccessful() + ", response.raw: " +response.raw()+ ", " +response.raw());
    }

    private void logTopStreamsResponse(retrofit2.Response<TopStreamsResultModel> response) {
        logger.info("---> Top Streams response: " +response.toString() + ", code: " +response.code()+ ", response.body: " +response.body()+ ", response.message: " +response.message()+ ", errorBody: " +response.errorBody()+
                ", isSuccessful: " +response.isSuccessful() + ", response.raw: " +response.raw()+ ", " +response.raw());
    }

    private String getHeaderInfo(final Call call) {
        Headers headers = call.request().headers();
        StringBuffer sb = new StringBuffer("headers.size is: " +headers.size());

        for (int i = 0; i < headers.size(); i++) {
            sb.append(headers.name(i) + ": " + headers.value(i)+ ", ");
        }
        return sb.toString();
    }
}
