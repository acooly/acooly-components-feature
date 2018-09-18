INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('20151024', NULL, 'CMS', 'MENU', '0', '2015-10-23 18:32:05', '', '1', 'icons-resource-caozuorizhichaxun', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('201510241', '20151024', '类型管理', 'URL', '0', '2015-10-25 01:14:31', '/manage/module/cms/contentType/index.html', '1', 'icons-resource-iconfontcolor28', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('20151025', '20151024', '文章', 'MENU', '0', '2015-10-23 18:32:05', '', '1', 'icons-resource-jiekuan1', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('201510252', '20151025', '服务协议', 'URL', '0', '2015-10-25 05:14:39', '/manage/module/cms/content/index.html?code=agreement', '1', 'icons-resource-hezuo', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('201510253', '20151025', '关于我们', 'URL', '0', '2015-10-25 07:14:41', '/manage/module/cms/content/index.html?code=aboutus', '1', 'icons-resource-aboutus', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('201510254', '20151025', '联系我们', 'URL', '0', '2015-10-25 06:14:41', '/manage/module/cms/content/index.html?code=contactus', '1', 'icons-resource-6', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('201510255', '20151025', '常见问题', 'URL', '0', '2015-10-25 04:14:41', '/manage/module/cms/content/index.html?code=faq', '1', 'icons-resource-changjianwenti', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('201510256', '20151025', '新闻动态', 'URL', '0', '2015-10-25 03:14:40', '/manage/module/cms/content/index.html?code=news', '1', 'icons-resource-anli1', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('201510257', '20151025', '最新公告', 'URL', '0', '2015-10-25 02:14:40', '/manage/module/cms/content/index.html?code=notice', '1', 'icons-resource-notice', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('20151026', '20151024', '横幅', 'MENU', '0', '2015-10-23 18:32:05', '', '1', 'icons-resource-banner1', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('201510261', '20151026', '首页横幅', 'URL', '0', '2015-10-25 02:14:40', '/manage/module/cms/content/index.html?code=indexBanner&cmsType=banner', '1', 'icons-resource-banner', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('201510262', '20151026', '友情链接', 'URL', '0', '2015-10-25 02:14:40', '/manage/module/cms/content/index.html?code=friend&cmsType=banner', '1', 'icons-resource-friend', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('201510263', '20151026', '合作伙伴', 'URL', '0', '2015-10-25 02:14:40', '/manage/module/cms/content/index.html?code=partner&cmsType=banner', '1', 'icons-resource-partner', NULL);

INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`) VALUES ('2016093008', '20151024', '编码管理', 'URL', '0', '2017-04-26 02:14:40', '/manage/module/cms/cmsCode/index.html', '1', 'icons-resource-choujiang2', NULL);



INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '20151024');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201510241');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '20151025');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201510252');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201510253');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201510254');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201510255');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201510256');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201510257');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '20151026');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201510261');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201510262');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201510263');

INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '2016093008');