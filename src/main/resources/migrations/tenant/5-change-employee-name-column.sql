--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

ALTER TABLE employees ADD COLUMN full_name VARCHAR(255);
UPDATE employees SET full_name = concat(first_name, ' ', last_name);
ALTER TABLE employees DROP COLUMN first_name;
ALTER TABLE employees DROP COLUMN last_name;
