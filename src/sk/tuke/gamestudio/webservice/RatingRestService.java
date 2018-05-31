package sk.tuke.gamestudio.webservice;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/rating")
public class RatingRestService {
    @EJB
    private RatingService ratingService;

    @GET
    @Path("ratings/{game}")
    @Produces("application/json")
    public List<Rating> getAllRatingsOfGame(@PathParam("game") String game) {
        return ratingService.getAllRatingsOfGame(game);
    }

    @GET
    @Path("/{game}")
    @Produces("application/json")
    public String getAverageRating(@PathParam("game") String game){
        return ratingService.averageRating(game);
    }

    @POST
    @Consumes("application/json")
    public Response setRating(Rating rating){
        if(rating == null){
            return Response.status(404).build();
        }
        ratingService.setRating(rating);
        return Response.ok().build();
    }
}
