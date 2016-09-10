package com.tobiassalem.mytwitchapp;

import android.app.Activity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tobiassalem.mytwitchapp.rest.TwitchApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Abstract base activity for all shared data and logic.
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
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        String baseURL = this.getString(R.string.serverBaseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(TwitchApi.class);
    }

    protected void notifyNotYetImplemented() {
        Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show();
    }
}
