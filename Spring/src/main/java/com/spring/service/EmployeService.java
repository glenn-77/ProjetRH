package com.spring.service;

import com.spring.model.*;
import com.spring.repository.*;
import com.spring.utils.EmailService;
import com.spring.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final ProjetRepository projetRepository;
    private final DepartementRepository departementRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    // ----------- CRUD -----------
    public List<Employe> getAll() {
        return employeRepository.findAll();
    }

    public Employe getById(Long id) {
        return employeRepository.findById(id).orElse(null);
    }

    public Employe createEmploye(Employe employe) {
        if (employe.getMatricule() == null || employe.getMatricule().isEmpty()) {
            employe.setMatricule(generateMatricule());
        }

        if (employe.getDateEmbauche() == null)
            employe.setDateEmbauche(LocalDate.now());

        return employeRepository.save(employe);
    }

    public Employe updateEmploye(Long id, Employe data) {
        Employe e = getById(id);
        if (e == null) return null;

        e.setNom(data.getNom());
        e.setPrenom(data.getPrenom());
        e.setPoste(data.getPoste());
        e.setAdresse(data.getAdresse());
        e.setTelephone(data.getTelephone());
        e.setEmail(data.getEmail());
        e.setGrade(data.getGrade());
        e.setSalaireBase(data.getSalaireBase());

        if (data.getDepartement() != null)
            e.setDepartement(data.getDepartement());

        return employeRepository.save(e);
    }

    public void deleteEmploye(Long id) {
        employeRepository.deleteById(id);
    }

    // ----------- SEARCH -----------
    public List<Employe> search(String keyword) {
        if (keyword == null || keyword.isEmpty()) return getAll();
        return employeRepository.search(keyword);
    }

    public List<Employe> sortByGrade() {
        return employeRepository.findAllByOrderByGradeAsc();
    }

    public List<Employe> sortByPoste() {
        return employeRepository.findAllByOrderByPosteAsc();
    }

    public List<Employe> getByDepartement(Long deptId) {
        return employeRepository.findByDepartement_Id(deptId);
    }

    // ----------- PROJETS -----------
    public void assignProjects(Long employeId, List<Long> projectIds) {
        Employe emp = getById(employeId);
        if (emp == null) return;

        emp.getProjets().clear();

        for (Long pid : projectIds) {
            Projet p = projetRepository.findById(pid).orElse(null);
            if (p != null) {
                emp.addProjet(p);
            }
        }

        employeRepository.save(emp);
    }

    @Transactional(readOnly = true)
    public Employe getEmployeWithDetails(Long id) {
        Employe e = employeRepository.findById(id).orElse(null);
        if (e != null) {
            // ⚡ Forcer le chargement des associations lazy
            if (e.getDepartement() != null) {
                e.getDepartement().getNom(); // déclenche le chargement
            }
            if (e.getProjets() != null) {
                e.getProjets().size(); // déclenche le chargement
            }
        }
        return e;
    }

    // ----------- UTILISATEUR -----------
    public Utilisateur createUserForEmploye(Employe employe, NomRole nomRole) {
        Role role = roleRepository.findByNomRole(nomRole);
        if (role == null) return null;

        Utilisateur u = Utilisateur.builder()
                .login(employe.getNom().charAt(0) + "." + employe.getPrenom())
                .motDePasse(PasswordUtil.generateRandomPassword(12))
                .role(role)
                .employe(employe)
                .build();

        handleChefRole(employe, nomRole);

        if (employe.getEmail() != null && !employe.getEmail().isEmpty()) {
            String sujet = "Création de votre compte - Gestion RH";
            String contenu = """
                    Bonjour %s,

                    Votre compte utilisateur a été créé sur la plateforme RH.

                    🔑 Identifiants de connexion :
                    • Login : %s
                    • Mot de passe : %s

                    Pensez à modifier votre mot de passe lors de votre première connexion.

                    Cordialement,
                    L'équipe RH
                    """.formatted(employe.getPrenom(), u.getLogin(), u.getMotDePasse());

            emailService.envoyerMail(employe.getEmail(), sujet, contenu);
        }

        return utilisateurRepository.save(u);
    }

    public void handleChefRole(Employe employe, NomRole role) {

        if (role != NomRole.CHEF_DE_DEPARTEMENT) return;
        if (employe.getDepartement() == null) return;

        Departement d = employe.getDepartement();

        // Retirer l'ancien chef si besoin
        if (d.getChef() != null && !d.getChef().getId().equals(employe.getId())) {
            d.setChef(null);
        }

        // Mettre ce nouvel employé comme chef
        d.setChef(employe);
        departementRepository.save(d);
    }

    private String generateMatricule() {
        return "EMP-" + (10000 + new Random().nextInt(90000));
    }

    public List<Employe> getChefsByDepartement(Long deptId) {
        return getByDepartement(deptId).stream()
                .filter(e -> utilisateurRepository.findByEmploye_Id(e.getId()) != null &&
                        utilisateurRepository.findByEmploye_Id(e.getId()).getRole().getNomRole() == NomRole.CHEF_DE_PROJET)
                .toList();
    }
}

