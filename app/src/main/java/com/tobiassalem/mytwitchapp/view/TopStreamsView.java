package com.tobiassalem.mytwitchapp.view;

import com.tobiassalem.mytwitchapp.model.game.TopGame;
import com.tobiassalem.mytwitchapp.model.stream.Stream;

import java.util.List;

/**
 * Interface defining the view for Top Streams, given a game.
 *
 * @author Tobias
 */
public interface TopStreamsView {

    void setTopStreams(List<Stream> topStreams);

    void onStreamResultError();

    void onStreamResultMissing();
}
