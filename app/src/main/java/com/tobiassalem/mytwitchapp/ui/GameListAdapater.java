package com.tobiassalem.mytwitchapp.ui;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tobiassalem.mytwitchapp.R;
import com.tobiassalem.mytwitchapp.model.game.TopGame;
import com.tobiassalem.mytwitchapp.model.stream.Stream;

import java.text.DecimalFormat;
import java.util.List;

/**
 * List adapter for managing TopGame objects
 *
 * @author Tobias
 */
public class GameListAdapater extends BaseAdapter<TopGame> {

    private static final String TAG = GameListAdapater.class.getSimpleName();
    private static final int LAYOUT_ID = R.layout.game_list_item;
    private static final int VIEWHOLDER_UNIQUE_KEY = LAYOUT_ID;

    private Context context;
    private List<TopGame> topGames;

    public GameListAdapater(Context context, List<TopGame> topGames) {
        super(context, -1, topGames);
        this.context = context;
        this.topGames = topGames;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            rowView = inflater.inflate(LAYOUT_ID, parent, false);
            holder.logoWebView = (WebView) rowView.findViewById(R.id.gameLogoWeb);
            holder.title = (TextView) rowView.findViewById(R.id.gameTitle);
            holder.viewerCount = (TextView) rowView.findViewById(R.id.gameViewerCount);
            optimizeWebView(holder.logoWebView);

            rowView.setTag(VIEWHOLDER_UNIQUE_KEY,holder);
        } else {
            rowView = convertView;
            holder = (ViewHolder) rowView.getTag(VIEWHOLDER_UNIQUE_KEY);
        }

        TopGame topGame = topGames.get(position);
        populateView(holder,topGame);
        return rowView;
    }

    private void populateView(ViewHolder holder, TopGame topGame) {
        logViewHolder(holder);
        logGameData(topGame);

        String viewerCount = this.getContext().getString(R.string.prompt_viewer_count) + " " + String.valueOf(topGame.getViewers());
        holder.title.setText(topGame.getGame().getName());
        holder.viewerCount.setText(viewerCount);

        if (hasPreviewImage(topGame)) {
            holder.logoWebView.loadData(ImageHelper.getImageSourceInHtml(topGame.getGame().getLogo().getSmall()), ImageHelper.MIMETYPE_HTML, ImageHelper.ENCODING_UTF8);
        }
    }

    private boolean hasPreviewImage(final TopGame topGame) {
        return topGame != null && topGame.getGame() != null && topGame.getGame().getLogo() != null && topGame.getGame().getLogo().getSmall() != null;
    }

    private void logGameData(final TopGame topGame) {
        if (topGame != null && topGame.getGame() != null) {
            Log.i(TAG, "topGame: " + topGame + ", topGame.viewers: " + topGame.getViewers() + ", topGame.game.name: " + topGame.getGame().getName() + ", topGame.game.logo: " + topGame.getGame().getLogo().getLarge());
        } else  {
            Log.e(TAG, "topGame is null");
        }
    }

    private void logViewHolder(final ViewHolder holder) {
        Log.i(TAG, "holder.title: " +holder.title+ ", holder.gameViewerCount: " +holder.viewerCount);
    }

}
