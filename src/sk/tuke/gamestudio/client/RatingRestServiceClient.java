package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RatingRestServiceClient implements RatingService {
    private static final String URL = "http://localhost:8080/gamestudio_war_exploded/api/rating";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";

    @Override
    public void setRating(Rating rating) {
        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(rating, MediaType.APPLICATION_JSON), Response.class);
        } catch (Exception e) {
            throw new RuntimeException("Error saving rating", e);
        }
    }

    @Override
    public String averageRating(String game) {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/" + game)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<String>() {
                    });
        } catch (Exception e) {
            throw new RuntimeException("Error loading rating", e);
        }
    }

    @Override
    public List<Rating> getAllRatingsOfGame(String game) {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/ratings/" + game)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Rating>>() {
                    });
        } catch (Exception e) {
            throw new RuntimeException("Error loading rating", e);
        }
    }


}
