--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

alter table work_shift_details add column selected boolean;
