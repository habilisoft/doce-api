ALTER TABLE scheduled_reports ADD report_id BIGINT;
ALTER TABLE scheduled_reports ADD user_filters JSONB;
ALTER TABLE scheduled_reports ADD CONSTRAINT "FKs3k276yitprjd0mt6rvv3f3nh" FOREIGN KEY (report_id) REFERENCES reports (id);
ALTER TABLE scheduled_reports DROP CONSTRAINT scheduled_report_group_id_fk;
ALTER TABLE scheduled_reports DROP COLUMN group_id;
ALTER TABLE scheduled_reports DROP COLUMN report;
