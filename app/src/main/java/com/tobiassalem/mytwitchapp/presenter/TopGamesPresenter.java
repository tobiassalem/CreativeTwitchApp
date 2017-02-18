package com.tobiassalem.mytwitchapp.presenter;

import com.tobiassalem.mytwitchapp.rest.TwitchAPIInteractor;
import com.tobiassalem.mytwitchapp.model.game.TopGame;
import com.tobiassalem.mytwitchapp.model.game.TopGamesResultModel;
import com.tobiassalem.mytwitchapp.view.TopGamesView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The Android MVP Presenter implementation for Top Games.
 *
 * Strictly speaking this could also be split with an interface and an implementation.
 * However, for this simple app it would be overkill.
 *
 * @author Tobias
 */
public class TopGamesPresenter implements TopGamesResultListener {

    private final Logger logger = LoggerFactory.getLogger(TopGamesPresenter.class);
    private final TopGamesView view;
    private final TwitchAPIInteractor interactor;

    public TopGamesPresenter(TopGamesView view, TwitchAPIInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void loadTopGames() {
        interactor.getTopGames(this);
    }

    @Override
    public void onGameResultSuccess(TopGamesResultModel resultModel) {
        if (resultModel != null && resultModel.getTopGames() != null && resultModel.getTopGames().size() > 0) {
            List<TopGame> topGames = resultModel.getTopGames();
            logResultModel(resultModel);
            view.setTopGames(topGames);

        } else {
            view.onGamesResultMissing();
            logger.error("resultModel is missing data: " +resultModel);
        }
    }

    @Override
    public void onGameResultError() {
        view.onGamesResultError();
    }

    private void logResultModel(TopGamesResultModel resultModel) {
        List<TopGame> topGames = resultModel.getTopGames();
        String modelInfo = "resultModel.total: " + resultModel.getTotal() + ", topGames.size: " + topGames.size()+ ", links: " +resultModel.getLinks();
        logger.info(modelInfo);
    }
}
