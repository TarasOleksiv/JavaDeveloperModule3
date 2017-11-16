USE toleksiv;
# 01. Populate companies;
INSERT INTO companies (name) VALUES
  ('Eleks'),
  ('Epam'),
  ('Conscensia'),
  ('SoftServe'),
  ('Vakoms');
# 02. Populate customers;
INSERT INTO customers (name) VALUES
  ('Bosch'),
  ('Bayern'),
  ('Toyota'),
  ('Denim'),
  ('Levis'),
  ('Sony'),
  ('Asics');
# 03. Populate skills;
INSERT INTO skills (name) VALUES
  ('PHP'),
  ('Delphi'),
  ('Java'),
  ('C'),
  ('C++'),
  ('C#'),
  ('COBOL'),
  ('.NET'),
  ('Ruby'),
  ('Python');
# 04. Populate developers (developer could work only in 1 company);
INSERT INTO developers (firstname, lastname, company_id, salary) VALUES
  ('Peter', 'Surname', 1, 2000),
  ('Ann', 'Surname', 1, 1500),
  ('Nadia', 'Surname', 1, 1000),
  ('George', 'Surname', 2, 800),
  ('Oleg', 'Surname', 2, 2000),
  ('Andrew', 'Surname', 2, 1500),
  ('Oleksandr', 'Surname', 2, 1000),
  ('Peter', 'Surname', 3, 800),
  ('Olga', 'Surname', 3, 600),
  ('Taras', 'Surname', 3, 2000),
  ('John', 'Surname', 3, 1500),
  ('George', 'Surname', 4, 1000),
  ('Ihor', 'Surname', 4, 800),
  ('Jane', 'Surname', 4, 600),
  ('Joe', 'Surname', 4, 2000),
  ('Tom', 'Surname', 4, 1500),
  ('Yuriy', 'Surname', 5, 1000),
  ('Saskia', 'Surname', 5, 800),
  ('Kate', 'Surname', 5, 600),
  ('Glenn', 'Surname', 5, 2000),
  ('Duane', 'Surname', 5, 1500),
  ('Steve', 'Surname', 5, 1000);
# 05. Populate projects;
# Company could work on different projects;
# Customer could order different projects;
INSERT INTO projects (name, company_id, customer_id, costs) VALUES
  ('Airdis', 1, 1, 12000),
  ('Nurvis', 1, 1, 12500),
  ('CRM', 2, 2, 12400),
  ('FUD', 3, 2, 12700),
  ('Tickets', 3, 3, 12300),
  ('DB2', 4, 4, 12200),
  ('Phase5', 4, 5, 12500),
  ('Airlines', 5, 6, 12400),
  ('Service Desk', 5, 7, 12500),
  ('Monitor', 5, 7, 12000);
# 06. Populate developer's skills (1 developer --> different skills);
INSERT INTO developer_skills (developer_id, skill_id) VALUES
  (1,4),(1,5),(1,6),
  (2,1),(2,10),
  (3,2),(3,9),
  (4,3),(4,8),
  (5,7),
  (6,5),(6,6),(6,8),
  (7,1),(7,2),
  (8,3),(8,4),
  (9,8),(9,9),
  (10,10),
  (11,2),(11,3),(11,4),
  (12,1),(12,8),
  (13,5),(13,9),
  (14,8),(14,9),
  (15,9),
  (16,8),(16,9),
  (17,10),
  (18,2),(18,3),(18,10),
  (19,1),(19,6),
  (20,5),(20,6),
  (21,8),(21,9),
  (22,6);
# 07. Populate relations between projects and developers;
# 1 project --> different developers;
# 1 developer could work on different projects;
INSERT INTO developer_projects (project_id, developer_id) VALUES
  (1,1),(1,2),
  (2,2),(2,3),
  (3,4),(3,5),(3,6),(3,7),
  (4,8),(4,9),
  (5,10),(5,11),
  (6,12),(6,13),(6,14),
  (7,14),(7,15),(7,16),
  (8,17),(8,18),
  (9,19),(9,20),
  (10,21),(10,22);
