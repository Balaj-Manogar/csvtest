package com.example.demo.dao;

import com.example.demo.entity.User;

import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager em;

    public void createUser(User user) {
        em.persist(user);
    }

    public List<User> getAllUsers() {
       return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public int updateUser(User user) {
        Query query = em.createQuery("update User set newName=:newName where id=:id");
        query.setParameter("newName", user.getNewName());
        query.setParameter("id", user.getId());
        int i = query.executeUpdate();
        return i;

    }


}
