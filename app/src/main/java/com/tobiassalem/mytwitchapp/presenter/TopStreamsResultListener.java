package com.tobiassalem.mytwitchapp.presenter;

import com.tobiassalem.mytwitchapp.model.stream.TopStreamsResultModel;


/**
 * Interface to be implemented by classes listening to top stream results.
 * Normally with the Android MVP design, this is the Presenter.
 *
 * @author Tobias
 */
public interface TopStreamsResultListener {

    void onStreamResultSuccess(TopStreamsResultModel resultModel);

    void onStreamResultError();
}
