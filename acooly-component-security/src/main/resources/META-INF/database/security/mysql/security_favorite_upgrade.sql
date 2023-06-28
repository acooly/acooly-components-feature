CREATE TABLE `sys_user_favorite`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id`     bigint(20) unsigned NOT NULL COMMENT '用户ID',
    `resc_id`     bigint(20) unsigned NOT NULL COMMENT '资源ID',
    `sort_time`   bigint(20) NOT NULL COMMENT '排序值(倒叙)',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_USER_FAVORITE` (`user_id`,`resc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT ='收藏夹';


INSERT INTO `sys_resource` (`id`, `parentid`, `name`, `type`, `show_state`, `order_time`, `value`, `show_mode`, `icon`, `descn`, `create_time`, `update_time`)
VALUES (10, 1, '收藏夹', 'URL', 0, '2014-01-08 02:39:41', '/manage/system/userFavorite/index.html', 1, 'fa-star', NULL, '2023-06-27 22:51:53', '2023-06-28 16:42:38');

insert into `sys_role_resc` (`role_id`, `resc_id`)
values (1, 10);
