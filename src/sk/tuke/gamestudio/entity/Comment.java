package sk.tuke.gamestudio.entity;

import javax.persistence.*;

@Entity
@NamedQueries({
    @NamedQuery(name = "Comment.getAllCommentsOfGame",
            query = "SELECT s FROM Comment s WHERE s.game=:game"),
    @NamedQuery(name = "Comment.getAllComments",
            query = "SELECT s FROM Comment s")})

public class Comment {
    @Id
    @GeneratedValue
    private Integer ident;
    private String name;
    private String comment;
    private String game;

    public Comment() {
    }

    public Comment(String name, String comment, String game) {
        this.name = name;
        this.comment = comment;
        this.game = game;
    }

    public Integer getIdent() {
        return ident;
    }

    public void setIdent(Integer ident) {
        this.ident = ident;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
