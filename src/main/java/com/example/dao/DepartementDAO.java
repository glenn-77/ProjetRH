package com.example.dao;

import com.example.model.Departement;
import com.example.model.Employe;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DepartementDAO {

    public void save(Departement d) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            // Synchronisation côté employé (clé étrangère)
            if (d.getEmployes() != null) {
                Set<Employe> attached = new HashSet<>();
                for (Employe e : d.getEmployes()) {
                    Employe emp = session.get(Employe.class, e.getId());
                    emp.setDepartement(d);
                    attached.add(emp);
                }
                d.setEmployes(attached);
            }

            session.saveOrUpdate(d);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) tx.rollback();
            ex.printStackTrace();
        }
    }

    public void update(Departement d, String[] employeIds) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Departement managedDept = session.get(Departement.class, d.getId());
            if (managedDept == null) throw new RuntimeException("Département introuvable");

            managedDept.setNom(d.getNom());

            if (d.getChef() != null) {
                Employe attachedChef = session.get(Employe.class, d.getChef().getId());
                managedDept.setChef(attachedChef);
                attachedChef.setDepartement(managedDept); // chef lié à son département
            }

            // Détacher les anciens employés
            for (Employe e : managedDept.getEmployes()) {
                e.setDepartement(null);
            }
            managedDept.getEmployes().clear();

            // Ajouter les nouveaux employés
            if (employeIds != null) {
                Set<Employe> attached = new HashSet<>();
                for (String idStr : employeIds) {
                    int empId = Integer.parseInt(idStr);
                    Employe emp = session.get(Employe.class, empId); //  employé existant
                    if (emp != null) {
                        emp.setDepartement(managedDept);
                        managedDept.getEmployes().add(emp);
                    }
                }
                managedDept.setEmployes(attached);
            }

            session.merge(managedDept);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) tx.rollback();
            ex.printStackTrace();
        }
    }

    public void delete(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Departement d = session.get(Departement.class, id);
            if (d != null) {
                // Détacher les employés avant suppression
                for (Employe e : d.getEmployes()) {
                    e.setDepartement(null);
                }
                session.delete(d);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) tx.rollback();
            ex.printStackTrace();
        }
    }

    public Departement getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT d FROM Departement d " +
                                    "LEFT JOIN FETCH d.employes " +
                                    "LEFT JOIN FETCH d.chef " +
                                    "WHERE d.id = :id",
                            Departement.class
                    ).setParameter("id", id)
                    .uniqueResult();
        }
    }

    public Departement findByChefId(int chefId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT d FROM Departement d LEFT JOIN FETCH d.employes LEFT JOIN FETCH d.chef WHERE d.chef.id = :chefId",
                            Departement.class
                    )
                    .setParameter("chefId", chefId)
                    .uniqueResult();
        }
    }


    public Departement findByEmployeId(int employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT d FROM Departement d " +
                                    "LEFT JOIN FETCH d.employes " +
                                    "LEFT JOIN FETCH d.chef " +
                                    "JOIN d.employes e " +
                                    "WHERE e.id = :employeId",
                            Departement.class
                    )
                    .setParameter("employeId", employeId)
                    .uniqueResult();
        }
    }

    public List<Departement> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT d FROM Departement d LEFT JOIN FETCH d.employes LEFT JOIN FETCH d.chef",
                    Departement.class
            ).list();
        }
    }
}
