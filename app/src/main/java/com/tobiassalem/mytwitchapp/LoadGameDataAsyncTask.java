package com.tobiassalem.mytwitchapp;

import android.os.AsyncTask;

import com.tobiassalem.mytwitchapp.model.game.TopGamesResultModel;
import com.tobiassalem.mytwitchapp.rest.TwitchApi;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * AsyncTask for loading game data from the Twitch API asynchronously.
 *
 * @author Tobias
 */
public class LoadGameDataAsyncTask extends AsyncTask<TwitchApi, Void, TopGamesResultModel> {

    private TwitchApi apiService;
    private final TopGamesResultListener listener;

    public LoadGameDataAsyncTask(TwitchApi apiService, TopGamesResultListener listener) {
        this.apiService = apiService;
        this.listener = listener;
    }

    @Override
    protected TopGamesResultModel doInBackground(TwitchApi... params) {
        TopGamesResultModel resultModel = null;
        Call<TopGamesResultModel> call = apiService.getTopGames(10);
        try {
            Response<TopGamesResultModel> response = call.execute();
            resultModel = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: populate drawables from URL paths and cache them

        return resultModel;
    }

    protected void onPostExecute(TopGamesResultModel resultModel) {
        super.onPostExecute(resultModel);
        if (resultModel != null) {
            listener.onGameResultSuccess(resultModel);
        } else {
            listener.onGameResultError();
        }
    }
}
