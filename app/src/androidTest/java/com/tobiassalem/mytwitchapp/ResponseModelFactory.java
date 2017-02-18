package com.tobiassalem.mytwitchapp;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Factory class for creating response models for testing purposes.
 *
 * @author Tobias
 */
class ResponseModelFactory {

    private static final String TAG = ResponseModelFactory.class.getSimpleName();

    /**
     * Reads and returns the String from the file with the given path. Native Java version
     *
     * @param filePath - file that should be on the Classpath
     * @return String read from the file
     */
    public static String readStringFromFile(String filePath) {
        String result = "";
        try {
            InputStream inputStream = ClassLoader.getSystemResourceAsStream(filePath);
            result = convertStreamToString(inputStream);
            inputStream.close();

        } catch (IOException e) {
            Log.e(TAG, "Failed to read string from file: " + filePath);
        }
        return result;
    }


    static String buildJSONResponseTopGames(Context context) {
        String jsonDataFilePath = "response_top_games.json";
        return readStringFromResource(context, jsonDataFilePath);
    }

    static String buildJSONResponseTopStreams(Context context) {
        String jsonDataFilePath = "response_top_streams.json";
        return readStringFromResource(context, jsonDataFilePath);
    }

    /**
     * Reads and returns the String from the file with the given name. Android version.
     *
     * @param context - the Android context to use
     * @param fileName - the fileName to use
     * @return String read from the file, e.g. a JSON response
     */
    private static String readStringFromResource(Context context, final String fileName) {
        String result = "";
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            result = convertStreamToString(inputStream);
            inputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "Failed to read string from resource: " + fileName);
        }
        return result;
    }

    private static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

}
