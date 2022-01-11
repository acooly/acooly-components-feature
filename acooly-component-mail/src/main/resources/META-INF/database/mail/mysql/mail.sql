CREATE TABLE `email_template`
(
    `id`          bigint(20)    NOT NULL AUTO_INCREMENT COMMENT '模板id',
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



