--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

alter table work_shifts add column late_grace_period int4;
