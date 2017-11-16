# Create new DB on server;
DROP SCHEMA IF EXISTS toleksiv;
CREATE SCHEMA toleksiv DEFAULT CHARACTER SET utf8 ;
USE toleksiv;
# 01. Create table companies;
CREATE TABLE companies (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX COMPANY_IDX (name ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
# 02. Create table customers;
CREATE TABLE customers (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX CUSTOMER_IDX (name ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
# 03. Create table skills;
CREATE TABLE skills (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX name_UNIQUE (name ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
# 04. Create table developers (developer could work only in 1 company);
CREATE TABLE developers (
  id int(11) NOT NULL AUTO_INCREMENT,
  firstname varchar(45) NOT NULL,
  lastname varchar(45) NOT NULL,
  company_id int(11) NOT NULL,
  salary DECIMAL(7,2) NOT NULL,
  PRIMARY KEY (id,company_id),
  KEY FK_COMPANY_idx (company_id),
  CONSTRAINT FK_DEVELOPERS_COMPANY FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# 05. Create table projects;
CREATE TABLE projects (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  company_id INT NOT NULL,
  customer_id INT NOT NULL,
  costs DECIMAL(9,2) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX CUST_PROJ (name ASC, customer_id ASC),
  INDEX COMPANY_idx (company_id ASC),
  INDEX CUSTOMER_idx (customer_id ASC),
  CONSTRAINT FK_PROJECTS_COMPANY
  FOREIGN KEY (company_id)
  REFERENCES companies (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT FK_PROJECTS_CUSTOMER
  FOREIGN KEY (customer_id)
  REFERENCES customers (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
# 06. Create table developer_skills to set "Many-to-Many" relation between developers and skills;
CREATE TABLE developer_skills (
  id int(11) NOT NULL AUTO_INCREMENT,
  developer_id int(11) NOT NULL,
  skill_id int(11) NOT NULL,
  PRIMARY KEY (developer_id,skill_id),
  UNIQUE KEY id_UNIQUE (id),
  KEY FK_SKILLS_idx (skill_id),
  CONSTRAINT FK_DEVELOPER_SKILL FOREIGN KEY (developer_id) REFERENCES developers (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_SKILL FOREIGN KEY (skill_id) REFERENCES skills (id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# 07. Create table developer_projects to set "Many-to-Many" relation between developers and projects;
# One and the same developer could work on different projects;
CREATE TABLE developer_projects (
  id int(11) NOT NULL AUTO_INCREMENT,
  developer_id int(11) NOT NULL,
  project_id int(11) NOT NULL,
  PRIMARY KEY (developer_id,project_id),
  UNIQUE KEY id_UNIQUE (id),
  KEY FK_PROJECT_idx (project_id),
  CONSTRAINT FK_DEVELOPER_PROJECT FOREIGN KEY (developer_id) REFERENCES developers (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_PROJECT FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# 08. Create view to calculate the price of each project based on the salary of developers working on this project;
CREATE OR REPLACE VIEW project_outlays AS
  SELECT developer_projects.project_id, sum(developers.salary) AS total_sum
  FROM developer_projects LEFT JOIN developers ON
  developer_projects.developer_id = developers.id
  GROUP BY developer_projects.project_id;
# 09. Create view to calculate benefits for each company coming from every customer;
CREATE OR REPLACE VIEW company_benefits AS
  SELECT projects.company_id, projects.customer_id, sum(projects.costs) AS benefit
  FROM projects GROUP BY company_id, customer_id;



