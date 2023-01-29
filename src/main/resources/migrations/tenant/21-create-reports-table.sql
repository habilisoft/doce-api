CREATE SEQUENCE IF NOT EXISTS reports_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS reports
(
    id            BIGINT NOT NULL,
    name          VARCHAR(255),
    slug          VARCHAR(255),
    description   VARCHAR(255),
    query         TEXT,
    count_query   TEXT,
    default_order VARCHAR(255),
    ui_filters    JSONB,
    ui_columns    JSONB,
    query_filters JSONB,
    CONSTRAINT pk_reports PRIMARY KEY (id)
);
