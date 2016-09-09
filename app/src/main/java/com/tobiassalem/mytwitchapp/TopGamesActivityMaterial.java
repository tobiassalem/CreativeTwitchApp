package com.tobiassalem.mytwitchapp;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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

public class TopGamesActivityMaterial extends AppCompatActivity implements TopGamesResultListener {

    public static final String LOG_TAG = TopGamesActivityMaterial.class.getSimpleName();
    public static final String BASE_URL = "https://api.twitch.tv/";
    private ListView gameListView;
    private GameListAdapater gameListAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadGameData();
    }

    private void initView() {
        setContentView(R.layout.activity_top_games_material);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        gameListAdapater = new GameListAdapater(this,new ArrayList<TopGame>());
        gameListView = (ListView) findViewById(R.id.gameTopListView);
        gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                notifyNotYetImplemented();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gameListView.setNestedScrollingEnabled(true);
        }

    }

    private void loadGameData() {
        // TODO: call Twitch backend and populate listview on callback hook method
        // Trailing slash is needed for base URL
        Gson gson = new GsonBuilder()
                //.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create();


        String baseURL = this.getString(R.string.serverBaseUrl);
        Log.i(LOG_TAG, "Using baseURL = " +baseURL);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TwitchApi apiService = retrofit.create(TwitchApi.class);
        Call<TopGamesResultModel> call = apiService.getTopGames(10);
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

        /*RestRequest<BusinessOverviewResponse> request = RequestFactory
                .get(ContextPathRestBusiness.OVERVIEW, BusinessOverviewResponse.class);
        getRestClient().execute(request, R.string.loading,
                new RestRequestListener<BusinessOverviewResponse>() {
                    @Override
                    public void onSuccess(BusinessOverviewResponse businessOverviewResponse) {
                        accountResponse = businessOverviewResponse.getAccountResponse();
                        creditCardAccountResponse = businessOverviewResponse
                                .getCreditCardAccountResponse();
                        debitCardResponse = businessOverviewResponse.getDebitCardResponse();
                        fillValues();
                    }
                });
        */
    }

    private void logResponse(Response<TopGamesResultModel> response) {
        Log.i(LOG_TAG, "---> response: " +response.toString() + ", code: " +response.code()+ ", response.body: " +response.body()+ ", response.message: " +response.message()+ ", errorBody: " +response.errorBody()+
                ", isSuccessful: " +response.isSuccessful() + ", response.raw: " +response.raw()+ ", " +response.raw());
    }

    @Override
    public void onGameResultSuccess(TopGamesResultModel resultModel) {
        if (resultModel != null) {
            List<TopGame> topGames = resultModel.getTopGames();
            String modelInfo = "resultModel.total: " + resultModel.getTotal() + ", topGames.size: " + topGames.size()+ ", links: " +resultModel.getLinks();
            Log.i(LOG_TAG, modelInfo);
            // ISSUE: use adapter for list of TopGame or list of Game?
            gameListAdapater.clear();
            gameListAdapater.addAll(topGames);
            gameListAdapater.notifyDataSetChanged();
            gameListView.setVisibility(View.VISIBLE);
            Toast.makeText(TopGamesActivityMaterial.this, modelInfo, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(TopGamesActivityMaterial.this, "resultModel is null :(", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGameResultError() {
        Toast.makeText(TopGamesActivityMaterial.this, "Failed to call Twitch API", Toast.LENGTH_SHORT).show();
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

    private void notifyNotYetImplemented() {
        Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show();
    }
}
