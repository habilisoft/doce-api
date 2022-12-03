-- ----------------------------
-- Table structure for departments
-- ----------------------------
CREATE TABLE IF NOT EXISTS departments (
  id                 bigserial primary key,
  created_by         varchar(255),
  created_date       timestamp default now(),
  deleted            bool      default false,
  last_modified_by   varchar(255),
  last_modified_date timestamp default now(),
  name               varchar(255)
);

-- ----------------------------
-- Table structure for locations
-- ----------------------------
CREATE TABLE IF NOT EXISTS "locations" (
  "id"                 bigserial primary key,
  "created_by"         varchar(255),
  "created_date"       timestamp default now(),
  "deleted"            bool      default false,
  "last_modified_by"   varchar(255),
  "last_modified_date" timestamp default now(),
  "address"            varchar(255),
  "google_address"     varchar(255),
  "latitude"           float8,
  "longitude"          float8,
  "name"               varchar(255)
);

-- ----------------------------
-- Table structure for devices
-- ----------------------------
--DROP TABLE IF EXISTS "devices";
CREATE TABLE IF NOT EXISTS "devices" (
  "serial_number"      varchar(255) primary key,
  "created_by"         varchar(255),
  "created_date"       timestamp default now(),
  "deleted"            bool      default false,
  "last_modified_by"   varchar(255),
  "last_modified_date" timestamp default now(),
  "description"        varchar(255),
  "card_size"          int4,
  "firmware"           varchar(255),
  "fp_algorithm"       varchar(255),
  "fp_size"            int4,
  "log_size"           int4,
  "mac"                varchar(255),
  "model_name"         varchar(255),
  "pwd_size"           int4,
  "time"               timestamp,
  "used_card"          int4,
  "used_fp"            int4,
  "used_log"           int4,
  "used_new_log"       int4,
  "used_pwd"           int4,
  "used_user"          int4,
  "user_size"          int4,
  "location"           varchar(255),
  "active"             bool,
  "location_id"        int8 references locations (id),
  "session_id"         varchar(255),
  "connected"          bool
);

-- ----------------------------
-- Table structure for work_shifts
-- ----------------------------
CREATE TABLE IF NOT EXISTS "work_shifts" (
  "id"                 bigserial primary key,
  "created_by"         varchar(255),
  "created_date"       timestamp default now(),
  "deleted"            bool      default false,
  "last_modified_by"   varchar(255),
  "last_modified_date" timestamp default now(),
  "name"               varchar(255),
  "week_work_hours"    float4
);

-- ----------------------------
-- Table structure for work_shift_details
-- ----------------------------
CREATE TABLE IF NOT EXISTS "work_shift_details" (
  "id"                 bigserial primary key,
  "created_by"         varchar(255),
  "created_date"       timestamp default now(),
  "deleted"            bool      default false,
  "last_modified_by"   varchar(255),
  "last_modified_date" timestamp default now(),
  "break_end_time"     time NOT NULL,
  "break_start_time"   time NOT NULL,
  "end_time"           time NOT NULL,
  "start_time"         time NOT NULL,
  "day_of_week"        varchar(255),
  "work_shift_id"      int8 NOT NULL references work_shifts (id)
);


CREATE INDEX IF NOT EXISTS work_shift_details_work_shift_id
  ON work_shift_details ("work_shift_id");

-- ----------------------------
-- Table structure for employees
-- ----------------------------
CREATE TABLE IF NOT EXISTS "employees" (
  "id"                 bigserial primary key,
  "created_by"         varchar(255),
  "created_date"       timestamp default now(),
  "deleted"            bool      default false,
  "last_modified_by"   varchar(255),
  "last_modified_date" timestamp default now(),
  "enroll_id"          int unique,
  "first_name"         varchar(255) not null,
  "last_name"          varchar(255) not null,
  "external_id"        varchar(255),
  "location_id"        int8 references locations (id),
  "work_shift_id"      int8 references work_shifts (id),
  "fp_data"            text,
  "department_id"      int8 references departments (id),
  "location_type"      varchar(255)
);

create index if not exists employees_work_shift_id_idx
  on employees (work_shift_id);

-- ----------------------------
-- Table structure for time_attendance_records
-- ----------------------------
CREATE TABLE IF NOT EXISTS "time_attendance_records" (
  "id"                   bigserial primary key,
  "created_by"           varchar(255),
  "created_date"         timestamp default now(),
  "deleted"              bool      default false,
  "last_modified_by"     varchar(255),
  "last_modified_date"   timestamp default now(),
  "device_serial_number" varchar(255) references devices (serial_number),
  "employee_id"          int8 references employees (id),
  "event"                int4,
  "in_out"               varchar(255),
  "record_mode"          varchar(255),
  "time"                 timestamp,
  "event_type"           varchar(255),
  "punch_type"           varchar(255),
  "record_date"          date not null,
  "work_shift_id"        int8 references work_shifts (id)
);

-- ----------------------------
-- Indexes structure for table time_attendance_records
-- ----------------------------
CREATE INDEX IF NOT EXISTS "time_attendance_records_employee_id_record_date_idx"
  ON "time_attendance_records"
  USING btree ("record_date", employee_id, "device_serial_number");

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
CREATE TABLE IF NOT EXISTS oauth_access_token (
  "authentication_id" varchar(255) PRIMARY KEY,
  "authentication"    bytea        not null,
  "client_id"         varchar(255) not null,
  "refresh_token"     varchar(255) not null,
  "token"             bytea,
  "token_id"          varchar(255) not null,
  "user_name"         varchar(255) not null
);

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
CREATE TABLE IF NOT EXISTS "oauth_refresh_token" (
  "token_id"       varchar(255) PRIMARY KEY,
  "authentication" bytea,
  "token"          bytea
);

-- ----------------------------
-- Table structure for permissions_groups
-- ----------------------------
CREATE TABLE IF NOT EXISTS "permissions_groups" (
  "id"                 bigserial primary key,
  "created_by"         varchar(255),
  "created_date"       timestamp default now(),
  "deleted"            boolean   default false,
  "last_modified_by"   varchar(255),
  "last_modified_date" timestamp default now(),
  "name"               varchar(255) not null unique
);

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
CREATE TABLE IF NOT EXISTS permissions (
  "id"                  bigserial primary key,
  "created_by"          varchar(255),
  "created_date"        timestamp default now(),
  "deleted"             boolean   default false,
  "last_modified_by"    varchar(255),
  "last_modified_date"  timestamp default now(),
  "active"              boolean   default true,
  "description"         varchar(255),
  "name"                varchar(255) NOT NULL unique,
  "permission_group_id" int8         not null references permissions_groups (id)
);

-- ----------------------------
-- Table structure for roles
-- ----------------------------
CREATE TABLE IF NOT EXISTS "roles" (
  "id"                 bigserial primary key,
  "created_by"         varchar(255),
  "created_date"       timestamp default now(),
  "deleted"            boolean   default false,
  "last_modified_by"   varchar(255),
  "last_modified_date" timestamp default now(),
  "active"             boolean   default true,
  "description"        varchar(255),
  "name"               varchar(255) not null unique
);

-- ----------------------------
-- Table structure for role_permissions
-- ----------------------------
CREATE TABLE IF NOT EXISTS "role_permissions" (
  "role_id"       int8 NOT NULL references roles (id),
  "permission_id" int8 NOT NULL references permissions (id),
  unique (role_id, permission_id)
);

-- ----------------------------
-- Table structure for users
-- ----------------------------
CREATE TABLE IF NOT EXISTS "users" (
  "id"                            bigserial primary key,
  "created_by"                    varchar(255),
  "created_date"                  timestamp default now(),
  "deleted"                       boolean   default false,
  "last_modified_by"              varchar(255),
  "last_modified_date"            timestamp default now(),
  "active"                        boolean   default true,
  "change_password_at_next_logon" boolean,
  "details"                       varchar(255),
  "name"                          varchar(255) not null,
  "password"                      varchar(255),
  "profile_image_url"             varchar(255),
  "username"                      varchar(255) not null unique,
  "verified"                      boolean
);

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
CREATE TABLE IF NOT EXISTS "user_roles" (
  "user_id" int8 NOT NULL references users (id),
  "role_id" int8 NOT NULL references roles (id),
  unique (user_id, role_id)
);

-- ----------------------------
-- Table structure for employees_work_shifts_audit
-- ----------------------------
create table if not exists employees_work_shifts_audit (
  id            bigserial primary key,
  employee_id   int8                        not null references employees (id),
  work_shift_id int8                        not null references work_shifts (id),
  start_date    timestamp without time zone not null,
  end_date      timestamp without time zone check (end_date > start_date),

  unique (employee_id, work_shift_id, start_date, end_date)
);

create index if not exists employees_work_shifts_audit_work_shift_id_idx
  on employees_work_shifts_audit (work_shift_id);
