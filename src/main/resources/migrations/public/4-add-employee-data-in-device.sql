CREATE TABLE employee_data_in_device
(
    device_sn VARCHAR(255) NOT NULL,
    enroll_id INTEGER      NOT NULL,
    CONSTRAINT pk_employee_data_in_device PRIMARY KEY (device_sn, enroll_id)
);
