--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

alter table employees add column document_number varchar(255);
