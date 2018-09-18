INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('1702260', NULL, '短信', 'MENU', '0', '2015-09-28 18:59:46', '', '1', 'icons-resource-app', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('17022601', '1702260', '短信发送记录', 'URL', '0', '2015-10-01 03:10:00', '/manage/module/sms/smsLog/index.html', '1', 'icons-resource-fangdaozhuomian', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('2016093012', '1702260', '短信黑名单', 'URL', '0', '2015-09-01 03:10:00', '/manage/module/sms/smsBlackList/index.html', '1', 'icons-resource-zhongjiangjilu', NULL);


INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '1702260');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '17022601');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '2016093012');
