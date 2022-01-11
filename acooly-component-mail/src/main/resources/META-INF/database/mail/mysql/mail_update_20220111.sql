ALTER TABLE `email_template` ADD COLUMN `title` VARCHAR(45) NULL COMMENT '模版标题';

CREATE TABLE `email_record`
(
    `id`              bigint(20) NOT NULL AUTO_INCREMENT,
    `template_name`   varchar(32)            DEFAULT NULL COMMENT '模板名称',
    `template_title`  varchar(32)            DEFAULT NULL COMMENT '模板标题',
    `subject`         varchar(256)           DEFAULT NULL COMMENT '主题',
    `from_address`    varchar(64)   NOT NULL COMMENT '发送人地址',
    `from_name`       varchar(32)            DEFAULT NULL COMMENT '发送人',
    `to_address_list` varchar(1024) NOT NULL COMMENT '收件人地址列表(多个逗号分隔)',
    `create_time`     timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `comments`        varchar(255)           DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='邮件发送记录';

CREATE TABLE `email_record_content`
(
    `id`          bigint(20) NOT NULL,
    `content`     text COMMENT '邮件内容',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邮件发送内容';



INSERT INTO `sys_resource` VALUES (201704272, 2019022201, '邮件记录', 'URL', 0, '2017-04-07 14:43:55', '/manage/module/mail/emailRecord/index.html', 1, 'fa-circle-o', NULL, '2022-01-11 12:44:07', '2022-01-11 12:44:10');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201704272');
