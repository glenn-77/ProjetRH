package com.example.dao;

import com.example.model.Employe;
import com.example.model.Projet;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Set<Projet> attachedProjets = new HashSet<>();
            for (Projet p : e.getProjets()) {
                Projet managed = session.get(Projet.class, p.getId());
                attachedProjets.add(managed);
                managed.getEmployes().add(e);
            }
            e.setProjets(attachedProjets);

            session.merge(e);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) tx.rollback();
            ex.printStackTrace();
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
        Transaction tx = null;
        Employe employe = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            employe = session.createQuery(
                            "SELECT e FROM Employe e " +
                                    "LEFT JOIN FETCH e.departement " +
                                    "LEFT JOIN FETCH e.projets " +
                                    "LEFT JOIN FETCH e.fichesPaie WHERE e.id = :id", Employe.class)
                    .setParameter("id", id)
                    .uniqueResult();

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        }

        return employe;
    }

    public Employe getByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employe WHERE email = :email", Employe.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }



    /**
     *  Récupère tous les employés
     */
    public List<Employe> getAll() {
        Transaction tx = null;
        List<Employe> employes = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            employes = session.createQuery(
                            "SELECT DISTINCT e FROM Employe e " +
                                    "LEFT JOIN FETCH e.departement " +
                                    "LEFT JOIN FETCH e.projets LEFT JOIN FETCH e.fichesPaie", Employe.class)
                    .getResultList();

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) tx.rollback();
            ex.printStackTrace();
        }

        return employes;
    }


    /**
     *  Liste des employés triés par grade (ordre alphabétique du grade)
     */
    public List<Employe> getAllOrderByGrade() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employe ORDER BY grade ASC", Employe.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *  Liste des employés triés par poste
     */
    public List<Employe> getAllOrderByPoste() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employe ORDER BY poste ASC", Employe.class).list();
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

    public List<Employe> search(String keyword) {
        Transaction tx = null;
        Session session = null;
        List<Employe> employes = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            employes = session.createQuery(
                            "SELECT e FROM Employe e " +
                                    "LEFT JOIN FETCH e.departement d " +
                                    "WHERE LOWER(e.nom) LIKE LOWER(:kw) " +
                                    "OR LOWER(e.prenom) LIKE LOWER(:kw) " +
                                    "OR LOWER(e.matricule) LIKE LOWER(:kw) " +
                                    "OR LOWER(d.nom) LIKE LOWER(:kw)", Employe.class)
                    .setParameter("kw", "%" + keyword + "%")
                    .list();

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        }

        return employes;
    }

}
