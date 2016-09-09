package com.tobiassalem.mytwitchapp.model.stream;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Stream {

    private Integer id;
    private String game;
    private Integer viewers;
    private String createdAt;
    private Integer videoHeight;
    private Integer averageFps;
    private Integer delay;
    private Boolean isPlaylist;
    private Links links;
    private Preview preview;
    private Channel channel;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The _id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The game
     */
    public String getGame() {
        return game;
    }

    /**
     *
     * @param game
     * The game
     */
    public void setGame(String game) {
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
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The videoHeight
     */
    public Integer getVideoHeight() {
        return videoHeight;
    }

    /**
     *
     * @param videoHeight
     * The video_height
     */
    public void setVideoHeight(Integer videoHeight) {
        this.videoHeight = videoHeight;
    }

    /**
     *
     * @return
     * The averageFps
     */
    public Integer getAverageFps() {
        return averageFps;
    }

    /**
     *
     * @param averageFps
     * The average_fps
     */
    public void setAverageFps(Integer averageFps) {
        this.averageFps = averageFps;
    }

    /**
     *
     * @return
     * The delay
     */
    public Integer getDelay() {
        return delay;
    }

    /**
     *
     * @param delay
     * The delay
     */
    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    /**
     *
     * @return
     * The isPlaylist
     */
    public Boolean getIsPlaylist() {
        return isPlaylist;
    }

    /**
     *
     * @param isPlaylist
     * The is_playlist
     */
    public void setIsPlaylist(Boolean isPlaylist) {
        this.isPlaylist = isPlaylist;
    }

    /**
     *
     * @return
     * The links
     */
    public Links getLinks() {
        return links;
    }

    /**
     *
     * @param links
     * The _links
     */
    public void setLinks(Links links) {
        this.links = links;
    }

    /**
     *
     * @return
     * The preview
     */
    public Preview getPreview() {
        return preview;
    }

    /**
     *
     * @param preview
     * The preview
     */
    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    /**
     *
     * @return
     * The channel
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     *
     * @param channel
     * The channel
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}