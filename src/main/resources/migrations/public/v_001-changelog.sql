--------------------------------------
-- TABLE plans
--------------------------------------

create table if not exists plans (
  id bigserial primary key,
  created_by varchar(255),
  created_date timestamp default now(),
  deleted bool default false,
  last_modified_by varchar(255),
  last_modified_date timestamp default now(),
  name varchar(255) not null unique,
  features text not null
);

--------------------------------------
-- TABLE plan_prices
--------------------------------------

create table if not exists plan_prices (
  id bigserial primary key,
  created_by varchar(255),
  created_date timestamp default now(),
  deleted boolean default false,
  last_modified_by varchar(255),
  last_modified_date timestamp default now(),
  plan_id int8 not null references plans(id),
  start_count int not null,
  end_count int,
  price float not null,
  constraint plan_prices_from_gt_zero_ck check(start_count > 0)
);

create index if not exists plan_prices_plan_id_idx on
    plan_prices(plan_id);

--------------------------------------
-- TABLE clients
--------------------------------------

create table if not exists clients (
  id bigserial primary key,
  created_by varchar(255),
  created_date timestamp default now(),
  deleted boolean default false,
  last_modified_by varchar(255),
  last_modified_date timestamp default now(),
  name varchar(255) not null,
  sub_domain_name varchar(255) not null unique,
  email varchar(255) not null unique ,
  active boolean not null default true,
  employees_count int8 default 0,
  plan_id int not null references plans(id)
);

--------------------------------------
-- TABLE tenant
--------------------------------------

create table if not exists tenant (
  name varchar(255) not null references clients(sub_domain_name) on update cascade,
  created_at timestamp default now(),
  unique(name)
);


--------------------------------------
-- TABLE client_devices
--------------------------------------

create table if not exists client_devices (
  id bigserial primary key,
  created_by varchar(255),
  created_date timestamp default now(),
  deleted boolean default false,
  last_modified_by varchar(255),
  last_modified_date timestamp default now(),
  serial_number varchar(255) not null unique,
  client_id int8 not null references clients(id)
);

create index if not exists client_devices_client_id_idx on
  client_devices(client_id);

--------------------------------------
-- TABLE oauth_access_token
--------------------------------------

CREATE TABLE IF NOT EXISTS oauth_access_token (
  "authentication_id" varchar(255) PRIMARY KEY,
  "authentication" bytea not null,
  "client_id" varchar(255) not null,
  "refresh_token" varchar(255) not null,
  "token" bytea,
  "token_id" varchar(255) not null,
  "user_name" varchar(255) not null
);

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
CREATE TABLE IF NOT EXISTS "oauth_refresh_token" (
  "token_id" varchar(255) PRIMARY KEY,
  "authentication" bytea,
  "token" bytea
);

-- ----------------------------
-- Table structure for permissions_groups
-- ----------------------------

CREATE TABLE IF NOT EXISTS "permissions_groups" (
  "id" bigserial primary key,
  "created_by" varchar(255),
  "created_date" timestamp default now(),
  "deleted" boolean default false,
  "last_modified_by" varchar(255),
  "last_modified_date" timestamp default now(),
  "name" varchar(255) not null unique
);

-- ----------------------------
-- Table structure for permissions
-- ----------------------------

CREATE TABLE IF NOT EXISTS permissions (
  "id" bigserial primary key,
  "created_by" varchar(255),
  "created_date" timestamp default now(),
  "deleted" boolean default false,
  "last_modified_by" varchar(255),
  "last_modified_date" timestamp default now(),
  "active" boolean default true,
  "description" varchar(255),
  "name" varchar(255) NOT NULL unique,
  "permission_group_id" int8 not null references permissions_groups(id)
);

-- ----------------------------
-- Table structure for roles
-- ----------------------------

CREATE TABLE IF NOT EXISTS "roles" (
  "id" bigserial primary key,
  "created_by" varchar(255),
  "created_date" timestamp default now(),
  "deleted" boolean default false,
  "last_modified_by" varchar(255),
  "last_modified_date" timestamp default now(),
  "active" boolean default true,
  "description" varchar(255),
  "name" varchar(255) not null unique
);

-- ----------------------------
-- Table structure for role_permissions
-- ----------------------------
CREATE TABLE IF NOT EXISTS "role_permissions" (
  "role_id" int8 NOT NULL references roles(id),
  "permission_id" int8 NOT NULL references permissions(id),
  unique(role_id, permission_id)
);

-- ----------------------------
-- Table structure for users
-- ----------------------------

CREATE TABLE IF NOT EXISTS "users" (
  "id" bigserial primary key,
  "created_by" varchar(255),
  "created_date" timestamp default now(),
  "deleted" boolean default false,
  "last_modified_by" varchar(255),
  "last_modified_date" timestamp default now(),
  "active" boolean default true,
  "change_password_at_next_logon" boolean,
  "details" varchar(255),
  "name" varchar(255) not null,
  "password" varchar(255),
  "profile_image_url" varchar(255),
  "username" varchar(255) not null unique,
  "verified" boolean
);

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
CREATE TABLE IF NOT EXISTS "user_roles" (
  "user_id" int8 NOT NULL references users(id),
  "role_id" int8 NOT NULL references roles(id),
  unique(user_id, role_id)
);

