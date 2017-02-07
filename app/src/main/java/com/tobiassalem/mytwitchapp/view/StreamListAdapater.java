package com.tobiassalem.mytwitchapp.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tobiassalem.mytwitchapp.R;
import com.tobiassalem.mytwitchapp.model.stream.Stream;

import java.util.List;

/**
 * List adapter for managing Stream objects
 *
 * @author Tobias
 */
public class StreamListAdapater extends BaseAdapter<Stream> {

    private static final String TAG = StreamListAdapater.class.getSimpleName();
    private static final int LAYOUT_ID = R.layout.stream_list_item;
    private static final int VIEWHOLDER_UNIQUE_KEY = LAYOUT_ID;

    private Context context;
    private List<Stream> streams;

    public StreamListAdapater(Context context, List<Stream> topGames) {
        super(context, -1, topGames);
        this.context = context;
        this.streams = topGames;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            rowView = inflater.inflate(LAYOUT_ID, parent, false);
            holder.logo = (ImageView) rowView.findViewById(R.id.streamLogo);
            holder.title = (TextView) rowView.findViewById(R.id.streamTitle);
            holder.viewerCount = (TextView) rowView.findViewById(R.id.streamViewerCount);

            rowView.setTag(VIEWHOLDER_UNIQUE_KEY,holder);
        } else {
            rowView = convertView;
            holder = (ViewHolder) rowView.getTag(VIEWHOLDER_UNIQUE_KEY);
        }

        Stream stream = streams.get(position);
        populateView(holder,stream);
        return rowView;
    }


    private void populateView(ViewHolder holder, Stream stream) {
        logViewHolder(holder);
        logStreamData(stream);
        String viewerCount = this.getContext().getString(R.string.prompt_viewer_count) + " " + String.valueOf(stream.getViewers());
        holder.title.setText(stream.getChannel().getDisplayName());
        holder.viewerCount.setText(viewerCount);

        if (hasPreviewImage(stream)) {
            String logoUrl = stream.getPreview().getSmall();
            Glide.with(holder.logo.getContext())
                    .load(ImageHelper.getUriFromUrl(holder.logo.getContext(), logoUrl, android.R.drawable.star_off))
                    .fitCenter()
                    .into(holder.logo);
        }
    }

    private boolean hasPreviewImage(final Stream stream) {
        return stream != null && stream.getPreview() != null && stream.getPreview().getSmall() != null;
    }

    private void logStreamData(final Stream stream) {
        if (stream != null) {
            Log.i(TAG, "stream: " + stream + ", stream.viewers: " + stream.getViewers() + ", stream.gameTitle: " + stream.getGame());
            if (stream.getChannel() != null) {
                Log.i(TAG, "channel.game: " +stream.getChannel().getGame() + ", channel.displayName: " +stream.getChannel().getDisplayName());
            }
            if (stream.getPreview() != null) {
                Log.i(TAG, "preview.small: " +stream.getPreview().getSmall());
            }
        } else  {
            Log.e(TAG, "Stream object is null");
        }
    }

    private void logViewHolder(final ViewHolder holder) {
        Log.i(TAG, "holder.title: " +holder.title+ ", holder.viewerCount: " +holder.viewerCount);
    }

}
