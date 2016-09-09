package com.tobiassalem.mytwitchapp.model.game;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

/**
 * Class representing the result model for the top games from the Twitch API
 *
 * @author Tobias
 */
@Generated("org.jsonschema2pojo")
public class TopGamesResultModel {

    @SerializedName("_total")
    private Integer total;

    @SerializedName("_links")
    private Links links;

    @SerializedName("top")
    private List<TopGame> topGames = new ArrayList<TopGame>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     *
     * @param total
     * The total
     */
    public void setTotal(Integer total) {
        this.total = total;
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
     * The topGames
     */
    public List<TopGame> getTopGames() {
        return topGames;
    }

    /**
     *
     * @param topGames
     * The topGames
     */
    public void setTopGames(List<TopGame> topGames) {
        this.topGames = topGames;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
