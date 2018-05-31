
package sk.tuke.gamestudio.forJson.book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookInfo {

    public String kind;
    public int totalItems;
    public List<Item> items = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public BookInfo() {
    }

    /**
     * 
     * @param items
     * @param totalItems
     * @param kind
     */
    public BookInfo(String kind, int totalItems, List<Item> items) {
        super();
        this.kind = kind;
        this.totalItems = totalItems;
        this.items = items;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
