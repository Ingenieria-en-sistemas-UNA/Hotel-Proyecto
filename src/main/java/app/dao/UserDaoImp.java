package app.dao;

import app.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public boolean existsByUsername(String username) {
        User user = getUser(username);
        return user != null;
    }

    @Override
    public User findByUsername(String username) {
        User user = getUser(username);
        return user;
    }

    @Override
    public void deleteByUsername(String username) {
        User user = getUser(username);
        this.entityManager.remove(user);
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> list() {
        return this.entityManager.createQuery("FROM User",User.class).getResultList();
    }

    @Override
    public User update(User user) {
        return this.entityManager.merge(user);
    }

    private User getUser(String username) {
        String query = "SELECT u FROM User u WHERE u.username = :userName";
        return this.entityManager.createQuery(query, User.class)
                .setParameter("userName", username)
                .getSingleResult();
    }
}
