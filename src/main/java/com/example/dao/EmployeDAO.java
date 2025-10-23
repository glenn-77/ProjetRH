package com.example.dao;

import com.example.model.Employe;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeDAO {

    /**
     *  Sauvegarde ou mise à jour d’un employé.
     * Hibernate détecte automatiquement si c’est un nouvel enregistrement (id = 0)
     * ou une mise à jour (id existant).
     */
    public void save(Employe e) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.merge(e);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) tx.rollback();
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    /**
     *  Mise à jour explicite d’un employé (appelée depuis EmployeServlet.doPost)
     */
    public void update(Employe e) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.merge(e);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) tx.rollback();
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    /**
     *  Suppression d’un employé par ID
     */
    public void delete(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Employe e = session.get(Employe.class, id);
            if (e != null) {
                session.delete(e);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    /**
     *  Récupère un employé à partir de son ID
     */
    public Employe getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Employe.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *  Récupère tous les employés
     */
    public List<Employe> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Employe", Employe.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *  Récupère tous les employés d’un département donné
     */
    public List<Employe> getByDepartement(int idDept) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Employe where departement.id = :idDept", Employe.class)
                    .setParameter("idDept", idDept)
                    .list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
