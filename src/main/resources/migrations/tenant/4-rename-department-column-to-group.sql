--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

ALTER TABLE employees RENAME COLUMN department_id TO group_id;
