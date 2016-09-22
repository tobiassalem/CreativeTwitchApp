package com.tobiassalem.mytwitchapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tobiassalem.mytwitchapp.BaseFragment;
import com.tobiassalem.mytwitchapp.R;
import com.tobiassalem.mytwitchapp.TopGamesResultListener;
import com.tobiassalem.mytwitchapp.TopStreamsActivity;
import com.tobiassalem.mytwitchapp.model.game.TopGame;
import com.tobiassalem.mytwitchapp.model.game.TopGamesResultModel;
import com.tobiassalem.mytwitchapp.rest.TwitchApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameListFragment extends BaseFragment implements TopGamesResultListener {

    private static final String LOG_TAG = GameListFragment.class.getSimpleName();
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_game_list, container, false);
        initView();
        loadGameData();
        return recyclerView;
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new TopGamesAdapter(new ArrayList<TopGame>()));
    }

    private void loadGameData() {

        TwitchApi apiService = buildApiService();
        Call<TopGamesResultModel> call = apiService.getTopGames(getLimitNrOfGames());
        call.enqueue(new Callback<TopGamesResultModel>() {
            @Override
            public void onResponse(Call<TopGamesResultModel> call, Response<TopGamesResultModel> response) {
                //logResponse(response);
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

            // RecyclerView version
            recyclerView.removeAllViews();
            ((TopGamesAdapter) recyclerView.getAdapter()).updateData(topGames);

        } else {
            Log.e(LOG_TAG, "resultModel: " +resultModel);
            Toast.makeText(getActivity(), getString(R.string.error_message_data_default), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGameResultError() {
        Toast.makeText(getActivity(), getString(R.string.error_message_backend_default), Toast.LENGTH_SHORT).show();
    }

    public static class TopGamesAdapter extends RecyclerView.Adapter<TopGamesAdapter.ViewHolder> {

        private List<TopGame> topGames;

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final WebView gameLogoWeb;
            public final ImageView gameLogo;
            public final TextView gameTitle;
            public final TextView gameViewerCount;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                gameLogoWeb = (WebView) view.findViewById(R.id.gameLogoWeb);
                gameLogo = (ImageView) view.findViewById(R.id.gameLogo);
                gameTitle = (TextView) view.findViewById(R.id.gameTitle);
                gameViewerCount = (TextView) view.findViewById(R.id.gameViewerCount);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + gameTitle.getText();
            }
        }

        public TopGame getValueAt(int position) {
            return topGames.get(position);
        }

        public TopGamesAdapter(List<TopGame> topGames) {
            this.topGames = topGames;
        }

        public void updateData(List<TopGame> viewModels) {
            topGames.clear();
            topGames.addAll(viewModels);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.game_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final TopGame topGame = topGames.get(position);
            final String gameTitle = topGame.getGame().getName();
            final String gamePreviewUrl = topGame.getGame().getLogo().getSmall();
            final String viewerCount = holder.mView.getContext().getString(R.string.prompt_viewer_count) + " " + String.valueOf(topGame.getViewers());

            //holder.gameLogoWeb.loadUrl(gamePreviewUrl);
            holder.gameLogoWeb.setVisibility(View.GONE);
            holder.gameTitle.setText(gameTitle);
            holder.gameViewerCount.setText(viewerCount);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, TopStreamsActivity.class);
                    intent.putExtra(TopStreamsActivity.KEY_GAME_TITLE, gameTitle);

                    context.startActivity(intent);
                }
            });

            Glide.with(holder.gameLogo.getContext())
                    .load(ImageHelper.getUriFromUrl(holder.mView.getContext(), gamePreviewUrl, android.R.drawable.star_off))
                    .fitCenter()
                    .into(holder.gameLogo);
        }

        @Override
        public int getItemCount() {
            return topGames.size();
        }
    }

    private void logResultModel(TopGamesResultModel resultModel) {
        List<TopGame> topGames = resultModel.getTopGames();
        String modelInfo = "resultModel.total: " + resultModel.getTotal() + ", topGames.size: " + topGames.size()+ ", links: " +resultModel.getLinks();
        Log.i(LOG_TAG, modelInfo);
    }    

}