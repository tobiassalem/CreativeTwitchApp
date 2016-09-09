package com.tobiassalem.mytwitchapp.model.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class TopStreamsResultModel {

    private List<Stream> streams = new ArrayList<Stream>();
    private Integer total;
    private Links__ links;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The streams
     */
    public List<Stream> getStreams() {
        return streams;
    }

    /**
     *
     * @param streams
     * The streams
     */
    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

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
     * The _total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     *
     * @return
     * The links
     */
    public Links__ getLinks() {
        return links;
    }

    /**
     *
     * @param links
     * The _links
     */
    public void setLinks(Links__ links) {
        this.links = links;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
