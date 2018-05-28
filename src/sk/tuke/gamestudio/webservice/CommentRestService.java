package sk.tuke.gamestudio.webservice;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@Path("/comment")
public class CommentRestService {
    @EJB
    private CommentService commentService;

    @POST
    @Consumes("application/json")
    public Response addComment(Comment comment){
        if(comment == null){
            return Response.status(404).build();
        }
        System.out.println("TU SOMMMMMMM");
        commentService.addComment(comment);
        return Response.ok().build();
    }

    @GET
    @Path("/{game}")
    @Produces("application/json")
    public List<Comment> getAllCommentsOfGame(@PathParam("game") String game) {
        return commentService.getAllCommentsOfGame(game);
    }

    @GET
    //@Path("/all")
    @Produces("application/json")
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

}
