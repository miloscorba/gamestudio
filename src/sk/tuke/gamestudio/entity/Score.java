package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "Score.getBestScoresForGame",
                query = "SELECT s FROM Score s WHERE s.game=:game ORDER BY s.score DESC"),
})

public class Score implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ident;
    private String player;
    private String game;
    private double score;

    public Score() {
    }

    public Score(String player, String game, double score) {
        this.player = player;
        this.game = game;
        this.score = score;
    }

    public Integer getIdent() {
        return ident;
    }

    public void setIdent(Integer ident) {
        this.ident = ident;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double points) {
        this.score = points;
    }


    @Override
    public String toString() {
        return "Score{" +
                "ident=" + ident +
                ", player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", time=" + score /60 + "min " + score %60 + "sec " +
                '}';
    }
}
