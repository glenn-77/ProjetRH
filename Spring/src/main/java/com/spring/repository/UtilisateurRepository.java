package com.spring.repository;

import com.spring.model.Utilisateur;
import com.spring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Utilisateur findByLoginAndMotDePasse(String login, String motDePasse);

    Utilisateur findByLogin(String login);

    Utilisateur findByEmploye_Id(Long employeId);

    java.util.List<Utilisateur> findByRole(Role role);
}

