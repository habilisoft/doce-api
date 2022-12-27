--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

alter table scheduled_reports add group_id int4;

ALTER TABLE scheduled_reports
    ADD CONSTRAINT "scheduled_report_group_id_fk" FOREIGN KEY (group_id) REFERENCES groups (id);
