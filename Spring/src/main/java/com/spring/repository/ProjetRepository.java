package com.spring.repository;

import com.spring.model.Projet;
import com.spring.model.EtatProjet;
import com.spring.model.Employe;
import com.spring.model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {

    List<Projet> findByEtat(EtatProjet etat);

    List<Projet> findByDepartement(Departement departement);

    List<Projet> findByChefProjet(Employe chef);

    List<Projet> findByEmployes_Id(Long employeId);

    List<Projet> findByNomContainingIgnoreCase(String nom);
}
