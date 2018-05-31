
package sk.tuke.gamestudio.forJson.weather;

import java.util.HashMap;
import java.util.Map;

public class Wind {

    public double speed;
    public int deg;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Wind() {
    }

    /**
     * 
     * @param speed
     * @param deg
     */
    public Wind(double speed, int deg) {
        super();
        this.speed = speed;
        this.deg = deg;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
