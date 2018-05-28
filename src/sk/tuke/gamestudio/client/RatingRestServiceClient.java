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
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

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

    public void createRating(Class clazz, String nameOfPlayer){
        Rating rating = new Rating();
        int rate = -1;
        while(true) {
            System.out.println("Put your raking here: (between 1 and 5)");
            Pattern p = Pattern.compile("[12345]");
            String input = getInput();
            Matcher m = p.matcher(input);
            if(!m.matches()) {
                System.out.println(ANSI_RED + "Not a valid rating!" + ANSI_RESET);
                continue;
            }
            rate = Integer.parseInt(input);
            if (rate <= 5 && rate >= 1) {
                rating.setRate(rate);
                rating.setGame(clazz.getSimpleName());
                rating.setName(nameOfPlayer);
                setRating(rating);
                return;
            } else {
                System.out.println(ANSI_RED + "Not a valid rating!" + ANSI_RESET);
            }
        }
    }

    public String getInput() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = null;
            input = br.readLine();
            return input;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void printList(List<Rating> list, String game){
        int index = 1;
        if(game != null){
            System.out.println(ANSI_GREEN + "- - - - R a T i N G S  of " + game.toUpperCase() + " G a M E - - - - " + ANSI_RESET);
        } else {
            System.out.println(ANSI_GREEN +"- - - - R a T i N G S - - - -" + ANSI_RESET);
        }
        for(Rating rating: list){
            System.out.println(index + ". "
                    + rating.getName() + ": "
                    + rating.getRate());
            index++;
        }
    }
}
