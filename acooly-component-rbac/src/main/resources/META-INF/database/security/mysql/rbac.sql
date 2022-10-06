CREATE TABLE rbac_resource
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `parent_id`   BIGINT COMMENT '父主键',
    `name`        VARCHAR(64) NOT NULL COMMENT '资源名称',
    `value`       VARCHAR(256) COMMENT '资源值',
    `type`        VARCHAR(64) NOT NULL COMMENT '资源类型{MENU:‘菜单’,FUNC:‘功能’, URL:‘链接’}',
    `show_status` VARCHAR(16) NOT NULL COMMENT '{title:‘是否显示‘,alias:‘whether‘}',
    `show_mode`   VARCHAR(16) COMMENT '加载方式 {AJAX:‘AJAX’,IFRAME:‘IFRAME’}',
    `order_time`  bigint(20)  NOT NULL COMMENT '排序时间',
    `icon`        VARCHAR(64) COMMENT '图标',
    `memo`        VARCHAR(256) COMMENT '描述',
    `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='权限资源表';


CREATE TABLE rbac_role
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        VARCHAR(64) NOT NULL COMMENT '角色名称',
    `title`       VARCHAR(64) NOT NULL COMMENT '角色标题',
    `memo`        VARCHAR(256) COMMENT '角色说明',
    `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';


CREATE TABLE rbac_role_resc
(
    `role_id` BIGINT NOT NULL,
    `resc_id` BIGINT NOT NULL,
    PRIMARY KEY (role_id, resc_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色权限表';


CREATE TABLE `rbac_user`
(
    `id`               BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`         VARCHAR(16)  NOT NULL COMMENT '用户名',
    `real_name`        VARCHAR(32) COMMENT '{title:’姓名’,type:’chinese’}',
    `pinyin`           varchar(32) COMMENT '姓名拼音',
    `password`         VARCHAR(128) NOT NULL COMMENT '登录密码',
    `salt`             VARCHAR(32) COMMENT '密码加盐',
    `user_type`        INT          NOT NULL COMMENT '用户类型 {1:管理员,2:操作员}',
    `email`            VARCHAR(64) COMMENT '{title:’邮件’,type:’email’}',
    `mobile_no`        VARCHAR(20) COMMENT '{title:’手机号码’,type:’mobile’}',
    `org_id`           BIGINT COMMENT '组织ID',
    `org_name`         VARCHAR(128) COMMENT '组织名称',
    `expire_time`      DATETIME COMMENT '密码过期时间',
    `unlock_time`      DATETIME COMMENT '解锁时间',
    `login_fail_count` INT COMMENT '登录失败次数',
    `login_time`       DATETIME COMMENT '最后登录时间',
    `status`           VARCHAR(16)  NOT NULL COMMENT '状态 {enable:’有效’,expired:’过期’,locked:’锁定’,disable:’禁用’}',
    `memo`             VARCHAR(256) COMMENT '描述',
    `create_time`      timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_rbac_username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';


CREATE TABLE `rbac_user_role`
(
    `role_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    PRIMARY KEY (`role_id`, `user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户角色表';


CREATE TABLE `rbac_org`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `parent_id`   bigint(20)   NOT NULL COMMENT '父类id',
    `code`        varchar(64)  DEFAULT NULL COMMENT '机构编码',
    `name`        varchar(32)  DEFAULT NULL COMMENT '机构名称',
    `path`        varchar(128) NOT NULL COMMENT '搜索路径',
    `order_time`  bigint(20)   NOT NULL COMMENT '排序值',
    `province`    varchar(64) COMMENT '省',
    `city`        varchar(64) COMMENT '市',
    `district`    varchar(64) COMMENT '区/县',
    `mobile_no`   varchar(20) COMMENT '{title:’手机号码’,type:’mobile’}',
    `address`     varchar(255) COMMENT '地址',
    `contacts`    varchar(64) COMMENT '联系人',
    `telephone`   varchar(20) COMMENT '固定电话',
    `email`       varchar(64) COMMENT '{title:’邮件’,type:’email’}',
    `status`      varchar(16)  NOT NULL COMMENT '{title:’状态’,alias:’able’}',
    `memo`        varchar(255) DEFAULT NULL COMMENT '备注',
    `create_time` timestamp    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组织机构';



