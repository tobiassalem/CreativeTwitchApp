package com.tobiassalem.mytwitchapp.model.game;


import java.util.HashMap;
import java.util.Map;

/**
 * Class represents a single Game from the Twitch API
 *
 * @author Tobias
 */
public class Game {

    private String name;
    private Integer popularity;
    private Integer id;
    private Integer giantbombId;
    private transient Box box;
    private Logo logo;
    private transient Links_ links_;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The popularity
     */
    public Integer getPopularity() {
        return popularity;
    }

    /**
     *
     * @param popularity
     * The popularity
     */
    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

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
     * The giantbombId
     */
    public Integer getGiantbombId() {
        return giantbombId;
    }

    /**
     *
     * @param giantbombId
     * The giantbomb_id
     */
    public void setGiantbombId(Integer giantbombId) {
        this.giantbombId = giantbombId;
    }


    /**
     *
     * @return
     * The logo
     */
    public Logo getLogo() {
        return logo;
    }

    /**
     *
     * @param logo
     * The logo
     */
    public void setLogo(Logo logo) {
        this.logo = logo;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }
}
