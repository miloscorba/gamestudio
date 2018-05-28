package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentService;

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

public class CommentRestServiceClient implements CommentService {
    private static final String URL = "http://localhost:8080/gamestudio_war_exploded/api/comment";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    @Override
    public void addComment(Comment comment)  {
        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(comment, MediaType.APPLICATION_JSON), Response.class);
        } catch (Exception e) {
            throw new RuntimeException("Error saving score", e);
        }
    }

    @Override
    public Comment getComment(Integer id) {
        return null;
    }

    @Override
    public List<Comment> getAllComments() {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/")
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Comment>>() {
                    });
        } catch (Exception e) {
            throw new RuntimeException(ANSI_RED + "Error loading comment" + ANSI_RESET, e);
        }
    }

    @Override
    public List<Comment> getAllCommentsOfGame(String game) {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/" + game)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Comment>>() {
                    });
        } catch (Exception e) {
            throw new RuntimeException("Error loading comment", e);
        }
    }

    @Override
    public void delete(Comment comment) {

    }

    public void printList(List<Comment> list, String game){
        int index = 1;
        if(game != null){
            System.out.println(ANSI_GREEN + "- - - - C o M e N T S  of " + game.toUpperCase() + " G a M E - - - - " + ANSI_RESET);
            for(Comment comment : list){
                System.out.println(index + ". "
                        + comment.getName() + ": "
                        + comment.getComment());
                index++;
            }
            System.out.println();
        } else {
            System.out.println(ANSI_GREEN +"- - - - C o M e N T S - - - -" + ANSI_RESET);
            for(Comment comment : list){
                System.out.println(index + ". "
                        + comment.getName()
                        + " (" + comment.getGame() + ")"
                        + ": " + comment.getComment());
                index++;
            }
            System.out.println();
        }
    }

    public void createComment(Class clazz, String nameOfPlayer){
        Comment comment = new Comment();
        System.out.println("Your Comment: ");
        comment.setComment(getInput());
        comment.setGame(clazz.getSimpleName());
        comment.setName(nameOfPlayer);
        addComment(comment);
    }

    public String getInput() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        try {
            input = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

}
