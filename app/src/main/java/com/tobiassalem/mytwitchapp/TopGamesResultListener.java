package com.tobiassalem.mytwitchapp;

import com.tobiassalem.mytwitchapp.model.game.TopGamesResultModel;

/**
 * Interface to be implemented by classes listening to top game results
 *
 * @author Tobias
 */
public interface TopGamesResultListener {

    void onGameResultSuccess(TopGamesResultModel resultModel);

    void onGameResultError();
}
