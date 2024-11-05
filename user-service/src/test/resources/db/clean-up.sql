delete from users;

delete from roles;

alter sequence users_id_seq restart with 2;

alter sequence roles_id_seq restart with 2;

