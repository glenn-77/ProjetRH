package com.spring.repository;

import com.spring.model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {

    Departement findByChef_Id(Long chefId);

    Departement findByEmployes_Id(Long employeId);

    Departement findByNomIgnoreCase(String nom);
}
