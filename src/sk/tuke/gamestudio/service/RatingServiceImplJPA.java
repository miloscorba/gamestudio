package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RatingServiceImplJPA implements  RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
        Rating oldRating;
        try {
            oldRating = (Rating) entityManager.createNamedQuery("Rating.getRatingByName")
                    .setParameter("game", rating.getGame())
                    .setParameter("name", rating.getName())
                    .getSingleResult();
        } catch (Exception e) {
            oldRating = null;
        }
        if(oldRating == null) {
            entityManager.persist(rating);
        }
        else {
            oldRating.setRate(rating.getRate());
            entityManager.merge(oldRating);
        }
    }

    @Override
    public List<Rating> getAllRatingsOfGame(String game) {
        return entityManager.createNamedQuery("Rating.getAllRatingsOfGame")
                .setParameter("game", game).getResultList();
    }

    @Override
    public String averageRating(String game) {
        String dou = null;
        try{
            dou = entityManager.createNamedQuery("Rating.averageRating")
                    .setParameter("game", game).getSingleResult().toString();
        } catch (Exception e){
            return "Not rated game.";
        }
        return dou;
    }
}
