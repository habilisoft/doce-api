create or replace function fnprevent_update()
  returns trigger as
$body$
    begin
        raise exception 'no update allowed';
    end;
$body$
  language plpgsql volatile
  cost 100;
