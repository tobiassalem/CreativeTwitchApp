package com.tobiassalem.mytwitchapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tobiassalem.mytwitchapp.model.stream.Stream;
import com.tobiassalem.mytwitchapp.model.stream.TopStreamsResultModel;
import com.tobiassalem.mytwitchapp.rest.TwitchApi;
import com.tobiassalem.mytwitchapp.ui.StreamListAdapater;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity for displaying the current top streams for a given game from the Twitch API
 *
 * @author Tobias
 */
public class TopStreamsActivity extends BaseActivity implements TopStreamsResultListener {

    private static final String TAG = TopStreamsActivity.class.getSimpleName();

    private StreamListAdapater listAdapter;
    private ListView listView;
    private String gameTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readGameTitle();
        initView();
        loadStreamData();
    }

    private void readGameTitle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameTitle = extras.getString(KEY_GAME_TITLE);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_top_streams);

        String dynamicTitle = String.format(getResources().getString(R.string.title_top_streams), gameTitle);
        TextView titleView = (TextView) findViewById(R.id.streamTopListTitle);
        titleView.setText(dynamicTitle);

        listAdapter = new StreamListAdapater(this, new ArrayList<Stream>());
        listView = (ListView) findViewById(R.id.streamTopListView);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                notifyNotYetImplemented();
            }
        });
    }

    private void loadStreamData() {

        TwitchApi apiService = buildApiService();
        Call<TopStreamsResultModel> call = apiService.getTopStreamsForGame(gameTitle, getLimitNrOfStreams());
        call.enqueue(new Callback<TopStreamsResultModel>() {
            @Override
            public void onResponse(Call<TopStreamsResultModel> call, Response<TopStreamsResultModel> response) {
                logResponse(response);
                TopStreamsResultModel resultModel = response.body();
                onStreamResultSuccess(resultModel);
            }

            @Override
            public void onFailure(Call<TopStreamsResultModel> call, Throwable t) {
                onStreamResultError();
            }
        });
    }

    /* ========================== [Callback methods] ======================================= */

    @Override
    public void onStreamResultSuccess(TopStreamsResultModel resultModel) {
        if (resultModel != null) {
            List<Stream> streams = resultModel.getStreams();
            logResultModel(resultModel);

            listAdapter.clear();
            listAdapter.addAll(streams);
            listAdapter.notifyDataSetChanged();
            listView.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(TopStreamsActivity.this, getString(R.string.error_message_data_default), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStreamResultError() {
        Toast.makeText(TopStreamsActivity.this, getString(R.string.error_message_backend_default), Toast.LENGTH_SHORT).show();
    }

    /* ========================== [Private help methods] ======================================= */

    private void logResponse(Response<TopStreamsResultModel> response) {
        Log.i(TAG, "---> response: " +response.toString() + ", code: " +response.code()+ ", response.body: " +response.body()+ ", response.message: " +response.message()+ ", errorBody: " +response.errorBody()+
                ", isSuccessful: " +response.isSuccessful() + ", response.raw: " +response.raw()+ ", " +response.raw());
    }

    private void logResultModel(TopStreamsResultModel resultModel) {
        List<Stream> streams = resultModel.getStreams();
        String modelInfo = "resultModel.total: " + resultModel.getTotal() + ", streams.size: " + streams.size()+ ", links: " +resultModel.getLinks();
        Log.i(TAG, modelInfo);
    }
}
