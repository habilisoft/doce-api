--liquibase formatted sql
--changeset schema:public

CREATE SEQUENCE IF NOT EXISTS permit_type_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE permit_type
(
    id                 BIGINT NOT NULL,
    last_modified_by   VARCHAR(255),
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    created_by         VARCHAR(255),
    deleted            BOOLEAN,
    description        VARCHAR(255),
    CONSTRAINT pk_permit_type PRIMARY KEY (id)
);


CREATE SEQUENCE IF NOT EXISTS employee_permit_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS employee_permit_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE employee_permits
(
    id                 BIGINT NOT NULL,
    last_modified_by   VARCHAR(255),
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    created_by         VARCHAR(255),
    deleted            BOOLEAN,
    employee_id        BIGINT,
    from_date          date   NOT NULL,
    to_date            date   NOT NULL,
    permit_type_id     BIGINT,
    comment            VARCHAR(255),
    CONSTRAINT pk_employee_permits PRIMARY KEY (id)
);

ALTER TABLE employee_permits
    ADD CONSTRAINT FK_EMPLOYEE_PERMITS_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employees (id);

ALTER TABLE employee_permits
    ADD CONSTRAINT FK_EMPLOYEE_PERMITS_ON_PERMITTYPE FOREIGN KEY (permit_type_id) REFERENCES permit_type (id);

INSERT INTO permit_type (id, description) VALUES (1, 'Vacaciones');
INSERT INTO permit_type (id, description) VALUES (2, 'Enfermedad');
INSERT INTO permit_type (id, description) VALUES (3, 'Licencia por maternidad');
INSERT INTO permit_type (id, description) VALUES (4, 'Licencia por paternidad');
INSERT INTO permit_type (id, description) VALUES (5, 'Licencia por matrimonio');
INSERT INTO permit_type (id, description) VALUES (6, 'Licencia por fallecimiento');
INSERT INTO permit_type (id, description) VALUES (7, 'Licencia por accidente');
INSERT INTO permit_type (id, description) VALUES (8, 'Licencia por enfermedad de un familiar');
INSERT INTO permit_type (id, description) VALUES (9, 'Licencia por asuntos personales');
INSERT INTO permit_type (id, description) VALUES (10, 'Licencia por asuntos de la empresa');
INSERT INTO permit_type (id, description) VALUES (11, 'Otros');
