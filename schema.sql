CREATE TABLE department (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description TEXT,
  responsable_id BIGINT NULL,
  CONSTRAINT fk_dept_resp FOREIGN KEY (responsable_id) REFERENCES employee(id)
);

CREATE TABLE employee (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  matricule VARCHAR(50) UNIQUE NOT NULL,
  nom VARCHAR(100) NOT NULL,
  prenom VARCHAR(100) NOT NULL,
  date_naissance DATE,
  email VARCHAR(150) UNIQUE,
  telephone VARCHAR(30),
  salaire_base DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  grade VARCHAR(50),
  poste VARCHAR(100),
  date_embauche DATE,
  photo_path VARCHAR(255),
  department_id BIGINT,
  password VARCHAR(255),
  CONSTRAINT fk_emp_dept FOREIGN KEY (department_id) REFERENCES department(id)
);

CREATE TABLE projet (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  titre VARCHAR(150) NOT NULL,
  description TEXT,
  date_debut DATE,
  date_fin DATE,
  etat ENUM('EN_COURS','TERMINE','ANNULE') DEFAULT 'EN_COURS'
);

CREATE TABLE employee_projet (
  employee_id BIGINT,
  projet_id BIGINT,
  role_projet VARCHAR(100),
  PRIMARY KEY (employee_id, projet_id),
  CONSTRAINT fk_ep_emp FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE,
  CONSTRAINT fk_ep_proj FOREIGN KEY (projet_id) REFERENCES projet(id) ON DELETE CASCADE
);

CREATE TABLE fiche_paie (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  employee_id BIGINT NOT NULL,
  annee INT NOT NULL,
  mois INT NOT NULL,
  salaire_base DECIMAL(10,2),
  primes DECIMAL(10,2),
  deductions DECIMAL(10,2),
  net_paye DECIMAL(10,2),
  date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_fp_emp FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE TABLE roles (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  employee_id BIGINT,
  CONSTRAINT fk_user_emp FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE TABLE user_roles (
  user_id BIGINT,
  role_id BIGINT,
  PRIMARY KEY(user_id, role_id),
  CONSTRAINT fk_ur_user FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_ur_role FOREIGN KEY (role_id) REFERENCES roles(id)
);
