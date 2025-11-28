🧑‍💼 Projet de Gestion RH – JEE & Spring Boot

Auteurs : Owen Paimba-Sail, Glenn Diffo, Mohamed ElGhali Sadiqi, Christelle Millet
Année : 2025 – 2026
Encadrant : Mohamed Haddache
Matière : JEE

📌 Présentation du projet

Ce projet a pour objectif de développer une application web complète dédiée à la gestion des ressources humaines d’une entreprise.
Il permet d’administrer les employés, les départements, les projets ainsi que les fiches de paie, tout en intégrant un système d'authentification sécurisé et basé sur les rôles.

Le projet comporte deux versions :

Une version JEE (Jakarta EE) : Servlets, JSP, JSTL, DAO Hibernate (JPA).

Une version Spring Boot : Controllers Spring, Thymeleaf, Spring Data JPA, services, templates intégrés.

🏗️ Fonctionnalités principales
🔐 Authentification & rôles

Connexion via identifiant unique + mot de passe.

Rôles disponibles : Admin, Chef de département, Chef de projet, Employé.

Permissions dynamiques selon le rôle.

👥 Gestion des employés

Création, modification et suppression d’employés.

Recherche via nom, prénom, matricule ou département.

Attribution automatique d’identifiant + mot de passe temporaire.

Consultation des projets et fiches de paie liées.

🏢 Gestion des départements

Ajout, modification et suppression de départements.

Attribution d’un chef de département.

Liste des employés rattachés.

📁 Gestion des projets

Création, modification, suppression de projets.

Attribution d’un chef de projet.

Ajout ou retrait d’employés dans les projets.

💸 Gestion des fiches de paie

Création et édition d’une fiche.

Export, consultation et suppression de fiches existantes.

👤 Profil utilisateur

Consultation des informations personnelles.

Modification du mot de passe.

📂 Arborescence du projet (version JEE)
src/
 ├── main/java/com/example/
 │    ├── controller/      → Servlets (AuthServlet, EmployeServlet...)
 │    ├── dao/             → DAO Hibernate
 │    ├── model/           → Entités JPA
 │    └── utils/           → HibernateUtil
 └── main/webapp/
       ├── jsp/            → JSP (login, employés, projets…)
       ├── css/            → Styles
       └── index.jsp

📂 Arborescence du projet (version Spring Boot)
src/
 ├── main/java/com/spring/
 │    ├── controller/      → Controllers Spring MVC
 │    ├── repository/      → Spring Data JPA
 │    ├── service/         → Services métier
 │    └── model/           → Entités
 └── main/resources/
       ├── templates/      → Pages Thymeleaf
       └── application.properties

🗃️ Base de données

SGBD : MySQL

Script SQL fourni dans le projet

Mapping via JPA/Hibernate

Spring Boot utilise spring.jpa.hibernate.ddl-auto=none pour éviter les altérations automatiques du schéma.

▶️ Lancer le projet
✔️ Version JEE

Importer le projet sous IntelliJ ou Eclipse.

Configurer Tomcat (v10+).

Démarrer le serveur → accès via :

http://localhost:8080/ProjetRH

✔️ Version Spring Boot

Importer le projet Maven.

Configurer application.properties (BDD).

Lancer la classe Application.java.

Accéder au site :

http://localhost:8080/

👨‍💻 Technologies utilisées
Version JEE

Jakarta Servlet & JSP

JSTL

Hibernate (JPA)

MySQL

Tomcat

Bootstrap

Version Spring Boot

Spring Web MVC

Spring Data JPA

Thymeleaf

Spring Mail

Lombok

MySQL

👥 Répartition des tâches

Owen : Authentification, pages profil, affichage et gestion des employés, projets, fiches de paie (JEE & Spring).

Glenn : Gestion projets & départements, interactions DAO, intégration Spring.

Ghali : Rapport, structure MVC, organisation, recherche & filtres employés.

Christelle : Interfaces JSP/Thymeleaf, navigation, cohérence graphique, tests.

✔️ Conclusion

Ce projet nous a permis d'appliquer concrètement les notions de JEE, puis d’approfondir en les portant sur Spring Boot, un framework moderne permettant une meilleure structuration, une configuration simplifiée et une architecture plus robuste.
La mise en place de rôles, de modules cohérents (employés, projets, départements, paie) et d’un MVC bien organisé nous a permis d’obtenir une application cohérente, fonctionnelle et proche d’un véritable outil RH professionnel.
