package com.tobiassalem.mytwitchapp;

import com.tobiassalem.mytwitchapp.model.stream.TopStreamsResultModel;


/**
 * Interface to be implmented by classes listening to top stream results
 *
 * @author Tobias
 */
public interface TopStreamsResultListener {

    void onStreamResultSuccess(TopStreamsResultModel resultModel);

    void onStreamResultError();
}
