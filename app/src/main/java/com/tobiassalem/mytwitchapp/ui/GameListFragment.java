package com.tobiassalem.mytwitchapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tobiassalem.mytwitchapp.R;
import com.tobiassalem.mytwitchapp.TopStreamsActivity;
import com.tobiassalem.mytwitchapp.model.game.TopGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_game_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new TopGameRecyclerViewAdapter(getActivity(), new ArrayList<TopGame>()));
    }

    public static class TopGameRecyclerViewAdapter
            extends RecyclerView.Adapter<TopGameRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<TopGame> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            //public final ImageView gameIcon;
            public final TextView gameTitle;
            public final TextView gameViewerCount;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                //gameIcon = (ImageView) view.findViewById(R.id.gameLogo);
                gameTitle = (TextView) view.findViewById(R.id.gameTitle);
                gameViewerCount = (TextView) view.findViewById(R.id.gameViewerCount);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + gameTitle.getText();
            }
        }

        public TopGame getValueAt(int position) {
            return mValues.get(position);
        }

        public TopGameRecyclerViewAdapter(Context context, List<TopGame> topGames) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = topGames;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.game_list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final TopGame topGame = mValues.get(position);
            holder.mBoundString = topGame.getGame().getName();
            holder.gameTitle.setText(topGame.getGame().getName());
            holder.gameViewerCount.setText(topGame.getViewers());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, TopStreamsActivity.class);
                    intent.putExtra(TopStreamsActivity.KEY_GAME_TITLE, holder.mBoundString);

                    context.startActivity(intent);
                }
            });

            /*Glide.with(holder.gameIcon.getContext())
                    .load(android.R.drawable.star_big_on)
                    .fitCenter()
                    .into(holder.gameIcon);*/
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
