package app.dao;

import app.entity.User;

import java.util.List;

public interface UserDao {

    boolean existsByUsername(String username);

    User findByUsername(String username);

    void deleteByUsername(String username);

    void save(User user);

    List<User> list();

    User update(User user);

}
