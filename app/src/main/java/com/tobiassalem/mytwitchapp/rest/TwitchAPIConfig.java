package com.tobiassalem.mytwitchapp.rest;

/**
 * Class managing the configuration needed for interacting with the TwitchAPI.
 *
 * @author Tobias
 */
public class TwitchAPIConfig {

    private String clientId;
    private String serverBaseUrl;
    private int limitNrOfGames;
    private int limitNrOfStreams;

    public TwitchAPIConfig(String clientId, String serverBaseUrl, int limitNrOfGames, int limitNrOfStreams) {
        this.clientId = clientId;
        this.serverBaseUrl = serverBaseUrl;
        this.limitNrOfGames = limitNrOfGames;
        this.limitNrOfStreams = limitNrOfStreams;
    }

    public String getClientId() {
        return clientId;
    }

    public String getServerBaseUrl() {
        return serverBaseUrl;
    }

    public void setServerBaseUrl(String serverBaseUrl) {
        this.serverBaseUrl = serverBaseUrl;
    }

    public int getLimitNrOfGames() {
        return limitNrOfGames;
    }

    public void setLimitNrOfGames(int limitNrOfGames) {
        this.limitNrOfGames = limitNrOfGames;
    }

    public int getLimitNrOfStreams() {
        return limitNrOfStreams;
    }

    public void setLimitNrOfStreams(int limitNrOfStreams) {
        this.limitNrOfStreams = limitNrOfStreams;
    }

}
