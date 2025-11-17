package com.spring.repository;

import com.spring.model.FicheDePaie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FicheDePaieRepository extends JpaRepository<FicheDePaie, Long> {

    List<FicheDePaie> findByEmploye_Id(Long employeId);

    List<FicheDePaie> findByEmploye_IdAndDateGenerationBetween(
            Long employeId, LocalDate debut, LocalDate fin
    );

    List<FicheDePaie> findByDateGenerationBetween(LocalDate debut, LocalDate fin);
}
