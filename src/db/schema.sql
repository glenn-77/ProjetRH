CREATE TABLE department (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description TEXT,
  manager_id BIGINT NULL,
  CONSTRAINT fk_department_manager FOREIGN KEY (manager_id)
      REFERENCES employee(id)
      ON DELETE SET NULL
);


CREATE TABLE employee (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  employee_code VARCHAR(50) UNIQUE NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  birth_date DATE,
  email VARCHAR(150) UNIQUE,
  phone VARCHAR(30),
  base_salary DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  grade VARCHAR(50),
  position VARCHAR(100),
  hire_date DATE,
  department_id BIGINT,
  password VARCHAR(255),
  CONSTRAINT fk_employee_department FOREIGN KEY (department_id)
      REFERENCES department(id)
      ON DELETE SET NULL
);

CREATE INDEX idx_employee_email ON employee(email);
CREATE INDEX idx_employee_department ON employee(department_id);


CREATE TABLE project (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(150) NOT NULL,
  manager_id BIGINT NULL,
  description TEXT,
  start_date DATE,
  end_date DATE,
  status VARCHAR(20) DEFAULT 'IN_PROGRESS' CHECK (status IN ('IN_PROGRESS','COMPLETED','CANCELLED')),
  CONSTRAINT fk_project_manager FOREIGN KEY (manager_id)
          REFERENCES employee(id)
          ON DELETE SET NULL
);

CREATE TABLE employee_project (
  employee_id BIGINT,
  project_id BIGINT,
  project_role VARCHAR(100),
  PRIMARY KEY (employee_id, project_id),
  CONSTRAINT fk_emp_proj_employee FOREIGN KEY (employee_id)
      REFERENCES employee(id)
      ON DELETE CASCADE,
  CONSTRAINT fk_emp_proj_project FOREIGN KEY (project_id)
      REFERENCES project(id)
      ON DELETE CASCADE
);


CREATE TABLE pay_slip (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  employee_id BIGINT NOT NULL,
  year INT NOT NULL,
  month INT NOT NULL,
  base_salary DECIMAL(10,2),
  bonuses DECIMAL(10,2),
  deductions DECIMAL(10,2),
  net_pay DECIMAL(10,2),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_payslip_employee FOREIGN KEY (employee_id)
      REFERENCES employee(id)
      ON DELETE CASCADE
);


CREATE TABLE role (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  employee_id BIGINT,
  CONSTRAINT fk_user_employee FOREIGN KEY (employee_id)
      REFERENCES employee(id)
      ON DELETE SET NULL
);

CREATE TABLE user_role (
  user_id BIGINT,
  role_id BIGINT,
  PRIMARY KEY(user_id, role_id),
  CONSTRAINT fk_userrole_user FOREIGN KEY (user_id)
      REFERENCES user(id)
      ON DELETE CASCADE,
  CONSTRAINT fk_userrole_role FOREIGN KEY (role_id)
      REFERENCES role(id)
      ON DELETE CASCADE
);
