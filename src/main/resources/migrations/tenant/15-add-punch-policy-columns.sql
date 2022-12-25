--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

ALTER TABLE work_shifts ADD punch_policy_meta_data JSONB;
ALTER TABLE work_shifts ADD punch_policy_type varchar(255);
