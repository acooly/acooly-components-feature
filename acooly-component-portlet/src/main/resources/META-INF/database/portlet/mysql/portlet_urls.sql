INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('1702261', NULL, '网站管理', 'MENU', '0', '2015-09-28 18:59:46', '', '1', 'icons-resource-shouye', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('17022611', '1702261', '网站配置', 'URL', '0', '2015-10-01 03:15:00', '/manage/module/portlet/siteConfig/index.html', '1', 'icons-resource-shezhi', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('17022612', '1702261', '客户反馈', 'URL', '0', '2015-10-01 03:14:00', '/manage/module/portlet/feedback/index.html', '1', 'icons-resource-fangdaozhuomian', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('17022613', '1702261', '访问记录', 'URL', '0', '2015-10-01 03:13:00', '/manage/module/portlet/actionLog/index.html', '1', 'icons-resource-anli1', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('17022614', '1702261', '访问映射', 'URL', '0', '2015-10-01 03:12:00', '/manage/module/portlet/actionMapping/index.html', '1', 'icons-resource-icon', NULL);


INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '1702261');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '17022611');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '17022612');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '17022613');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '17022614');
