INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`, `create_time`, `update_time`)
VALUES
	(20180815, NULL, '动态表单(EAV)', 'MENU', 0, '2018-09-20 23:13:42', '', 1, 'fa-certificate', NULL, NULL, '2019-03-04 01:17:55'),
	(2018081501, 20180815, '选项管理', 'URL', 0, '2018-09-20 23:13:40', '/manage/module/eav/eavOption/index.html', 1, 'fa-circle-o', NULL, NULL, '2019-03-07 22:47:58'),
	(2018081502, 20180815, '方案管理', 'URL', 0, '2018-09-20 23:13:41', '/manage/module/eav/eavAttribute/index.html', 1, 'fa-circle-o', NULL, NULL, '2019-03-07 22:47:20'),
	(2018081503, 20180815, '数据管理', 'URL', 0, '2018-09-20 23:13:39', '/manage/module/eav/eavEntity/index.html', 1, 'fa-database', NULL, NULL, '2019-03-07 22:48:26'),
	(201808211412, 20180815, '方案定义', 'URL', 1, '2019-03-07 22:54:24', '/manage/module/eav/eavScheme/index.html', 1, 'fa-certificate', NULL, '2019-03-07 22:54:24', '2019-03-07 22:54:24'),
	(201808211413, 20180815, '分组标签', 'URL', 1, '2019-03-07 22:55:29', '/manage/module/eav/eavSchemeTag/index.html', 1, 'fa-database', NULL, '2019-03-07 22:55:29', '2019-03-07 22:55:29'),
	(201808211414, 20180815, '数据:商品管理', 'URL', 0, '2018-09-20 23:13:38', '/manage/module/eav/eavEntity/index.html?schemeId=1', 1, 'fa-star', NULL, '2019-03-10 20:07:09', '2019-03-12 12:56:40');

INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`)
VALUES
	(1, 20180815),
	(1, 2018081501),
	(1, 2018081502),
	(1, 2018081503),
	(1, 201808211412),
	(1, 201808211413),
	(1, 201808211414);
