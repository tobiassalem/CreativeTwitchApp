package com.tobiassalem.mytwitchapp;

import android.content.Intent;

import com.tobiassalem.mytwitchapp.view.BaseActivity;
import com.tobiassalem.mytwitchapp.view.TopStreamsActivity;

/**
 * Instrumentation test for the TopStreamsActivity.
 *
 * @author Tobias
 */
public class TopStreamsActivityTest extends AbstractActivityTest<TopStreamsActivity> {

    public TopStreamsActivityTest() {
        super(TopStreamsActivity.class);
    }

    public void testViewInformation() throws Exception {
        startActivityWithIntentAndResponseModel(buildGameTitleIntent(), buildTopStreamsResponseModel());

        // Stream information
        assertInView("teaminven");
        assertInView("LenaGol0vach");
        server.shutDown();
    }

    public void testTopGamesGeneratesOnlyOneRequest() throws Exception {
        startActivityWithIntentAndResponseModel(buildGameTitleIntent(), buildTopStreamsResponseModel());

        assertRequestCount(1);
        server.shutDown();
    }

    private Intent buildGameTitleIntent() {
        Intent intent = new Intent();
        intent.putExtra(BaseActivity.KEY_GAME_TITLE, "Hearthstone");
        return intent;
    }

}