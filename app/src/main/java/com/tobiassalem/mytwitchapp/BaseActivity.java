package com.tobiassalem.mytwitchapp;

import android.app.Activity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tobiassalem.mytwitchapp.rest.TwitchApi;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Abstract base activity for all shared data and logic.
 * If we want to use Material Design with DrawerLayout, we need to extend AppCompatActivity (instead of Activity).
 *
 * @author Tobias
 */
public abstract class BaseActivity extends Activity {

    public static final String KEY_GAME_TITLE = "gameTitle";

    protected int getLimitNrOfGames() {
        return getResources().getInteger(R.integer.defaultTopGamesLimit);
    }

    protected int getLimitNrOfStreams() {
        return getResources().getInteger(R.integer.defaultTopStreamsLimit);
    }

    protected TwitchApi buildApiService() {
        OkHttpClient httpClient = buildHttpClientWithCliendIdHeader(getResources().getString(R.string.clientId));

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

    protected void notifyNotYetImplemented() {
        Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show();
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
