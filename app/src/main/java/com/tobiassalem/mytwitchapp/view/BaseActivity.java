package com.tobiassalem.mytwitchapp.view;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tobiassalem.mytwitchapp.R;
import com.tobiassalem.mytwitchapp.rest.TwitchAPIConfig;

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
        String jsonVersion = getResources().getString(R.string.serverBaseUrl);
        int limitNrOfGames = getResources().getInteger(R.integer.defaultTopGamesLimit);
        int lmitNrOfStreams = getResources().getInteger(R.integer.defaultTopStreamsLimit);

        return new TwitchAPIConfig(clientId, serverBaseUrl, jsonVersion, limitNrOfGames, lmitNrOfStreams);
    }

    protected void notifyNotYetImplemented() {
        Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show();
    }


}
