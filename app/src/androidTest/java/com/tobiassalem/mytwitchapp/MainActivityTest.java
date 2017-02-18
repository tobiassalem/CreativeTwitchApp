package com.tobiassalem.mytwitchapp;

/**
 * Instrumentation test for the MainActivity.
 *
 * @author Tobias
 */
public class MainActivityTest extends AbstractActivityTest<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void testViewInformation() throws Exception {
        startActivityWithResponseModel(buildTopGamesResponseModel());

        // Game information
        assertInView("League of Legends");
        assertInView("Viewers:");
        assertInView("79420");

        assertInView("Hearthstone");
        assertInView("Viewers:");
        assertInView("37350");

        server.shutDown();
    }

    public void testTopGamesGeneratesOnlyOneRequest() throws Exception {
        startActivityWithResponseModel(buildTopGamesResponseModel());

        // Top Games should only generate one request
        assertRequestCount(1);
        server.shutDown();
    }

}