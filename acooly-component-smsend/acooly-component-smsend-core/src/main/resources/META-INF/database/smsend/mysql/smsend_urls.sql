INSERT INTO `sys_resource` (`id`, `parentid`, `name`, `type`, `show_state`, `order_time`, `value`, `show_mode`, `icon`, `descn`, `create_time`, `update_time`)
VALUES
	(2020022209, NULL, '短信服务', 'MENU', 0, '2020-05-19 15:14:22', '', 1, 'fa-circle-o', NULL, '2020-05-19 15:13:10', '2020-05-21 13:36:47'),
	(2020022210, 2020022209, '发送应用管理', 'URL', 0, '2020-05-19 15:13:12', '/manage/smsend/smsApp/index.html', 1, 'fa-circle-o', NULL, '2020-05-19 15:14:00', '2020-05-21 22:04:48'),
	(2020022211, 2020022209, '短信模板管理', 'URL', 0, '2020-05-19 15:13:11', '/manage/smsend/smsTemplate/index.html', 1, 'fa-circle-o', NULL, '2020-05-19 15:14:22', '2020-05-21 22:04:41'),
	(2020022212, 2020022211, '短信渠道模板', 'URL', 1, '2020-05-19 15:14:50', '/manage/smsend/smsTemplateProvider/index.html', 1, 'fa-circle-o', NULL, '2020-05-19 15:14:50', '2020-05-19 15:18:33'),
	(2020022213, 2020022209, '短信黑名单', 'URL', 0, '2020-05-19 15:13:10', '/manage/module/sms/smsBlackList/index.html', 1, 'fa fa-circle-o', NULL, '2020-05-05 02:34:56', '2020-05-21 21:58:32'),
	(2020022214, 2020022209, '短信发送记录', 'URL', 0, '2015-10-01 03:10:00', '/manage/smsend/smsSendLog/index.html', 1, 'fa-circle-o', NULL, '2020-05-05 02:34:56', '2020-05-21 21:58:32');

INSERT INTO `sys_role_resc` (`role_id`, `resc_id`)
VALUES
	(1, 2020022209),
	(1, 2020022210),
	(1, 2020022211),
	(1, 2020022212),
	(1, 2020022213),
	(1, 2020022214);
