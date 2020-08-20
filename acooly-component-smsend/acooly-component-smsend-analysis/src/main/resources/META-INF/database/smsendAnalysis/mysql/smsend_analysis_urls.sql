INSERT INTO `sys_resource` (`id`, `parentid`, `name`, `type`, `show_state`, `order_time`, `value`, `show_mode`, `icon`, `descn`, `create_time`, `update_time`)
VALUES (2020022215, 2020022209, '短信发送日报', 'URL', 0, '2015-09-01 03:10:00', '/manage/analysis/smsSendDay/index.html', 1, 'fa-circle-o', NULL, '2020-05-05 02:34:56', '2020-05-21 21:58:32'),
(2020022216, 2020022209, '短信发送统计', 'URL', 0, '2015-08-01 03:10:00', '/manage/analysis/smsSendDay/period.html', 1, 'fa-circle-o', NULL, '2020-05-05 02:34:56', '2020-05-21 21:58:32');

INSERT INTO `sys_role_resc` (`role_id`, `resc_id`) VALUES (1, 2020022215),(1, 2020022216);
