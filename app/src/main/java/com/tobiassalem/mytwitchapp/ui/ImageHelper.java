package com.tobiassalem.mytwitchapp.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Helper methods for image management.
 *
 * @author Tobias
 */
public class ImageHelper {

    public static final String MIMETYPE_HTML = "text/html";
    public static final String ENCODING_UTF8 = "UTF-8";

    private static final String TAG = ImageHelper.class.getSimpleName();

    /**
     * Loads a Drawable from the given URL path, typically referencing a jpg or bmp image.
     * NOTE that this method never should be called on the main thread, rather from an AsyncTask.
     *
     * @param res
     * @param url
     * @return
     * @throws IOException
     */
    public static Drawable getDrawableFromUrl(Resources res, String url) throws IOException {
        Bitmap bitmap;
        Log.i(TAG, "getDrawableFromUrl: " +url);

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        bitmap = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(res, bitmap);
    }

    /**
     * Get image source in html format. Typically used by a WebView.
     * Concrete loading of the image can then be done by calling
     * webView.loadData(imageSourceHtml, MIMETYPE_HTML, ENCODING_UTF8);
     *
     * WebView is already loading asynchronously, thus no need to place the call in an AsyncTask.
     *
     * @param urlForImage
     * @return
     */
    public static String getImageSourceInHtml(final String urlForImage) {
        String imageSourceInHtml = "<html><img src='" + urlForImage + "' /></html>";
        return imageSourceInHtml;
    }
}
