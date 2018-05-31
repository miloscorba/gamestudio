
package sk.tuke.gamestudio.forJson.weather;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherInfo {

    public Coord coord;
    public List<Weather> weather = null;
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int id;
    public String name;
    public int cod;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public WeatherInfo() {
    }

    /**
     * 
     * @param id
     * @param dt
     * @param clouds
     * @param coord
     * @param wind
     * @param cod
     * @param visibility
     * @param sys
     * @param name
     * @param base
     * @param weather
     * @param main
     */
    public WeatherInfo(Coord coord, List<Weather> weather, String base, Main main, int visibility, Wind wind, Clouds clouds, int dt, Sys sys, int id, String name, int cod) {
        super();
        this.coord = coord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.clouds = clouds;
        this.dt = dt;
        this.sys = sys;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getName() {
        return name;
    }

    public Main getMain() {
        return main;
    }
}
