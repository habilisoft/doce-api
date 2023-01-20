--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

ALTER TABLE employees ADD column active boolean not null default true;
