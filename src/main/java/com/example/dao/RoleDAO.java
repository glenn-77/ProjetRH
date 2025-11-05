package com.example.dao;

import com.example.model.Role;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class RoleDAO {
    public Role getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Role.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Role> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Role", Role.class).list();
        }
    }

}
