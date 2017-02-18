package com.tobiassalem.mytwitchapp;

import android.content.Context;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Helper class for the awesome MockWebServer from com.squareup.okhttp.
 *
 * @author Tobias
 */
public class MockWebServerHelper {

    private MockWebServer mockWebServer;
    private URL url;

    public MockWebServerHelper(){
        mockWebServer = new MockWebServer();

    }

    public MockWebServer getServer(){
        return mockWebServer;
    }

    public void enqueueJsonResponse(Object responseBody) throws IOException {
        String response = new ObjectMapper().writeValueAsString(responseBody);
        enqueueJsonResponseString(response);
    }

    public void enqueueError() throws IOException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));
    }

    public void enqueueJsonResponseString(String responseBody) throws IOException {
        enqueResponse(new MockResponse().setBody(responseBody));
    }

    public void enqueueJsonResponseFile(Context context, String fileName, int responseDelay) throws IOException {
        MockResponse mockResponse = new MockResponse().setBody(readStringFromResource(context, fileName)).setBodyDelayTimeMs(responseDelay);
        enqueResponse(mockResponse);
    }

    private void enqueResponse(MockResponse mockResponse) {
        mockWebServer.enqueue(mockResponse.setHeader("content-type", "application/json"));
    }

    private static String readStringFromResource(Context context, final String fileName) throws IOException {
        InputStream inputStream = context.getAssets().open(fileName);
        String result = convertStreamToString(inputStream);
        inputStream.close();
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

    public void start() throws Exception {
        mockWebServer.play();
        url = mockWebServer.getUrl("");
    }

    public void shutDown() throws Exception {
        mockWebServer.shutdown();
    }

    public URL getUrl() {
        return url;
    }

}
