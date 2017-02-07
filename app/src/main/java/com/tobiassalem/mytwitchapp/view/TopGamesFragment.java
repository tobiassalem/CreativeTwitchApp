package com.tobiassalem.mytwitchapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tobiassalem.mytwitchapp.R;
import com.tobiassalem.mytwitchapp.presenter.TopGamesPresenter;
import com.tobiassalem.mytwitchapp.rest.TwitchAPIInteractorImpl;
import com.tobiassalem.mytwitchapp.model.game.TopGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Using Android MVP.
 * Fragment implementation of the TopGamesView.
 *
 * @author Tobias
 */
public class TopGamesFragment extends BaseFragment implements TopGamesView {

    private static final String LOG_TAG = TopGamesFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private TopGamesPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_game_list, container, false);
        initView();

        presenter = new TopGamesPresenter(this, new TwitchAPIInteractorImpl(buildTwichAPIConfig()));
        presenter.loadTopGames();
        return recyclerView;
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new TopGamesAdapter(new ArrayList<TopGame>()));
    }

    /* ========================== [View interface methods] ======================================= */

    @Override
    public void setTopGames(List<TopGame> topGames) {
        recyclerView.removeAllViews();
        ((TopGamesAdapter) recyclerView.getAdapter()).updateData(topGames);
    }

    public void onGamesResultError() {
        Toast.makeText(this.getContext(), getString(R.string.error_message_backend_default), Toast.LENGTH_SHORT).show();
    }

    public void onGamesResultMissing() {
        Toast.makeText(this.getContext(), getString(R.string.error_message_data_default), Toast.LENGTH_SHORT).show();
    }

    /* ========================== [View implementations] ======================================= */

    public static class TopGamesAdapter extends RecyclerView.Adapter<TopGamesAdapter.ViewHolder> {

        private List<TopGame> topGames;

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final ImageView gameLogo;
            public final TextView gameTitle;
            public final TextView gameViewerCount;

            public ViewHolder(View view) {
                super(view);
                mView = view;
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

}