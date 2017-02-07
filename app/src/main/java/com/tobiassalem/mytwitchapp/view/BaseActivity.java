package com.tobiassalem.mytwitchapp.view;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
 * Abstract base activity for all shared data and logic.
 * When using Material Design with DrawerLayout, we need to extend AppCompatActivity (instead of Activity).
 *
 * @author Tobias
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String KEY_GAME_TITLE = "gameTitle";

    protected TwitchAPIConfig buildTwichAPIConfig() {
        String clientId = getResources().getString(R.string.clientId);
        String serverBaseUrl = getResources().getString(R.string.serverBaseUrl);
        int limitNrOfGames = getResources().getInteger(R.integer.defaultTopGamesLimit);
        int lmitNrOfStreams = getResources().getInteger(R.integer.defaultTopStreamsLimit);

        return new TwitchAPIConfig(clientId, serverBaseUrl, limitNrOfGames, lmitNrOfStreams);
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
