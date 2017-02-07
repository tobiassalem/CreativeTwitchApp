package com.tobiassalem.mytwitchapp.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tobiassalem.mytwitchapp.model.game.TopGamesResultModel;
import com.tobiassalem.mytwitchapp.model.stream.TopStreamsResultModel;
import com.tobiassalem.mytwitchapp.presenter.TopGamesResultListener;
import com.tobiassalem.mytwitchapp.presenter.TopStreamsResultListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Interactor for the Twitch API
 *
 * @author Tobias
 */
public class TwitchAPIInteractorImpl implements TwitchAPIInteractor {

    private static final String LOG_TAG = TwitchAPIInteractorImpl.class.getSimpleName();

    private TwitchAPIConfig config;

    public TwitchAPIInteractorImpl(TwitchAPIConfig config) {
        this.config = config;
    }

    @Override
    public void getTopGames(final TopGamesResultListener listener) {
        TwitchApi apiService = buildApiService();
        Call<TopGamesResultModel> call = apiService.getTopGames(config.getLimitNrOfGames());
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

    protected TwitchApi buildApiService() {
        OkHttpClient httpClient = buildHttpClientWithCliendIdHeader(config.getClientId());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        String baseURL = config.getServerBaseUrl();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(TwitchApi.class);
    }

    private OkHttpClient buildHttpClientWithCliendIdHeader(final String clientId) {
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept (Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().addHeader("Client-id", clientId).build();
                        return chain.proceed(request);
                    }
                }).build();
        return httpClient;
    }

    /* ========================== [Private help methods] ======================================= */

    private void logTopGamesResponse(retrofit2.Response<TopGamesResultModel> response) {
        Log.i(LOG_TAG, "---> Top Games response: " +response.toString() + ", code: " +response.code()+ ", response.body: " +response.body()+ ", response.message: " +response.message()+ ", errorBody: " +response.errorBody()+
                ", isSuccessful: " +response.isSuccessful() + ", response.raw: " +response.raw()+ ", " +response.raw());
    }

    private void logTopStreamsResponse(retrofit2.Response<TopStreamsResultModel> response) {
        Log.i(LOG_TAG, "---> Top Streams response: " +response.toString() + ", code: " +response.code()+ ", response.body: " +response.body()+ ", response.message: " +response.message()+ ", errorBody: " +response.errorBody()+
                ", isSuccessful: " +response.isSuccessful() + ", response.raw: " +response.raw()+ ", " +response.raw());
    }
}
