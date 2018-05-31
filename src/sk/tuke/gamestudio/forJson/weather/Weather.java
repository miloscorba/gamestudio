
package sk.tuke.gamestudio.forJson.weather;

import java.util.HashMap;
import java.util.Map;

public class Weather {

    public int id;
    public String main;
    public String description;
    public String icon;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Weather() {
    }

    /**
     * 
     * @param id
     * @param icon
     * @param description
     * @param main
     */
    public Weather(int id, String main, String description, String icon) {
        super();
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
