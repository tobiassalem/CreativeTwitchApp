package com.tobiassalem.mytwitchapp.rest;

/**
 * Class managing the configuration needed for interacting with the TwitchAPI.
 *
 * @author Tobias
 */
public class TwitchAPIConfig {

    private static boolean testMode = false;
    private static String serverBaseUrlTestMode;

    private String clientId;
    private String serverBaseUrl;
    private String jsonVersion;
    private int limitNrOfGames;
    private int limitNrOfStreams;

    public TwitchAPIConfig(String clientId, String serverBaseUrl, String jsonVersion, int limitNrOfGames, int limitNrOfStreams) {
        this.clientId = clientId;
        if (testMode) {
            this.serverBaseUrl = serverBaseUrlTestMode;
        } else {
            this.serverBaseUrl = serverBaseUrl;
        }
        this.jsonVersion = jsonVersion;
        this.limitNrOfGames = limitNrOfGames;
        this.limitNrOfStreams = limitNrOfStreams;
    }

    public String getClientId() {
        return clientId;
    }

    public String getServerBaseUrl() {
        return serverBaseUrl;
    }

    public String getJsonVersion() {
        return this.jsonVersion;
    }

    public int getLimitNrOfGames() {
        return limitNrOfGames;
    }

    public int getLimitNrOfStreams() {
        return limitNrOfStreams;
    }

    public static void setServerBaseUrlTestMode(String serverBaseUrlTestMode) {
        TwitchAPIConfig.serverBaseUrlTestMode = serverBaseUrlTestMode;
        testMode = true;
    }

}
