package com.tobiassalem.mytwitchapp.model.stream;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Preview {

    private String small;
    private String medium;
    private String large;
    private String template;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The small
     */
    public String getSmall() {
        return small;
    }

    /**
     *
     * @param small
     * The small
     */
    public void setSmall(String small) {
        this.small = small;
    }

    /**
     *
     * @return
     * The medium
     */
    public String getMedium() {
        return medium;
    }

    /**
     *
     * @param medium
     * The medium
     */
    public void setMedium(String medium) {
        this.medium = medium;
    }

    /**
     *
     * @return
     * The large
     */
    public String getLarge() {
        return large;
    }

    /**
     *
     * @param large
     * The large
     */
    public void setLarge(String large) {
        this.large = large;
    }

    /**
     *
     * @return
     * The template
     */
    public String getTemplate() {
        return template;
    }

    /**
     *
     * @param template
     * The template
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
