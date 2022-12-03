--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

ALTER TABLE work_shifts ADD punch_for_break boolean;
ALTER TABLE work_shifts ADD break_minutes int4;
