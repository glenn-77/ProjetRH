DROP DATABASE IF EXISTS gestion_rh;
CREATE DATABASE gestion_rh;
USE gestion_rh;


CREATE TABLE IF NOT EXISTS departement(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom     VARCHAR(100) NOT NULL,
    chef_id BIGINT
);


CREATE TABLE IF NOT EXISTS employe (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  matricule VARCHAR(50) UNIQUE NOT NULL,
  prenom VARCHAR(100) NOT NULL,
  nom VARCHAR(100) NOT NULL,
  email VARCHAR(150) UNIQUE,
  telephone VARCHAR(30),
  adresse VARCHAR(255),
  salaireBase DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  grade ENUM('JUNIOR', 'SENIOR', 'STAGIAIRE', 'INTERMEDIAIRE', 'DIRECTEUR', 'MANAGER') DEFAULT 'JUNIOR',
  poste VARCHAR(100),
  dateEmbauche DATE,
  departement_id BIGINT,
  CONSTRAINT fk_employee_department FOREIGN KEY (departement_id)
      REFERENCES departement(id)
      ON DELETE SET NULL
);

CREATE INDEX idx_employee_email ON employe(email);
CREATE INDEX idx_employee_department ON employe(departement_id);

ALTER TABLE departement
    ADD CONSTRAINT fk_department_manager FOREIGN KEY (chef_id)
        REFERENCES employe(id)
        ON DELETE SET NULL;


CREATE TABLE IF NOT EXISTS projet (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(150) NOT NULL,
  manager_id BIGINT,
  description TEXT,
  budget DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  dateDebut DATE,
  dateFin DATE,
  etat ENUM('EN_COURS','TERMINE','ANNULE') DEFAULT 'ANNULE',
  CONSTRAINT fk_project_manager FOREIGN KEY (manager_id)
  REFERENCES employe(id)
  ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS employe_projet (
  employe_id BIGINT,
  projet_id BIGINT,
  PRIMARY KEY (employe_id, projet_id),
  CONSTRAINT fk_emp_proj_employee FOREIGN KEY (employe_id)
      REFERENCES employe(id)
      ON DELETE CASCADE,
  CONSTRAINT fk_emp_proj_project FOREIGN KEY (projet_id)
      REFERENCES projet(id)
      ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS fiche_de_paie (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  employe_id BIGINT NOT NULL,
  annee INT NOT NULL,
  mois INT NOT NULL,
  salaireBase DECIMAL(10,2),
  prime DECIMAL(10,2),
  deduction DECIMAL(10,2),
  netAPayer DECIMAL(10,2),
  date_generation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_payslip_employee FOREIGN KEY (employe_id)
      REFERENCES employe(id)
      ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS role (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nomRole ENUM('ADMINISTRATEUR', 'CHEF_DE_DEPARTEMENT','CHEF_DE_PROJET','EMPLOYE') NOT NULL
);

CREATE TABLE IF NOT EXISTS utilisateur (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(100) UNIQUE NOT NULL,
  motDePasse VARCHAR(255) NOT NULL,
  employe_id BIGINT,
  role_id BIGINT NOT NULL,
  CONSTRAINT fk_user_employee FOREIGN KEY (employe_id)
      REFERENCES employe(id)
      ON DELETE SET NULL,
  FOREIGN KEY (role_id) REFERENCES role(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

INSERT INTO role (nomRole)
VALUES ('ADMINISTRATEUR'),
       ('CHEF_DE_DEPARTEMENT'),
       ('CHEF_DE_PROJET'),
       ('EMPLOYE');

INSERT INTO departement (nom) VALUES ('Informatique'), ('Finance'), ('Ressources Humaines');
INSERT INTO employe (nom, prenom, matricule, poste, grade, salaireBase, departement_id)
VALUES ('Dupont', 'Jean', 'E001', 'Développeur', 'Junior', 2500,  1),
       ('Martin', 'Sophie', 'E002', 'Analyste', 'Senior', 320, 2);

INSERT INTO utilisateur (login, motDePasse, role_id, employe_id)
VALUES ('admin', 'admin', 1, 1);
