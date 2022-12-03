--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

ALTER TABLE work_shifts ADD details JSONB;
