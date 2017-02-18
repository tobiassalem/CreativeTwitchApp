package com.tobiassalem.mytwitchapp.presenter;

import android.util.Log;

import com.tobiassalem.mytwitchapp.rest.TwitchAPIInteractor;
import com.tobiassalem.mytwitchapp.model.game.TopGame;
import com.tobiassalem.mytwitchapp.model.game.TopGamesResultModel;
import com.tobiassalem.mytwitchapp.model.stream.Stream;
import com.tobiassalem.mytwitchapp.model.stream.TopStreamsResultModel;
import com.tobiassalem.mytwitchapp.view.TopStreamsView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The Android MVP Presenter implementation for Top Streams.
 *
 * Strictly speaking this could also be split with an interface and an implementation.
 * However, for this simple app it would be overkill.
 *
 * @author Tobias
 */
public class TopStreamsPresenter implements TopStreamsResultListener {

    private final Logger logger = LoggerFactory.getLogger(TopGamesPresenter.class);
    private final TopStreamsView view;
    private final TwitchAPIInteractor interactor;

    public TopStreamsPresenter(TopStreamsView view, TwitchAPIInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void loadTopStreams(final String gameTitle) {
        interactor.getTopStreams(gameTitle, this);
    }

    /* ========================== [Callback methods] ======================================= */

    @Override
    public void onStreamResultSuccess(TopStreamsResultModel resultModel) {
        if (resultModel != null && resultModel.getStreams() != null && resultModel.getStreams().size() > 0) {
            List<Stream> streams = resultModel.getStreams();
            logResultModel(resultModel);
            view.setTopStreams(streams);

        } else {
            view.onStreamResultMissing();
            logger.error("resultModel is missing data: " +resultModel);
        }
    }

    @Override
    public void onStreamResultError() {
        view.onStreamResultError();
    }

    /* ============================ [Private help methods] ========================================= */


    private void logResultModel(TopGamesResultModel resultModel) {
        List<TopGame> topGames = resultModel.getTopGames();
        String modelInfo = "resultModel.total: " + resultModel.getTotal() + ", topGames.size: " + topGames.size()+ ", links: " +resultModel.getLinks();
        logger.info(modelInfo);
    }

    private void logResultModel(TopStreamsResultModel resultModel) {
        List<Stream> streams = resultModel.getStreams();
        String modelInfo = "resultModel.total: " + resultModel.getTotal() + ", streams.size: " + streams.size()+ ", links: " +resultModel.getLinks();
        logger.info(modelInfo);
    }

}
