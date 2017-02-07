package com.tobiassalem.mytwitchapp.view;

import com.tobiassalem.mytwitchapp.model.game.TopGame;

import java.util.List;

/**
 * Interface defining the view for Top Games.
 *
 * @author Tobias
 */
public interface TopGamesView {

    void setTopGames(List<TopGame> topGames);

    void onGamesResultError();

    void onGamesResultMissing();
}
