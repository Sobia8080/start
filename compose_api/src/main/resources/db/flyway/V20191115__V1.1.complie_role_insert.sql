insert into sys_users values(1,'wsm','123','salt','管理员',0);
insert into sys_roles values(21,'user','用户角色',0,0);
insert into sys_permissions values(31,'user:create','用户创建',0,0);
insert into sys_users_roles values(1,1,21);
insert into sys_roles_permissions values(1,21,31);