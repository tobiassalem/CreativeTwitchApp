package com.tobiassalem.mytwitchapp;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tobiassalem.mytwitchapp.model.stream.Stream;
import com.tobiassalem.mytwitchapp.model.stream.TopStreamsResultModel;
import com.tobiassalem.mytwitchapp.rest.TwitchApi;
import com.tobiassalem.mytwitchapp.ui.ImageHelper;
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

    private RecyclerView recyclerView;
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
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String dynamicTitle = String.format(getResources().getString(R.string.title_top_streams), gameTitle);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(dynamicTitle);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new TopStreamsAdapter(new ArrayList<Stream>()));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* ========================== [Callback methods] ======================================= */

    @Override
    public void onStreamResultSuccess(TopStreamsResultModel resultModel) {
        if (resultModel != null) {
            List<Stream> streams = resultModel.getStreams();
            logResultModel(resultModel);

            ((TopStreamsAdapter) recyclerView.getAdapter()).updateData(streams);

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

    private class TopStreamsAdapter extends RecyclerView.Adapter<TopStreamsAdapter.ViewHolder> {
        private List<Stream> streams;

        class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final ImageView streamLogo;
            public final TextView streamTitle;
            public final TextView streamViewerCount;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                streamLogo = (ImageView) view.findViewById(R.id.streamLogo);
                streamTitle = (TextView) view.findViewById(R.id.streamTitle);
                streamViewerCount = (TextView) view.findViewById(R.id.streamViewerCount);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + streamTitle.getText();
            }
        }

        public TopStreamsAdapter(ArrayList<Stream> streams) {
            this.streams = streams;
        }

        public void updateData(List<Stream> viewModels) {
            streams.clear();
            streams.addAll(viewModels);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.stream_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Stream stream = streams.get(position);
            final String streamTitle = stream.getChannel().getDisplayName();
            final String gamePreviewUrl = stream.getPreview().getSmall();
            String viewerCount = holder.mView.getContext().getString(R.string.prompt_viewer_count) + " " + String.valueOf(stream.getViewers());
            holder.streamTitle.setText(streamTitle);
            holder.streamViewerCount.setText(viewerCount);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyNotYetImplemented();
                }
            });

            Glide.with(holder.streamLogo.getContext())
                    .load(ImageHelper.getUriFromUrl(holder.mView.getContext(), gamePreviewUrl, android.R.drawable.star_off))
                    .fitCenter()
                    .into(holder.streamLogo);
        }

        @Override
        public int getItemCount() {
            return streams.size();
        }
    }
}
