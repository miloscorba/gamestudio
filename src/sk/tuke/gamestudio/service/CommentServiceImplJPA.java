package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CommentServiceImplJPA implements CommentService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) {
        System.out.println("POLACEK");
        entityManager.persist(comment);
    }

    @Override
    public Comment getComment(Integer id) {
        return entityManager.find(Comment.class, id);
    }

    @Override
    public List<Comment> getAllComments() {
        return  entityManager.createNamedQuery("Comment.getAllComments")
                .getResultList();
    }

    @Override
    public List<Comment> getAllCommentsOfGame(String game) {
        return entityManager.createNamedQuery("Comment.getAllCommentsOfGame")
                .setParameter("game", game).getResultList();
    }

    @Override
    public void delete(Comment comment) {
        entityManager.remove(comment);
    }
}
