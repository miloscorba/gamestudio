package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.forJson.weather.WeatherInfo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

public class WeatherRestServiceClient {

    private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q=";

    public WeatherInfo getWeatherInfo(String city) {
        String NEWURL = URL.concat(city+"&units=metric&appid=a0f9838c87590e64378759a3f29ab438");
        System.out.println(URL);
        try {
            Client client = ClientBuilder.newClient();
            return client.target(NEWURL)
                    //.path()
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<WeatherInfo>() {
                    });
        } catch (Exception e) {
            System.out.println("You choose bad city, try it again!");
        }
        return null;
    }

}
