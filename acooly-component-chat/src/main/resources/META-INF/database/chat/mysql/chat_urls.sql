INSERT INTO `sys_resource` VALUES ('2016093014', null, 'IM聊天', 'MENU', '0', '2018-08-30 17:25:21', '', '1', 'icons-resource-app1', null, '2018-08-30 17:25:21', '2018-08-30 17:25:21');
INSERT INTO `sys_resource` VALUES ('2016093015', '2016093014', 'IM客服消息模板', 'URL', '0', '2018-08-30 17:25:19', '/manage/component/chat/chatInfoTemplate/index.html', '1', 'icons-resource-jiekuan1', null, '2018-08-30 17:26:01', '2018-08-31 18:38:18');
INSERT INTO `sys_resource` VALUES ('2016093016', '2016093014', 'IM用户管理', 'URL', '0', '2018-08-30 17:25:20', '/manage/component/chat/chatUser/index.html', '1', 'icons-resource-kehuguanli', null, '2018-08-30 17:26:11', '2018-08-31 18:38:15');


INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '2016093014');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '2016093015');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '2016093016');