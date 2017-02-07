package com.tobiassalem.mytwitchapp.view;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Abstract base adapter for all shared data and logic.
 *
 * @author Tobias
 */
public abstract class BaseAdapter<T> extends ArrayAdapter<T> {

    public BaseAdapter(Context context, int resource) {
        super(context, resource);
    }

    public BaseAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
    }

    /**
     * Optimize the webView for performance, and also prevent it from stealing clicks
     * from the list items.
     *
     * @param webView
     */
    protected void optimizeWebView(WebView webView) {
        webView.setClickable(false);
        webView.setLongClickable(false);
        webView.setFocusable(false);
        webView.setFocusableInTouchMode(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    /**
     * Optimize ListView and layout inflation by implementing the simple ViewHolder design pattern
     */
    class ViewHolder {
        ImageView logo;
        TextView title;
        TextView viewerCount;
    }

}
