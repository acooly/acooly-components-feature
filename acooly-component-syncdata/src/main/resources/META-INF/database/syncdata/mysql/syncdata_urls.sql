INSERT INTO `sys_resource` VALUES ('2023050811', null, '数据同步', 'MENU', '0', '2018-10-10 17:25:21', '', '1', 'icons-resource-app1', null, '2018-08-30 17:25:21', '2018-08-30 17:25:21');
INSERT INTO `sys_resource` VALUES ('2023050812', '2023050811', '数据同步管理', 'URL', '0', '2018-10-10 17:25:19', '/manage/syncdata/tableAsyncData/index.html', '1', 'icons-resource-tongji', null, '2018-08-30 17:26:01', '2018-10-16 17:45:05');

INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '2023050811');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '2023050812');