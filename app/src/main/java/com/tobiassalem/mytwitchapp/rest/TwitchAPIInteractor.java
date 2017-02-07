package com.tobiassalem.mytwitchapp.rest;

import com.tobiassalem.mytwitchapp.presenter.TopGamesResultListener;
import com.tobiassalem.mytwitchapp.presenter.TopStreamsResultListener;

/**
 * @author Tobias
 *         Created on 2017-02-03.
 *         Project MyTwitchApp
 */

public interface TwitchAPIInteractor {

    void getTopGames(final TopGamesResultListener listener);

    void getTopStreams(final String gameTitle, final TopStreamsResultListener listener);
}
