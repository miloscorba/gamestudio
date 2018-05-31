
package sk.tuke.gamestudio.forJson.book;

import java.util.HashMap;
import java.util.Map;

public class Item {

    public String kind;
    public String id;
    public String etag;
    public String selfLink;
    public VolumeInfo volumeInfo;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Item() {
    }


    public Item(String kind, String id, String etag, String selfLink, VolumeInfo volumeInfo) {
        super();
        this.kind = kind;
        this.id = id;
        this.etag = etag;
        this.selfLink = selfLink;
        this.volumeInfo = volumeInfo;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
