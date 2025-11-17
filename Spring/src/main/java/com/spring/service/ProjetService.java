package com.spring.service;

import java.util.List;
import com.spring.model.*;
import com.spring.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjetService {

    private final ProjetRepository projetRepository;
    private final EmployeRepository employeRepository;
    private final DepartementRepository departementRepository;

    public List<Projet> getAll() {
        return projetRepository.findAll();
    }

    public Projet getById(Long id) {
        return projetRepository.findById(id).orElse(null);
    }

    public Projet create(Projet p) {
        return projetRepository.save(p);
    }

    public Projet update(Long id, Projet data) {
        Projet p = getById(id);
        if (p == null) return null;

        p.setNom(data.getNom());
        p.setDescription(data.getDescription());
        p.setBudget(data.getBudget());
        p.setEtat(data.getEtat());
        p.setDateDebut(data.getDateDebut());
        p.setDateFin(data.getDateFin());
        return projetRepository.save(p);
    }

    public void delete(Long id) {
        projetRepository.deleteById(id);
    }

    // Gérer chef de projet
    public void setChefProjet(Long projetId, Long chefId) {
        Projet p = getById(projetId);
        Employe chef = employeRepository.findById(chefId).orElse(null);

        if (p == null || chef == null) return;

        p.setChefProjet(chef);
        projetRepository.save(p);
    }

    // Affectation employés
    public void assignEmployes(Long projetId, List<Long> empIds) {
        Projet p = getById(projetId);
        if (p == null) return;

        p.getEmployes().clear();

        for (Long id : empIds) {
            Employe e = employeRepository.findById(id).orElse(null);
            if (e != null) {
                p.addEmploye(e);
            }
        }

        projetRepository.save(p);
    }
}

