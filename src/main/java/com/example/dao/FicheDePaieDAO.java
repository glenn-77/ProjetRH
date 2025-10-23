package com.example.dao;

import com.example.model.FicheDePaie;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class FicheDePaieDAO {

    public void save(FicheDePaie f) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(f);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) tx.rollback();
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    public void delete(int id) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            FicheDePaie f = session.get(FicheDePaie.class, id);
            if (f != null) session.delete(f);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) tx.rollback();
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    public FicheDePaie getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(FicheDePaie.class, id);
        }
    }

    public List<FicheDePaie> getByEmploye(int idEmp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from FicheDePaie where employe.id = :idEmp", FicheDePaie.class)
                    .setParameter("idEmp", idEmp)
                    .list();
        }
    }

    public List<FicheDePaie> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from FicheDePaie", FicheDePaie.class).list();
        }
    }
}
