package com.tobiassalem.mytwitchapp;

import com.tobiassalem.mytwitchapp.model.game.Game;
import com.tobiassalem.mytwitchapp.model.game.TopGame;
import com.tobiassalem.mytwitchapp.model.game.TopGamesResultModel;
import com.tobiassalem.mytwitchapp.presenter.TopGamesPresenter;
import com.tobiassalem.mytwitchapp.presenter.TopGamesResultListener;
import com.tobiassalem.mytwitchapp.rest.TwitchAPIInteractor;
import com.tobiassalem.mytwitchapp.view.TopGamesView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Tobias
 */
@RunWith(MockitoJUnitRunner.class)
public class TopGamesPresenterTest {

    @Mock
    TopGamesView view;
    @Mock
    TwitchAPIInteractor interactor;

    private TopGamesPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new TopGamesPresenter(view, interactor);
    }

    @Test
    public void checkInteractorGetTopGamesIsCalledOnLoadTopGames() {
        presenter.loadTopGames();
        verify(interactor, times(1)).getTopGames(any(TopGamesResultListener.class));
    }

    @Test
    public void checkViewOnGameResultErrorIsCalledOnGameResultError() {
        presenter.onGameResultError();
        verify(view, times(1)).onGamesResultError();
    }

    @Test
    public void checkViewSetTopGamesIsCalledOnGameResultSuccess() {
        presenter.onGameResultSuccess(buildResultModel());
        verify(view, times(1)).setTopGames(anyList());
    }

    @Test
    public void checkResultModelItemsArePassedToView() {
        TopGamesResultModel resultModel = buildResultModel();
        List<TopGame> topGames = resultModel.getTopGames();

        presenter.onGameResultSuccess(resultModel);
        verify(view, times(1)).setTopGames(topGames);
    }

    @Test
    public void checkViewOnGameResultMissingIsCalledOnGameResultSuccess() {
        presenter.onGameResultSuccess(buildEmptyResultModel());
        verify(view, times(1)).onGamesResultMissing();
    }


    private TopGamesResultModel buildEmptyResultModel() {
        return new TopGamesResultModel();
    }

    private TopGamesResultModel buildResultModel() {
        List<TopGame> topGames = new ArrayList<>();
        topGames.add(buildTopGame());
        TopGamesResultModel model = new TopGamesResultModel();
        model.setTopGames(topGames);
        return model;
    }

    private TopGame buildTopGame() {
        TopGame topGame = new TopGame();
        topGame.setGame(buildGame("Lord of the Rings Online"));
        return topGame;
    }

    private Game buildGame(String name) {
        Game game = new Game();
        game.setName(name);
        return game;
    }

}
