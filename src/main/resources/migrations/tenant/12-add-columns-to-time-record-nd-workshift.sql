--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

alter table time_attendance_records add column is_late boolean;
alter table time_attendance_records add column is_early boolean;
alter table time_attendance_records add column difference_in_seconds int4;
