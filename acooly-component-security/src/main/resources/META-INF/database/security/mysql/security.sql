/*==============================================================*/
/* Table: SYS_PORTALLET                                         */
/*==============================================================*/
CREATE TABLE sys_portallet
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title`       VARCHAR(64) NOT NULL COMMENT '标题',
    `user_name`   VARCHAR(32) NULL COMMENT '所属用户',
    `width`       INT         NOT NULL COMMENT '高度',
    `height`      INT         NOT NULL COMMENT '宽度',
    `collapsible` INT         NOT NULL COMMENT '可否收缩{1:true,0:false}',
    `load_mode`   INT         NULL COMMENT '内容类型{1:url,2:html}',
    `show_mode`   INT         NULL COMMENT '加载方式 {1:ajax,2:iframe}',
    `href`        VARCHAR(128) COMMENT '连接地址',
    `content`     VARCHAR(4000) COMMENT '内容',
    `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='桌面管理';

/*==============================================================*/
/* Table: SYS_RESOURCE                                          */
/*==============================================================*/
CREATE TABLE sys_resource
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `parentid`    BIGINT COMMENT '父主键',
    `name`        VARCHAR(64) NOT NULL COMMENT '资源名称',
    `type`        VARCHAR(64) NOT NULL COMMENT '资源类型 MENU FUNC URL',
    `show_state`  INT         NOT NULL COMMENT '是否显示{0:显示,1:隐藏}',
    `order_time`  DATETIME    NOT NULL COMMENT '排序时间',
    `value`       VARCHAR(256) COMMENT '资源值',
    `show_mode`   INT COMMENT '加载方式 (1:AJAXLOAD,2:IFRAME)',
    `icon`        VARCHAR(64) COMMENT '图标',
    `descn`       VARCHAR(256) COMMENT '描述',
    `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统资源表';

/*==============================================================*/
/* Table: SYS_ROLE                                              */
/*==============================================================*/
CREATE TABLE sys_role
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(64) NOT NULL,
    `org_id`      INT,
    `descn`       VARCHAR(256),
    `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统角色表';

/*==============================================================*/
/* Table: SYS_ROLE_RESC                                         */
/*==============================================================*/
CREATE TABLE sys_role_resc
(
    `role_id` BIGINT NOT NULL,
    `resc_id` BIGINT NOT NULL,
    PRIMARY KEY (role_id, resc_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色权限表';

/*==============================================================*/
/* Table: SYS_USER                                              */
/*==============================================================*/
CREATE TABLE `sys_user`
(
    `id`               BIGINT       NOT NULL AUTO_INCREMENT,
    `username`         VARCHAR(16)  NOT NULL COMMENT '登录用户名',
    `real_name`        VARCHAR(32) COMMENT '操作员姓名',
    `pinyin`           varchar(32) COMMENT '姓名拼音',
    `password`         VARCHAR(128) NOT NULL COMMENT '登录密码',
    `salt`             VARCHAR(32) COMMENT '密码加密填充值',
    `user_type`        INT          NOT NULL COMMENT '用户类型 {1:管理员,2:操作员}',
    `email`            VARCHAR(64) COMMENT '电子邮件',
    `mobile_no`        VARCHAR(32) COMMENT '手机号码',
    `org_id`           INT COMMENT '组织ID',
    `org_name`         VARCHAR(128) COMMENT '组织名称(冗余)',
    `last_modify_time` DATETIME COMMENT '最后修改时间',
    `expiration_time`  DATETIME COMMENT '密码过期时间',
    `unlock_time`      DATETIME COMMENT '解锁时间',
    `login_status`     INT COMMENT '是否登陆{1:未登陆,2:已登陆}',
    `login_fail_times` INT COMMENT '登录失败次数',
    `login_time`       DATETIME COMMENT '登陆时间',
    `status`           INT          NOT NULL COMMENT '状态 {1:有效,2:过期,3:锁定,4:禁用}',
    `descn`            VARCHAR(256) COMMENT '描述',
    `create_time`      timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_sys_username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='操作员表';

/*==============================================================*/
/* Table: SYS_USER_ROLE                                         */
/*==============================================================*/
CREATE TABLE `sys_user_role`
(
    `role_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    PRIMARY KEY (`role_id`, `user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='操作员表';

/*==============================================================*/
/* Table: SYS_CONFIG                                            */
/*==============================================================*/
CREATE TABLE sys_config
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title`       VARCHAR(64) NOT NULL COMMENT '标题',
    `name`        VARCHAR(64) NOT NULL COMMENT '名称',
    `value`       VARCHAR(512) COMMENT '值',
    `comment`     VARCHAR(512) COMMENT '备注',
    `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统配置';


DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `parent_id`   bigint(20) NOT NULL COMMENT '父类id',
    `name`        varchar(128) DEFAULT NULL COMMENT '机构名称',
    `status`      varchar(32)  DEFAULT NULL COMMENT '状态{valid:有效,invalid:无效}',
    `province`    varchar(64)  DEFAULT NULL COMMENT '省',
    `city`        varchar(64)  DEFAULT NULL COMMENT '市',
    `county`      varchar(64)  DEFAULT NULL COMMENT '县',
    `mobile_no`   varchar(20)  DEFAULT NULL COMMENT '手机号',
    `address`     varchar(255) DEFAULT NULL COMMENT '地址',
    `contacts`    varchar(64)  DEFAULT NULL COMMENT '联系人',
    `telephone`   varchar(20)  DEFAULT NULL COMMENT '固定电话',
    `email`       varchar(64)  DEFAULT NULL COMMENT '机构邮箱',
    `create_time` datetime     DEFAULT NULL,
    `update_time` datetime     DEFAULT NULL,
    `memo`        varchar(255) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组织机构';


INSERT INTO `sys_org`(`id`, `parent_id`, `name`, `status`, `province`, `city`, `county`, `mobile_no`, `address`, `contacts`, `telephone`, `email`, `create_time`, `update_time`, `memo`)
VALUES (1, 0, 'acooly', 'valid', '重庆市', '重庆市', '渝北区', '13896177630', '重庆渝北', '张浦', '', 'zhangpu@acooly.cn', '2019-02-21 18:05:36', '2019-02-21 18:05:36', 'dev');

insert into `sys_resource` (id, `parentid`, `name`, `type`, `value`, `show_state`, `show_mode`, `order_time`, `icon`, `descn`)
values (1, null, '系统管理', 'MENU', null, 0, 1, '2014-01-07', 'fa-cogs', null);
insert into `sys_resource` (id, `parentid`, `name`, `type`, `value`, `show_state`, `show_mode`, `order_time`, `icon`, `descn`)
values (2, 1, '账户管理', 'URL', '/manage/system/user/index.html', 0, 1, '2015-01-10 00:00:01', 'fa-user', null);
insert into `sys_resource` (id, `parentid`, `name`, `type`, `value`, `show_state`, `show_mode`, `order_time`, `icon`, `descn`)
values (3, 1, '角色管理', 'URL', '/manage/system/role/index.html', 0, 1, '2014-01-10 00:00:01', 'fa-user-circle-o', null);
insert into `sys_resource` (id, `parentid`, `name`, `type`, `value`, `show_state`, `show_mode`, `order_time`, `icon`, `descn`)
values (4, 1, '资源菜单', 'URL', '/manage/system/resource/index.html', 0, 1, '2014-01-10', 'fa-list', null);
INSERT INTO `sys_resource`
VALUES ('2016093009', '1', '机构管理', 'URL', '0', '2014-01-09 02:39:40', '/manage/module/security/org/index.html', '1', 'fa-sitemap', null, '2017-05-26 17:51:13', '2017-05-27 13:39:38');
insert into `sys_resource` (id, `parentid`, `name`, `type`, `value`, `show_state`, `show_mode`, `order_time`, `icon`, `descn`)
values (7, 1, '桌面管理', 'URL', '/manage/system/portallet/index.html', 0, 1, '2014-01-08 02:39:40', 'fa-windows', null);
insert into `sys_resource` (id, `parentid`, `name`, `type`, `value`, `show_state`, `show_mode`, `order_time`, `icon`, `descn`)
values (8, 1, '数据库监控', 'URL', '/manage/druid/index.html', 0, 2, '2014-01-08 02:39:40', 'fa-eye', null);
INSERT INTO `sys_resource` (`id`, `parentid`, `name`, `type`, `show_state`, `order_time`, `value`, `show_mode`, `icon`, `descn`, `create_time`, `update_time`)
VALUES (9, 1, '会话监控', 'URL', 0, '2014-01-08 02:39:39', '/manage/session/index.html', 1, 'fa-group', NULL, '2022-12-08 22:58:59', '2022-12-09 23:42:54');

INSERT INTO `sys_resource`(`id`, `parentid`, `name`, `type`, `show_state`, `order_time`, `value`, `show_mode`, `icon`, `descn`, `create_time`, `update_time`)
VALUES (2019022201, NULL, '通用功能', 'MENU', 0, '2015-10-23 18:32:04', '', 1, 'fa-th', NULL, '2019-02-22 16:39:49', '2019-02-22 16:40:00');

insert into SYS_ROLE (id, `name`, `descn`) values (1, 'ROLE_SYSTEM', '系统管理角色');

insert into `sys_role_resc` (`role_id`, `resc_id`) values (1, 1);
insert into `sys_role_resc` (`role_id`, `resc_id`) values (1, 2);
insert into `sys_role_resc` (`role_id`, `resc_id`) values (1, 3);
insert into `sys_role_resc` (`role_id`, `resc_id`) values (1, 4);
insert into `sys_role_resc` (`role_id`, `resc_id`) values (1, 7);
insert into `sys_role_resc` (`role_id`, `resc_id`) values (1, 8);
insert into `sys_role_resc` (`role_id`, `resc_id`) values (1, 9);
insert into `sys_role_resc` (`role_id`, `resc_id`) values (1, 2016093009);
insert into `sys_role_resc` (`role_id`, `resc_id`) values (1, 2019022201);

INSERT INTO `sys_user`(`id`, `username`, `real_name`, `pinyin`, `password`, `salt`, `user_type`, `email`, `mobile_no`, `org_id`, `org_name`, `last_modify_time`, `expiration_time`, `unlock_time`, `login_status`, `login_fail_times`, `login_time`, `status`, `descn`, `create_time`, `update_time`)
VALUES (1, 'admin', '超级管理员','S', 'ab7cc1898fe24ef738595e56759b17893f2dbcc6', 'f10a5bda42097431', 1, 'zp820705@163.com', '13896177630', 1, 'acooly', '2019-02-21 18:10:23', NULL, NULL, 1, 0, NULL, 1, '', '2014-01-07 00:00:00', '2019-02-21 18:10:23');

insert into `sys_user_role`(`role_id`, `user_id`) values (1, 1);





