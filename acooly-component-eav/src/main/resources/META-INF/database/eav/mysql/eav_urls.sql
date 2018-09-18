
insert into `sys_resource` ( `ID`, `PARENTID`, `SHOW_MODE`, `ICON`, `DESCN`, `VALUE`, `TYPE`, `ORDER_TIME`, `NAME`, `SHOW_STATE`) values ( '20180815', null, '1', 'icons-resource-jiekuanbiaoxinxi', null, '', 'MENU', '2016-03-10 17:30:47', '动态数据', '0');
insert into `sys_resource` ( `ID`, `PARENTID`, `SHOW_MODE`, `ICON`, `DESCN`, `VALUE`, `TYPE`, `ORDER_TIME`, `NAME`, `SHOW_STATE`) values ( '2018081501', '20180815', '1', 'icons-resource-kehuguanli', null, '/manage/module/eav/eavSchema/index.html', 'URL', '2018-08-15 23:49:39', '模板管理', '0');
insert into `sys_resource` ( `ID`, `PARENTID`, `SHOW_MODE`, `ICON`, `DESCN`, `VALUE`, `TYPE`, `ORDER_TIME`, `NAME`, `SHOW_STATE`) values ( '2018081502', '20180815', '1', 'icons-resource-jiekuan1', null, '/manage/module/eav/eavAttribute/index.html', 'URL', '2018-08-15 16:35:52', '属性管理', '0');
insert into `sys_resource` ( `ID`, `PARENTID`, `SHOW_MODE`, `ICON`, `DESCN`, `VALUE`, `TYPE`, `ORDER_TIME`, `NAME`, `SHOW_STATE`) values ( '2018081503', '20180815', '1', 'icons-resource-dingdan1', null, '/manage/module/eav/eavEntity/index.html', 'URL', '2018-08-15 12:45:13', '数据管理', '0');

insert into `SYS_ROLE_RESC` ( `ROLE_ID`, `RESC_ID`) values ( '1', '20180815');
insert into `SYS_ROLE_RESC` ( `ROLE_ID`, `RESC_ID`) values ( '1', '2018081501');
insert into `SYS_ROLE_RESC` ( `ROLE_ID`, `RESC_ID`) values ( '1', '2018081502');
insert into `SYS_ROLE_RESC` ( `ROLE_ID`, `RESC_ID`) values ( '1', '2018081503');