--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

CREATE SEQUENCE  IF NOT EXISTS send_report_tasks_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE  IF NOT EXISTS scheduled_reports_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE send_report_tasks (id BIGINT NOT NULL, created_by VARCHAR(255), created_date TIMESTAMP WITHOUT TIME ZONE, deleted BOOLEAN, last_modified_by VARCHAR(255), last_modified_date TIMESTAMP WITHOUT TIME ZONE, cron_expression VARCHAR(255), time_zone VARCHAR(255), scheduled_report_id BIGINT, CONSTRAINT "send_report_tasksPK" PRIMARY KEY (id));
CREATE TABLE scheduled_reports (id BIGINT NOT NULL, created_by VARCHAR(255), created_date TIMESTAMP WITHOUT TIME ZONE, deleted BOOLEAN, last_modified_by VARCHAR(255), last_modified_date TIMESTAMP WITHOUT TIME ZONE, description TEXT, recipients JSONB, report VARCHAR(255), schedule_entries JSONB, CONSTRAINT "scheduled_reportsPK" PRIMARY KEY (id));
ALTER TABLE send_report_tasks ADD CONSTRAINT "FKkprwtlh3bffkbcrckpmvwv5nm" FOREIGN KEY (scheduled_report_id) REFERENCES scheduled_reports (id);



