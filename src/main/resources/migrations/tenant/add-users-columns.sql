--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

alter table users add column last_login timestamp;
alter table users add column recovery_token varchar(255);
