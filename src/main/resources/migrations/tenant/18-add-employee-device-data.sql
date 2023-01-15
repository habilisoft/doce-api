--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

CREATE SEQUENCE IF NOT EXISTS employee_device_data_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE employee_device_data
(
    id           BIGINT NOT NULL,
    employee_id  BIGINT,
    number       INTEGER,
    device_model VARCHAR(255),
    record       TEXT,
    CONSTRAINT pk_employee_device_data PRIMARY KEY (id)
);

ALTER TABLE employee_device_data
    ADD CONSTRAINT FK_EMPLOYEE_DEVICE_DATA_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employees (id);
