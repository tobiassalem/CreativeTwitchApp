package com.tobiassalem.mytwitchapp.model.game;

/**
 * Created by Tobias on 2016-09-06.
 */
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class TopGame {

    private Game game;
    private Integer viewers;
    private Integer channels;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The game
     */
    public Game getGame() {
        return game;
    }

    /**
     *
     * @param game
     * The game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     *
     * @return
     * The viewers
     */
    public Integer getViewers() {
        return viewers;
    }

    /**
     *
     * @param viewers
     * The viewers
     */
    public void setViewers(Integer viewers) {
        this.viewers = viewers;
    }

    /**
     *
     * @return
     * The channels
     */
    public Integer getChannels() {
        return channels;
    }

    /**
     *
     * @param channels
     * The channels
     */
    public void setChannels(Integer channels) {
        this.channels = channels;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}