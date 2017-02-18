package com.tobiassalem.mytwitchapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import com.tobiassalem.mytwitchapp.rest.TwitchAPIConfig;

import java.io.IOException;


public abstract class AbstractActivityTest<T extends Activity> extends ActivityInstrumentationTestCase2<T> {

    protected final Class<T> typeParameterClass;

    protected MockWebServerHelper server;

    protected Solo solo;


    public AbstractActivityTest(Class<T> typeParameterClass) {
        super(typeParameterClass);
        this.typeParameterClass = typeParameterClass;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        server = new MockWebServerHelper();
        solo = new Solo(getInstrumentation());
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    protected void startActivityWithIntentAndResponseModel(final Intent intent, final String expectedCardResponseModel) throws Exception {
        enqueueServerResponseModel(expectedCardResponseModel);
        server.start();
        setActivityIntent(intent);

        TwitchAPIConfig.setServerBaseUrlTestMode(server.getUrl().toString());
        getActivity();
        solo.waitForDialogToClose(1000);
    }

    protected void startActivityWithResponseModel(final String expectedCardResponseModel) throws Exception {
        enqueueServerResponseModel(expectedCardResponseModel);

        server.start();
        Intent intent = new Intent();
        intent.putExtra("DEBUG_MODE", true);
        setActivityIntent(intent);

        TwitchAPIConfig.setServerBaseUrlTestMode(server.getUrl().toString());
        getActivity();
        solo.waitForDialogToClose(1000);
    }

    /**
     * Enqueue server response to the mock server.
     *
     * @param expectedResponseModel - the response model in JSON format
     * @throws IOException
     */
    private void enqueueServerResponseModel(final String expectedResponseModel) throws IOException {
        server.enqueueJsonResponseString(expectedResponseModel);
    }

    protected void assertRequestCount(final int expectedRequestCount) {
        assertEquals("Expected " +expectedRequestCount+ " nr of requests.", expectedRequestCount, server.getServer().getRequestCount());
    }

    protected RecordedRequest getRecordedRequest(int index) throws InterruptedException {
        MockWebServer mockServer = server.getServer();
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                return mockServer.takeRequest();
            }
            mockServer.takeRequest();
        }
        return null;
    }

    protected void assertInView(final String expected) {
        assertTrue(expected + " expected in view!", solo.searchText(expected, true));
    }

    protected void assertNotInView(final String expected) {
        assertFalse(expected + " NOT expected in view!", solo.searchText(expected, true));
    }


    protected String buildTopGamesResponseModel() {
        Context context = this.getInstrumentation().getContext();
        return ResponseModelFactory.buildJSONResponseTopGames(context);
    }

    protected String buildTopStreamsResponseModel() {
        Context context = this.getInstrumentation().getContext();
        return ResponseModelFactory.buildJSONResponseTopStreams(context);
    }

}