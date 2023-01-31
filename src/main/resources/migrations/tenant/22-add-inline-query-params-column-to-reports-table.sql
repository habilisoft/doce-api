--liquibase formatted sql
--changeset schema:public

SET search_path TO ${schema};

ALTER TABLE reports ADD column inline_query_params boolean not null default false;
