package com.tobiassalem.mytwitchapp.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
        Log.d(TAG, "getDrawableFromUrl: " +url);

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

    /**
     * Get an Uri object from the given Url path. Typically to be used by an image loading library.
     * The path is already encoded, for example:
     * https://static-cdn.jtvnw.net/ttv-logoart/Dota%202-60x36.jpg
     *
     * Note that the appendPath method encodes the slashes in the path to %2F.
     * Since the path is already encoded, we should use appendEncodedPath.
     *
     * @param encodedUrlPath
     * @return Uri object
     * @throws MalformedURLException
     */
    public static Uri getUriFromUrl(final String encodedUrlPath) throws MalformedURLException {

        URL url = new URL(encodedUrlPath);
        Uri.Builder builder =  new Uri.Builder()
                .scheme(url.getProtocol())
                .authority(url.getAuthority())
                .appendEncodedPath(encodedUrlPath);

        Uri uri = builder.build();
        Log.d(TAG, "---> getUriFromUrl: " +encodedUrlPath+ ", Uri: " +uri+ ", url.getProtocol(): " +url.getProtocol()+ ", url.getAuthority(): " +url.getAuthority()+ ", url.getPath(): " +url.getPath());

        return uri;
    }

    public static Uri getUriFromUrl(@NonNull final Context context, final String thisUrl, @AnyRes int backupDrawableId)  {
        try {
            return getUriFromUrl(thisUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "getUriFromUrl caused exception " +e);
            return getUriToDrawable(context, backupDrawableId);
        }
    }

    public static final Uri getUriToDrawable(@NonNull Context context, @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }
}
