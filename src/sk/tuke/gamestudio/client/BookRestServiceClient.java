package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.forJson.book.BookInfo;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

public class BookRestServiceClient {
    private static final String startURL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String endURL = "&printType=books&projection=lite&key=AIzaSyBW0CpSogOAiEuDu-q6lFfbP4kr699waVE";

    public BookInfo getBookInfo(String authorsName) {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(startURL+authorsName+endURL)
                    //.path()
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<BookInfo>() {
                    });
        } catch (Exception e) {
            System.out.println("You choose bad city, try it again!");
        }
        return null;
    }
}
