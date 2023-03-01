SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for rbac_org
-- ----------------------------
CREATE TABLE `rbac_org`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `parent_id`   bigint(20) NOT NULL COMMENT '父类id',
    `code`        varchar(64)           DEFAULT NULL COMMENT '机构编码',
    `name`        varchar(32)           DEFAULT NULL COMMENT '机构名称',
    `path`        varchar(128) NOT NULL COMMENT '搜索路径',
    `order_time`  bigint(20) NOT NULL COMMENT '排序值',
    `province`    varchar(64)           DEFAULT NULL COMMENT '省',
    `city`        varchar(64)           DEFAULT NULL COMMENT '市',
    `district`    varchar(64)           DEFAULT NULL COMMENT '区/县',
    `mobile_no`   varchar(20)           DEFAULT NULL COMMENT '{title:’手机号码’,type:’mobile’}',
    `address`     varchar(255)          DEFAULT NULL COMMENT '地址',
    `contacts`    varchar(64)           DEFAULT NULL COMMENT '联系人',
    `telephone`   varchar(20)           DEFAULT NULL COMMENT '固定电话',
    `email`       varchar(64)           DEFAULT NULL COMMENT '{title:’邮件’,type:’email’}',
    `status`      varchar(16)  NOT NULL COMMENT '{title:’状态’,alias:’able’}',
    `memo`        varchar(255)          DEFAULT NULL COMMENT '备注',
    `create_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='组织机构';

-- ----------------------------
-- Table structure for rbac_resource
-- ----------------------------
CREATE TABLE `rbac_resource`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `parent_id`   bigint(20) DEFAULT NULL COMMENT '父主键',
	`code`        varchar(128) NOT NULL COMMENT '资源code',
    `name`        varchar(64) NOT NULL COMMENT '资源名称',
    `value`       varchar(256)         DEFAULT NULL COMMENT '资源值',
    `type`        varchar(64) NOT NULL COMMENT '资源类型{MENU:‘菜单’,FUNC:‘功能’, URL:‘链接’}',
    `show_status` varchar(16) NOT NULL COMMENT '{title:‘是否显示‘,alias:‘whether‘}',
    `show_mode`   varchar(16)          DEFAULT NULL COMMENT '加载方式 {AJAX:‘AJAX’,IFRAME:‘IFRAME’}',
    `order_time`  bigint(20) NOT NULL COMMENT '排序时间',
    `icon`        varchar(64)          DEFAULT NULL COMMENT '图标',
    `memo`        varchar(256)         DEFAULT NULL COMMENT '描述',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='权限资源表';

-- ----------------------------
-- Table structure for rbac_role
-- ----------------------------
CREATE TABLE `rbac_role`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(64) NOT NULL COMMENT '角色名称',
    `title`       varchar(64) NOT NULL COMMENT '角色标题',
    `memo`        varchar(256)         DEFAULT NULL COMMENT '角色说明',
	`role_type`        varchar(256)         DEFAULT NULL COMMENT '角色类型 {BPULIC:‘共有’,PRIVATE:‘私有’}',
	`belong_code`        varchar(256)         DEFAULT NULL COMMENT '角色所属三方用户code（当role_type是私有时有效）',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Table structure for rbac_role_resc
-- ----------------------------
CREATE TABLE `rbac_role_resc`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id`     bigint(20) NOT NULL,
    `resc_id`     bigint(20) NOT NULL,
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COMMENT='角色权限表';

-- ----------------------------
-- Table structure for rbac_user
-- ----------------------------
CREATE TABLE `rbac_user`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`         varchar(16)  NOT NULL COMMENT '用户名',
    `member_no`        varchar(64)           DEFAULT NULL COMMENT '会员编码',
    `real_name`        varchar(32)           DEFAULT NULL COMMENT '{title:’姓名’,type:’chinese’}',
    `pinyin`           varchar(32)           DEFAULT NULL COMMENT '姓名拼音',
    `password`         varchar(128) NOT NULL COMMENT '登录密码',
    `salt`             varchar(32)           DEFAULT NULL COMMENT '密码加盐',
    `user_type`        int(11) NOT NULL COMMENT '用户类型 {1:管理员,2:操作员}',
    `email`            varchar(64)           DEFAULT NULL COMMENT '{title:’邮件’,type:’email’}',
    `mobile_no`        varchar(20)           DEFAULT NULL COMMENT '{title:’手机号码’,type:’mobile’}',
    `org_id`           bigint(20) DEFAULT NULL COMMENT '组织ID',
    `org_name`         varchar(128)          DEFAULT NULL COMMENT '组织名称',
    `expire_time`      datetime              DEFAULT NULL COMMENT '密码过期时间',
    `unlock_time`      datetime              DEFAULT NULL COMMENT '解锁时间',
    `login_fail_count` int(11) DEFAULT NULL COMMENT '登录失败次数',
    `login_time`       datetime              DEFAULT NULL COMMENT '最后登录时间',
    `status`           varchar(16)  NOT NULL COMMENT '状态 {enable:’有效’,expired:’过期’,locked:’锁定’,disable:’禁用’}',
    `memo`             varchar(256)          DEFAULT NULL COMMENT '描述',
    `create_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_rbac_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Table structure for rbac_user_role
-- ----------------------------
CREATE TABLE `rbac_user_role`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id`     bigint(20) NOT NULL,
    `user_id`     bigint(20) NOT NULL,
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';

SET
FOREIGN_KEY_CHECKS = 1;
