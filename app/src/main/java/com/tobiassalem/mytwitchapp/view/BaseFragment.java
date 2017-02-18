package com.tobiassalem.mytwitchapp.view;

import android.support.v4.app.Fragment;

import com.tobiassalem.mytwitchapp.R;
import com.tobiassalem.mytwitchapp.rest.TwitchAPIConfig;

/**
 * Abstract base fragment for all shared data and logic.
 *
 * @author Tobias
 */
public abstract class BaseFragment extends Fragment {


    protected TwitchAPIConfig buildTwichAPIConfig() {
        String clientId = getResources().getString(R.string.clientId);
        String serverBaseUrl = getResources().getString(R.string.serverBaseUrl);
        String jsonVersion = getResources().getString(R.string.jsonVersion);
        int limitNrOfGames = getResources().getInteger(R.integer.defaultTopGamesLimit);
        int lmitNrOfStreams = getResources().getInteger(R.integer.defaultTopStreamsLimit);

        return new TwitchAPIConfig(clientId, serverBaseUrl, jsonVersion, limitNrOfGames, lmitNrOfStreams);
    }

}
