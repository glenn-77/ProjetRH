package com.example.dao;

import com.example.model.Employe;
import com.example.model.EtatProjet;
import com.example.model.Projet;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjetDAO {

    public void save(Projet p) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(p);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) tx.rollback();
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    public void update(Projet p) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Set<Employe> attachedEmployes = new HashSet<>();
            for (Employe e : p.getEmployes()) {
                Employe managed = session.get(Employe.class, e.getId());
                attachedEmployes.add(managed);
                managed.getProjets().add(p); // synchro inverse
            }
            p.setEmployes(attachedEmployes);

            session.merge(p);
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
            Projet p = session.get(Projet.class, id);
            if (p != null) session.delete(p);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) tx.rollback();
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    public Projet getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT p FROM Projet p LEFT JOIN FETCH p.chefProjet LEFT JOIN FETCH p.employes WHERE p.id = :id",
                    Projet.class
            ).setParameter("id", id).uniqueResult();
        }
    }

    public List<Projet> getByEmploye(int employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT p FROM Projet p JOIN p.employes e WHERE e.id = :employeId",
                            Projet.class
                    )
                    .setParameter("employeId", employeId)
                    .list();
        }
    }


    public List<Projet> getByChef(int chefId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT p FROM Projet p JOIN p.chefProjet c WHERE c.id = :chefId",
                            Projet.class
                    )
                    .setParameter("employeId", chefId)
                    .list();
        }
    }


    public List<Projet> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT DISTINCT p FROM Projet p LEFT JOIN FETCH p.chefProjet LEFT JOIN FETCH p.employes", Projet.class).list();
        }
    }

    public List<Projet> getByEtat(EtatProjet etat) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Projet where etat = :etat", Projet.class)
                    .setParameter("etat", etat)
                    .list();
        }
    }
}