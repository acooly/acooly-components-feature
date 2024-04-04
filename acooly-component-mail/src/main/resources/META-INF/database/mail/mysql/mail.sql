CREATE TABLE `email_template`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '模板id',
    `name`        varchar(45)            DEFAULT NULL COMMENT '模版名称',
    `title`       varchar(45)            DEFAULT NULL COMMENT '模版标题',
    `subject`     varchar(1024) NOT NULL COMMENT '模板邮件主题',
    `content`     mediumtext    NOT NULL COMMENT '模板邮件内容',
    `create_time` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_name` (`name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='邮件模板表';

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


CREATE TABLE `email_sender`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `sys_user_id`    int(11) DEFAULT NULL COMMENT 'boss用户id',
    `sys_user_name`  varchar(64) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT 'boss登录名',
    `sys_real_name`  varchar(64) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT 'boss真实姓名',
    `host_name`      varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'SMTP服务器地址',
    `host_port`      int(8) DEFAULT '25' COMMENT 'SMTP服务器端口',
    `email_name`     varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱名称',
    `email_password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱密码',
    `from_address`   varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发件人邮箱',
    `from_Name`      varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发送者名称',
    `ssl_Port`       int(8) DEFAULT '465' COMMENT 'SSL链接的端口',
    `start_tls`      varchar(16) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '启动tls{yes:是,no:否}',
    `ssl_Connect`    varchar(16) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '使用SSL连接{yes:是,no:否}',
    `create_time`    timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `sys_user_id_index` (`sys_user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='邮件发件人';
