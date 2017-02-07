package com.tobiassalem.mytwitchapp.view;

import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tobiassalem.mytwitchapp.R;
import com.tobiassalem.mytwitchapp.rest.TwitchAPIConfig;
import com.tobiassalem.mytwitchapp.rest.TwitchApi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Abstract base fragment for all shared data and logic.
 *
 * @author Tobias
 */
public abstract class BaseFragment extends Fragment {


    protected TwitchAPIConfig buildTwichAPIConfig() {
        String clientId = getResources().getString(R.string.clientId);
        String serverBaseUrl = getResources().getString(R.string.serverBaseUrl);
        int limitNrOfGames = getResources().getInteger(R.integer.defaultTopGamesLimit);
        int lmitNrOfStreams = getResources().getInteger(R.integer.defaultTopStreamsLimit);

        return new TwitchAPIConfig(clientId, serverBaseUrl, limitNrOfGames, lmitNrOfStreams);
    }

    /**
     * Builds and returns the Twitch API representing the backend service.
     *
     * Example of using retrofit with custom converter and httpClient
     * Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(url).client(httpClient).build();
     *
     * @return TwitchAPI
     */
    protected TwitchApi buildApiService() {
        OkHttpClient httpClient = buildHttpClientWithCliendIdHeader(getContext().getString(R.string.clientId));

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        String baseURL = this.getString(R.string.serverBaseUrl);


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

}
