--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

CREATE SEQUENCE IF NOT EXISTS locations_id_seq;
CREATE SEQUENCE IF NOT EXISTS groups_id_seq;
CREATE SEQUENCE IF NOT EXISTS employee_id_seq;
CREATE SEQUENCE IF NOT EXISTS time_attendance_log_id_seq;
CREATE SEQUENCE IF NOT EXISTS user_id_pk_sequence;
CREATE SEQUENCE IF NOT EXISTS work_shift_details_id_seq;
CREATE SEQUENCE IF NOT EXISTS work_shifts_id_seq;
