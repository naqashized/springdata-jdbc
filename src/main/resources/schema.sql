-- drop table client;
-- drop table project;

create TABLE client (
  id     IDENTITY,
  name         VARCHAR(200)
);

create TABLE project (
  id     IDENTITY,
  name         VARCHAR(200),
  client INTEGER
);
ALTER TABLE project ADD FOREIGN KEY (client) REFERENCES client(id);