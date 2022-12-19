--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

CREATE TABLE company_info ( name VARCHAR(255), CONSTRAINT "company_infoPK" PRIMARY KEY (name));
