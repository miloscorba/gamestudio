package sk.tuke.gamestudio.entity;

import com.sun.xml.internal.bind.v2.model.core.ID;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Rating.getAllRatingsOfGame",
                query = "SELECT s FROM Rating s WHERE s.game=:game"),
        @NamedQuery(name = "Rating.getAllRatings",
                query = "SELECT s FROM Rating s"),
        @NamedQuery(name = "Rating.averageRating",
                query = "SELECT AVG(s.rate) FROM Rating s WHERE s.game=:game"),
        @NamedQuery(name = "Rating.getRatingByName",
                query = "SELECT s FROM Rating s WHERE s.game=:game AND s.name=:name"),
})

public class Rating {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String game;
    private int rate;

    public Rating() {
    }

    public Rating(String name, int rate, String game) {
        this.name = name;
        this.rate = rate;
        this.game = game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
