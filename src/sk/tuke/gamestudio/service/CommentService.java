package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);
    Comment getComment(Integer id);
    List<Comment> getAllComments();
    List<Comment> getAllCommentsOfGame(String game);
    void delete(Comment comment);
}
