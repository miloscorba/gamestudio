package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.util.List;

public interface RatingService {
    void setRating(Rating rating);
    String averageRating(String game);
    List<Rating> getAllRatingsOfGame(String game);
}
