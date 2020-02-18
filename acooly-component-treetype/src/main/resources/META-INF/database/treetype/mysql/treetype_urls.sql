INSERT INTO `sys_resource`(`id`, `parentid`, `name`, `type`, `show_state`, `order_time`, `value`, `show_mode`, `icon`, `descn`, `create_time`, `update_time`)
VALUES (2020022201, NULL, '通用多级分类', 'MENU', 0, '2015-10-23 18:32:04', '', 1, 'fa-th', NULL, '2019-02-22 16:39:49', '2019-02-22 16:40:00');
insert into `sys_resource` (id, `parentid`, `name`, `type`, `value`, `show_state`, `show_mode`, `order_time`, `icon`, `descn`)
values (2019110301, 2020022201, '分类管理', 'URL', '/manage/module/treeType/treeType/index.html', 0, 1, '2019-11-02 22:53:01', 'fa-circle-o', null);
insert into `sys_role_resc` (`role_id`, `resc_id`) values (1, 2020022201);
insert into `sys_role_resc` (`role_id`, `resc_id`) values (1, 2019110301);
