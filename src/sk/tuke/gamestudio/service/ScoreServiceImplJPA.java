package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Stateless
public class ScoreServiceImplJPA implements ScoreService {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private JMSContext context;

    @Resource(lookup = "jms/achievementQueue")
    private Queue queue;

    @Override
    public void addScore(Score score) {
        entityManager.persist(score);
        String text = "New great score for player: " + score.getPlayer();
        context.createProducer().send(queue, context.createTextMessage(text));
    }


    @GET
    @Path("/id/{id}")
    @Produces({"application/json"})
    public Score getScore(@PathParam("id") Integer id){
        return entityManager.find(Score.class, id);
    }

    @Override
    public List<Score> getBestScoresForGame(String game) {
        return entityManager.createNamedQuery("Score.getBestScoresForGame")
                .setParameter("game", game).setMaxResults(10).getResultList();
    }



    public Score updateScore(Score score){
        return entityManager.merge(score);
    }

    public void deleteScore(Score scoreToRemove){
       Score score = entityManager.find(Score.class, scoreToRemove);
       entityManager.remove(score);
    }
}
