--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

alter table work_shift_details alter column break_end_time drop not null;
alter table work_shift_details alter column break_start_time drop not null;
alter table work_shift_details alter column start_time drop not null;
alter table work_shift_details alter column end_time drop not null;
