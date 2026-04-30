update users set active = true where active is null;
update users set verified = true where verified is null;

alter table users alter column active set default true;
alter table users alter column verified set default true;

create index if not exists users_username_search_idx on users ((lower(trim(username))));
create index if not exists users_name_search_idx on users ((lower(trim(name))));
