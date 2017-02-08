package com.tobiassalem.mytwitchapp;

import com.tobiassalem.mytwitchapp.model.game.Game;
import com.tobiassalem.mytwitchapp.model.game.TopGame;
import com.tobiassalem.mytwitchapp.model.stream.Stream;
import com.tobiassalem.mytwitchapp.model.stream.TopStreamsResultModel;
import com.tobiassalem.mytwitchapp.presenter.TopGamesResultListener;
import com.tobiassalem.mytwitchapp.presenter.TopStreamsPresenter;
import com.tobiassalem.mytwitchapp.presenter.TopStreamsResultListener;
import com.tobiassalem.mytwitchapp.rest.TwitchAPIInteractor;
import com.tobiassalem.mytwitchapp.view.TopGamesView;
import com.tobiassalem.mytwitchapp.view.TopStreamsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Tobias
 */
@RunWith(MockitoJUnitRunner.class)
public class TopStreamsPresenterTest {

    private static final String GAME_TITLE = "Lord of the Rings Online";

    @Mock
    TopStreamsView view;
    @Mock
    TwitchAPIInteractor interactor;

    private TopStreamsPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new TopStreamsPresenter(view, interactor);
    }

    @Test
    public void checkInteractorGetTopStreamsIsCalledOnLoadTopStreams() {
        presenter.loadTopStreams(GAME_TITLE);
        verify(interactor, times(1)).getTopStreams(eq(GAME_TITLE), any(TopStreamsResultListener.class));
    }

    @Test
    public void checkViewOnGameResultErrorIsCalledOnGameResultError() {
        presenter.onStreamResultError();
        verify(view, times(1)).onStreamResultError();
    }

    @Test
    public void checkViewSetTopGamesIsCalledOnGameResultSuccess() {
        presenter.onStreamResultSuccess(buildResultModel());
        verify(view, times(1)).setTopStreams(anyList());
    }

    @Test
    public void checkResultModelItemsArePassedToView() {
        TopStreamsResultModel resultModel = buildResultModel();
        List<Stream> streams = resultModel.getStreams();

        presenter.onStreamResultSuccess(resultModel);
        verify(view, times(1)).setTopStreams(streams);
    }

    @Test
    public void checkViewOnGameResultMissingIsCalledOnGameResultSuccess() {
        presenter.onStreamResultSuccess(buildEmptyResultModel());
        verify(view, times(1)).onStreamResultMissing();
    }


    private TopStreamsResultModel buildEmptyResultModel() {
        return new TopStreamsResultModel();
    }

    private TopStreamsResultModel buildResultModel() {
        List<Stream> topStreams = new ArrayList<>();
        topStreams.add(buildTopStream());
        TopStreamsResultModel model = new TopStreamsResultModel();
        model.setStreams(topStreams);
        return model;
    }

    private Stream buildTopStream() {
        Stream stream = new Stream();
        stream.setGame("Lord of the Rings Online");
        stream.setAverageFps(new Integer(142));
        return stream;
    }

    private Game buildGame(String name) {
        Game game = new Game();
        game.setName(name);
        return game;
    }

}
