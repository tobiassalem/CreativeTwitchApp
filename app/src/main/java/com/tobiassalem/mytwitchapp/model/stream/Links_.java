package com.tobiassalem.mytwitchapp.model.stream;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Links_ {

    private String self;
    private String follows;
    private String commercial;
    private String streamKey;
    private String chat;
    private String subscriptions;
    private String editors;
    private String teams;
    private String videos;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The self
     */
    public String getSelf() {
        return self;
    }

    /**
     *
     * @param self
     * The self
     */
    public void setSelf(String self) {
        this.self = self;
    }

    /**
     *
     * @return
     * The follows
     */
    public String getFollows() {
        return follows;
    }

    /**
     *
     * @param follows
     * The follows
     */
    public void setFollows(String follows) {
        this.follows = follows;
    }

    /**
     *
     * @return
     * The commercial
     */
    public String getCommercial() {
        return commercial;
    }

    /**
     *
     * @param commercial
     * The commercial
     */
    public void setCommercial(String commercial) {
        this.commercial = commercial;
    }

    /**
     *
     * @return
     * The streamKey
     */
    public String getStreamKey() {
        return streamKey;
    }

    /**
     *
     * @param streamKey
     * The stream_key
     */
    public void setStreamKey(String streamKey) {
        this.streamKey = streamKey;
    }

    /**
     *
     * @return
     * The chat
     */
    public String getChat() {
        return chat;
    }

    /**
     *
     * @param chat
     * The chat
     */
    public void setChat(String chat) {
        this.chat = chat;
    }

    /**
     *
     * @return
     * The subscriptions
     */
    public String getSubscriptions() {
        return subscriptions;
    }

    /**
     *
     * @param subscriptions
     * The subscriptions
     */
    public void setSubscriptions(String subscriptions) {
        this.subscriptions = subscriptions;
    }

    /**
     *
     * @return
     * The editors
     */
    public String getEditors() {
        return editors;
    }

    /**
     *
     * @param editors
     * The editors
     */
    public void setEditors(String editors) {
        this.editors = editors;
    }

    /**
     *
     * @return
     * The teams
     */
    public String getTeams() {
        return teams;
    }

    /**
     *
     * @param teams
     * The teams
     */
    public void setTeams(String teams) {
        this.teams = teams;
    }

    /**
     *
     * @return
     * The videos
     */
    public String getVideos() {
        return videos;
    }

    /**
     *
     * @param videos
     * The videos
     */
    public void setVideos(String videos) {
        this.videos = videos;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
