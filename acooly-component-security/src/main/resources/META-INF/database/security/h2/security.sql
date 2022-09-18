

insert into sys_resource (id, name, type, show_state, show_mode, order_time, icon)
values (1, '系统管理', 'MENU', 0, 1, '2014-01-07', 'fa-cogs');
insert into sys_resource (id, parentid, name, type, "VALUE", show_state, show_mode, order_time, icon, descn)
values (2, 1, '账户管理', 'URL', '/manage/system/user/index.html', 0, 1, '2015-01-10 00:00:01', 'fa-user', '');
insert into sys_resource (id, parentid, name, type, "VALUE", show_state, show_mode, order_time, icon, descn)
values (3, 1, '角色管理', 'URL', '/manage/system/role/index.html', 0, 1, '2014-01-10 00:00:01', 'fa-user-circle-o', '');
insert into sys_resource (id, parentid, name, type, "VALUE", show_state, show_mode, order_time, icon, descn)
values (4, 1, '资源菜单', 'URL', '/manage/system/resource/index.html', 0, 1, '2014-01-10', 'fa-list', '');
INSERT INTO sys_resource VALUES ('2016093009', '1', '机构管理', 'URL', '0', '2014-01-09 02:39:40', '/manage/module/security/org/index.html', '1', 'fa-sitemap', null, '2017-05-26 17:51:13', '2017-05-27 13:39:38');
insert into sys_resource (id, parentid, name, type, "VALUE", show_state, show_mode, order_time, icon, descn)
values (7, 1, '桌面管理', 'URL', '/manage/system/portallet/index.html', 0, 1, '2014-01-08 02:39:40', 'fa-dashboard', '');
insert into sys_resource (id, parentid, name, type, "VALUE", show_state, show_mode, order_time, icon, descn)
values (8, 1, '连接池监控', 'URL', '/manage/druid/index.html', 0, 2, '2014-01-08 02:39:40', 'fa-eye', '');
INSERT INTO sys_resource(id, parentid, name, type, show_state, order_time, "VALUE", show_mode, icon, descn, create_time, update_time)
VALUES (2019022201, NULL, '通用功能', 'MENU', 0, '2015-10-23 18:32:04', '', 1, 'fa-th', NULL, '2019-02-22 16:39:49', '2019-02-22 16:40:00');


INSERT INTO sys_org (id, parent_id, name, status, province, city, county, mobile_no, address, contacts, telephone, email, create_time, update_time, memo)
VALUES (1, 5, 'acooly', 'valid', '重庆市', '重庆市', '渝北区', '13896177630', '重庆渝北', '张浦', '', 'zhangpu@acooly.cn', '2019-02-21 18:05:36', '2019-02-21 18:05:36', 'dev');


insert into SYS_ROLE (id, name, descn)
values (1, 'ROLE_SYSTEM', '系统管理角色');

insert into sys_role_resc (role_id, resc_id)
values (1, 1);
insert into sys_role_resc (role_id, resc_id)
values (1, 2);
insert into sys_role_resc (role_id, resc_id)
values (1, 3);
insert into sys_role_resc (role_id, resc_id)
values (1, 4);
insert into sys_role_resc (role_id, resc_id)
values (1, 7);
insert into sys_role_resc (role_id, resc_id)
values (1, 8);
insert into sys_role_resc (role_id, resc_id)
values (1, 2016093009);
insert into sys_role_resc (role_id, resc_id)
values (1, 2019022201);

INSERT INTO sys_user(id, username, real_name, pinyin, password, salt, user_type, email, mobile_no, org_id, org_name, last_modify_time, expiration_time, unlock_time, login_status, login_fail_times, login_time, status, descn, create_time, update_time)
VALUES (1, 'admin', '超级管理员', 'S', 'ab7cc1898fe24ef738595e56759b17893f2dbcc6', 'f10a5bda42097431', 1, 'zp820705@163.com', '13896177630', 1, 'acooly', '2019-02-21 18:10:23', NULL, NULL, 1, 0, NULL, 1, '', '2014-01-07 00:00:00', '2019-02-21 18:10:23');

insert into sys_user_role(role_id, user_id)
values (1, 1);

