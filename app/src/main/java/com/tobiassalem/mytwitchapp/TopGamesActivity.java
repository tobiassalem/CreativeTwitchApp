package com.tobiassalem.mytwitchapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tobiassalem.mytwitchapp.model.game.TopGame;
import com.tobiassalem.mytwitchapp.model.game.TopGamesResultModel;
import com.tobiassalem.mytwitchapp.rest.TwitchApi;
import com.tobiassalem.mytwitchapp.ui.GameListAdapater;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Activity for displaying the current top games from the Twitch API
 *
 * @author Tobias
 */
public class TopGamesActivity extends BaseActivity implements TopGamesResultListener {

    public static final String LOG_TAG = TopGamesActivity.class.getSimpleName();

    private ListView gameListView;
    private GameListAdapater gameListAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadGameData();
    }

    private void initView() {
        setContentView(R.layout.activity_top_games);

        gameListAdapater = new GameListAdapater(this,new ArrayList<TopGame>());
        gameListView = (ListView) findViewById(R.id.gameTopListView);
        gameListView.setAdapter(gameListAdapater);
        gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewTopStreamsForGame(gameListAdapater.getItem(position));
            }


        });
    }

    private void viewTopStreamsForGame(TopGame topGame) {
        Intent intent = new Intent(TopGamesActivity.this, TopStreamsActivity.class);
        intent.putExtra(KEY_GAME_TITLE, topGame.getGame().getName());
        startActivity(intent);
    }

    private void loadGameData() {

        TwitchApi apiService = buildApiService();
        Call<TopGamesResultModel> call = apiService.getTopGames(LIMIT_NR_OF_GAMES);
        call.enqueue(new Callback<TopGamesResultModel>() {
            @Override
            public void onResponse(Call<TopGamesResultModel> call, Response<TopGamesResultModel> response) {
                logResponse(response);
                TopGamesResultModel resultModel = response.body();
                onGameResultSuccess(resultModel);
            }

            @Override
            public void onFailure(Call<TopGamesResultModel> call, Throwable t) {
                onGameResultError();
            }
        });
    }

    @Override
    public void onGameResultSuccess(TopGamesResultModel resultModel) {
        if (resultModel != null) {
            List<TopGame> topGames = resultModel.getTopGames();
            logResultModel(resultModel);

            gameListAdapater.clear();
            gameListAdapater.addAll(topGames);
            gameListAdapater.notifyDataSetChanged();
            gameListView.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(TopGamesActivity.this, getString(R.string.error_message_data_default), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGameResultError() {
        Toast.makeText(TopGamesActivity.this, getString(R.string.error_message_backend_default), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_twitch_top_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            notifyNotYetImplemented();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* ========================== [Private help methods] ======================================= */

    private void logResponse(Response<TopGamesResultModel> response) {
        Log.i(LOG_TAG, "---> response: " +response.toString() + ", code: " +response.code()+ ", response.body: " +response.body()+ ", response.message: " +response.message()+ ", errorBody: " +response.errorBody()+
                ", isSuccessful: " +response.isSuccessful() + ", response.raw: " +response.raw()+ ", " +response.raw());
    }

    private void logResultModel(TopGamesResultModel resultModel) {
        List<TopGame> topGames = resultModel.getTopGames();
        String modelInfo = "resultModel.total: " + resultModel.getTotal() + ", topGames.size: " + topGames.size()+ ", links: " +resultModel.getLinks();
        Log.i(LOG_TAG, modelInfo);
    }
}
