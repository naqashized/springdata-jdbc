-- drop table client;
-- drop table project;

create TABLE client (
                        id     INT auto_increment primary key,
                        name         VARCHAR(200)
);

create TABLE project (
                         id     INT auto_increment primary key,
                         name         VARCHAR(200),
                         client INTEGER
);
ALTER TABLE project ADD FOREIGN KEY (client) REFERENCES client(id);