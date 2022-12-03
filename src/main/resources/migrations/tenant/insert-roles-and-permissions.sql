-- --------------------------
-- Insert default roles int roles
-- --------------------------

insert into roles (id,created_by, deleted, last_modified_by, description, name)
values (1,'boostrap', false, '1', 'Administrador', 'admin')
ON CONFLICT (name)
  DO NOTHING;
;
