package sk.tuke.gamestudio.webservice;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/score")
public class ScoreRestService {
    @EJB
    private ScoreService scoreService;

    @POST
    @Consumes("application/json")
    public Response addScore(Score score){
        if(score == null){
            return Response.status(404).build();
        }
        else{
            scoreService.addScore(score);
//            return Response.created(
//                    UriBuilder.fromMethod(ScoreRestService.class, "getScore")
//                            .build(score.getIdent())
//            ).build();
            return Response.ok().build();
        }
    }

//    @PUT
//    @Path("/id/{id}")
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Score updateScore(@PathParam("id") Integer id, Score score){
//        return scoreService.updateScore(score);
//    }

//    @GET
//    @Path("/id/{id}")
//    @Produces({"application/json"})
//    public Response getScoreById(@PathParam("id") Integer id){
//        Score score = scoreService.getScore(id);
//        if(score == null){
//            return Response.status(404).build();
//        }
//        else{
//            return Response.ok(score).build();
//        }
//    }

    @GET
    @Path("/{game}")
    @Produces("application/json")
    public List<Score> getBestScoresForGame(@PathParam("game") String game) {
        return scoreService.getBestScoresForGame(game);
    }

//    @DELETE
//    @Path("/id/{id}")
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response deleteScore(@PathParam("id") Integer id, Score score){
//        scoreService.deleteScore(score);
//        return Response.ok(score).build();
//    }

//    public void printScoreList(String game){
//        List<Score> listOfScore = scoreService.getBestScoresForGame(game);
//        int index = 0;
//        for(Score s: listOfScore){
//            System.out.println(index + ". " + s.getPlayer() + ": " + s.getScore()/1000/60 + "min "
//                    + s.getScore()/1000%60 + "sec");
//        }
//    }

}

