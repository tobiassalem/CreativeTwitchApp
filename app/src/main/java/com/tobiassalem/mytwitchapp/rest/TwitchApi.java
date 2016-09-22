package com.tobiassalem.mytwitchapp.rest;

import com.tobiassalem.mytwitchapp.model.game.TopGamesResultModel;
import com.tobiassalem.mytwitchapp.model.stream.TopStreamsResultModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Interface defining the Twitch API endpoints
 *
 * @author Tobias
 */
public interface TwitchApi {

    @GET("kraken/games/top")
    Call<TopGamesResultModel> getTopGames(@Query("limit") int limit);

    @GET("kraken/streams")
    Call<TopStreamsResultModel> getTopStreamsForGame(@Query("game") String game, @Query("limit") int limit);
}
