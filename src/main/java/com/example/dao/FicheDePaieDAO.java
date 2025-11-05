package com.example.dao;

import com.example.model.FicheDePaie;
import com.example.model.Projet;
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

    public List<FicheDePaie> getByEmploye(int employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT f FROM FicheDePaie f JOIN f.employe e WHERE e.id = :employeId",
                            FicheDePaie.class
                    )
                    .setParameter("employeId", employeId)
                    .list();
        }
    }

    public List<FicheDePaie> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT DISTINCT f from FicheDePaie f LEFT JOIN FETCH f.employe", FicheDePaie.class).list();
        }
    }
}
