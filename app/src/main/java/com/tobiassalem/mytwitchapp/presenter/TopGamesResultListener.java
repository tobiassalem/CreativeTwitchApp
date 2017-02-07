package com.tobiassalem.mytwitchapp.presenter;

import com.tobiassalem.mytwitchapp.model.game.TopGamesResultModel;

/**
 * Interface to be implemented by classes listening to top game results.
 * Normally with the Android MVP design, this is the Presenter.
 *
 * @author Tobias
 */
public interface TopGamesResultListener {

    void onGameResultSuccess(TopGamesResultModel resultModel);

    void onGameResultError();
}
