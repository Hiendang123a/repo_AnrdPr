package com.example.society.dao.Impl;

import com.example.society.dao.Interface.UserDAO;
import jakarta.persistence.EntityManager;
import com.example.society.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public User getUser(Long userID) {
        return null;
    }

    @Override
    public void createUser(User user) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.persist(user);
    }

    @Override
    public void updateUser(User user) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.merge(user);
    }

    @Override
    public void deleteUser(Long userID) {

    }

    @Override
    public User findByUsername(String username) {
        Session currentSession = entityManager.unwrap(Session.class);
        String hql = "SELECT u FROM User u WHERE u.username = :username";
        User user = (User)currentSession.createQuery(hql).setParameter("username", username).uniqueResult();
        return user;
    }

    @Override
    public boolean existsByUsername(String username) {
        Session currentSession = entityManager.unwrap(Session.class);
        String hql = "SELECT COUNT(u) FROM User u WHERE u.username = :username";
        Long  count = (Long)currentSession.createQuery(hql).setParameter("username", username).uniqueResult();
        System.out.println(count + " " + username);
        return count > 0;
    }

}

