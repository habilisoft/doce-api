--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

CREATE TABLE IF NOT EXISTS scheduled_report_groups
(
    scheduled_report_id int4,
    group_id int4
);

ALTER TABLE scheduled_report_groups
    ADD CONSTRAINT "scheduled_report_groups_report_id_fk" FOREIGN KEY (scheduled_report_id) REFERENCES scheduled_reports (id);
ALTER TABLE scheduled_report_groups
    ADD CONSTRAINT "scheduled_report_groups_group_id_fk" FOREIGN KEY (group_id) REFERENCES groups (id);

alter table scheduled_reports add previous_date_data boolean;
alter table scheduled_reports add report_format varchar(255);
