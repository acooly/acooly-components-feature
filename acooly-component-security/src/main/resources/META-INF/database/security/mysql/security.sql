/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014/1/10 0:25:53                            */
/*==============================================================*/


/*==============================================================*/
/* Table: SYS_PORTALLET                                         */
/*==============================================================*/
CREATE TABLE SYS_PORTALLET
(
   ID                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
   TITLE                VARCHAR(64) NOT NULL COMMENT '标题',
   USER_NAME			VARCHAR(32) NULL COMMENT '所属用户',
   WIDTH                INT NOT NULL COMMENT '高度',
   HEIGHT               INT NOT NULL COMMENT '宽度',
   COLLAPSIBLE          INT NOT NULL COMMENT '可否收缩{1:true,0:false}',   
   LOAD_MODE			INT NULL COMMENT '内容类型{1:url,2:html}',
   SHOW_MODE			INT NULL COMMENT '加载方式 {1:ajax,2:iframe}',
   HREF                 VARCHAR(128) COMMENT '连接地址',
   CONTENT              VARCHAR(4000) COMMENT '内容',
   `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (ID)
);

ALTER TABLE SYS_PORTALLET COMMENT '桌面管理';

/*==============================================================*/
/* Table: SYS_RESOURCE                                          */
/*==============================================================*/
CREATE TABLE SYS_RESOURCE
(
   ID                   BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
   PARENTID             BIGINT COMMENT '父主键',
   NAME                 VARCHAR(64) NOT NULL COMMENT '资源名称',
   TYPE                 VARCHAR(64) NOT NULL COMMENT '资源类型 MENU FUNC URL',
   SHOW_STATE           INT NOT NULL COMMENT '是否显示{0:显示,1:隐藏}',
   ORDER_TIME           DATETIME NOT NULL COMMENT '排序时间',
   VALUE                VARCHAR(256) COMMENT '资源值',
   SHOW_MODE            INT COMMENT '加载方式 (1:AJAXLOAD,2:IFRAME)',
   ICON                 VARCHAR(64) COMMENT '图标',
   DESCN                VARCHAR(256) COMMENT '描述',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (ID)
);

ALTER TABLE SYS_RESOURCE COMMENT '系统资源表';

/*==============================================================*/
/* Table: SYS_ROLE                                              */
/*==============================================================*/
CREATE TABLE SYS_ROLE
(
   ID                   BIGINT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(64) NOT NULL,
   ORG_ID				INT,
   DESCN                VARCHAR(256),
   `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: SYS_ROLE_RESC                                         */
/*==============================================================*/
CREATE TABLE SYS_ROLE_RESC
(
   ROLE_ID              BIGINT NOT NULL,
   RESC_ID              BIGINT NOT NULL,
   PRIMARY KEY (ROLE_ID, RESC_ID)
);

/*==============================================================*/
/* Table: SYS_USER                                              */
/*==============================================================*/
CREATE TABLE SYS_USER
(
   ID                   BIGINT NOT NULL AUTO_INCREMENT,
   USERNAME             VARCHAR(16) NOT NULL COMMENT '登录用户名',
   REAL_NAME            VARCHAR(32) COMMENT '操作员姓名',
   PASSWORD             VARCHAR(128) NOT NULL COMMENT '登录密码',
   SALT                 VARCHAR(32) COMMENT '密码加密填充值',
   USER_TYPE            INT NOT NULL COMMENT '用户类型 {1:管理员,2:操作员}',
   EMAIL                VARCHAR(64) COMMENT '电子邮件',
   MOBILE_NO            VARCHAR(32) COMMENT '手机号码',
   ORG_ID				INT COMMENT '组织ID',
   ORG_NAME				VARCHAR(128) COMMENT '组织名称(冗余)',
   LAST_MODIFY_TIME     DATETIME COMMENT '最后修改时间',
   EXPIRATION_TIME      DATETIME COMMENT '密码过期时间',
   UNLOCK_TIME          DATETIME COMMENT '解锁时间',
   LOGIN_STATUS         INT COMMENT '是否登陆{1:未登陆,2:已登陆}',
   LOGIN_FAIL_TIMES		INT COMMENT '登录失败次数',
   LOGIN_TIME           DATETIME COMMENT '登陆时间',
   STATUS               INT NOT NULL COMMENT '状态 {1:有效,2:过期,3:锁定,4:禁用}',
   DESCN                VARCHAR(256) COMMENT '描述',
   `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (ID),
   KEY (USERNAME)
);

ALTER TABLE SYS_USER COMMENT '操作员表';

/*==============================================================*/
/* Table: SYS_USER_ROLE                                         */
/*==============================================================*/
CREATE TABLE SYS_USER_ROLE
(
   ROLE_ID              BIGINT NOT NULL,
   USER_ID              BIGINT NOT NULL,
   PRIMARY KEY (ROLE_ID, USER_ID)
);

ALTER TABLE SYS_ROLE_RESC ADD CONSTRAINT FK_SYS_ROLE_RESC_SYS_RESOURCE FOREIGN KEY (RESC_ID)
      REFERENCES SYS_RESOURCE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE SYS_ROLE_RESC ADD CONSTRAINT FK_SYS_ROLE_RESC_SYS_ROLE FOREIGN KEY (ROLE_ID)
      REFERENCES SYS_ROLE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE SYS_USER_ROLE ADD CONSTRAINT FK_SYS_USER_ROLE_SYS_ROLE FOREIGN KEY (ROLE_ID)
      REFERENCES SYS_ROLE (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE SYS_USER_ROLE ADD CONSTRAINT FK_SYS_USER_ROLE_SYS_USER FOREIGN KEY (USER_ID)
      REFERENCES SYS_USER (ID) ON DELETE RESTRICT ON UPDATE RESTRICT;
      
/*==============================================================*/
/* Table: SYS_CONFIG                                            */
/*==============================================================*/
CREATE TABLE SYS_CONFIG
(
   ID                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
   TITLE                VARCHAR(64) NOT NULL COMMENT '标题',
   NAME                 VARCHAR(64) NOT NULL COMMENT '名称',
   VALUE                VARCHAR(512) COMMENT '值',
   COMMENTS             VARCHAR(512) COMMENT '备注',
   `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (ID)
);
ALTER TABLE SYS_CONFIG COMMENT '基础框架配置表';

DROP TABLE IF EXISTS `SYS_ORG`;
CREATE TABLE `SYS_ORG` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` bigint(20) NOT NULL COMMENT '父类id',
  `name` varchar(128) DEFAULT NULL COMMENT '机构名称',
  `status` varchar(32) DEFAULT NULL COMMENT '状态{valid:有效,invalid:无效}',
  `province` varchar(64) DEFAULT NULL COMMENT '省',
  `city` varchar(64) DEFAULT NULL COMMENT '市',
  `county` varchar(64) DEFAULT NULL COMMENT '县',
  `mobile_no` varchar(20) DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `contacts` varchar(64) DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(20) DEFAULT NULL COMMENT '固定电话',
  `email` varchar(64) DEFAULT NULL COMMENT '机构邮箱',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组织机构';


insert into SYS_RESOURCE (ID, PARENTID, NAME, TYPE, VALUE, SHOW_STATE, SHOW_MODE, ORDER_TIME, ICON, DESCN)
values (1, null, '系统管理', 'MENU', null, 0, 1, '2014-01-07', 'fa-cogs', null);
insert into SYS_RESOURCE (ID, PARENTID, NAME, TYPE, VALUE, SHOW_STATE, SHOW_MODE, ORDER_TIME, ICON, DESCN)
values (2, 1, '账户管理', 'URL', '/manage/system/user/index.html', 0, 1, '2015-01-10 00:00:01', 'fa-user', null);
insert into SYS_RESOURCE (ID, PARENTID, NAME, TYPE, VALUE, SHOW_STATE, SHOW_MODE, ORDER_TIME, ICON, DESCN)
values (3, 1, '角色管理', 'URL', '/manage/system/role/index.html', 0, 1, '2014-01-10 00:00:01', 'fa-user-circle-o', null);
insert into SYS_RESOURCE (ID, PARENTID, NAME, TYPE, VALUE, SHOW_STATE, SHOW_MODE, ORDER_TIME, ICON, DESCN)
values (4, 1, '资源菜单', 'URL', '/manage/system/resource/index.html', 0, 1, '2014-01-10', 'fa-list', null);
-- insert into SYS_RESOURCE (ID, PARENTID, NAME, TYPE, VALUE, SHOW_STATE, SHOW_MODE, ORDER_TIME, ICON, DESCN)
-- values (5, 1, '操作日志', 'URL', '/manage/module/olog/olog/index.html', 0, 1, '2014-01-08 02:36:49', 'icons-resource-shouzhimingxi', null);
INSERT INTO `sys_resource` VALUES ('2016093009', '1', '机构管理', 'URL', '0', '2014-01-09 02:39:40', '/manage/module/security/org/index.html', '1', 'fa-sitemap', null, '2017-05-26 17:51:13', '2017-05-27 13:39:38');
insert into SYS_RESOURCE (ID, PARENTID, NAME, TYPE, VALUE, SHOW_STATE, SHOW_MODE, ORDER_TIME, ICON, DESCN)
values (7, 1, '桌面管理', 'URL', '/manage/system/portallet/index.html', 0, 1, '2014-01-08 02:39:40', 'fa-dashboard', null);

insert into SYS_RESOURCE (ID, PARENTID, NAME, TYPE, VALUE, SHOW_STATE, SHOW_MODE, ORDER_TIME, ICON, DESCN)
values (8, 1, '连接池监控', 'URL', '/manage/druid/index.html', 0, 2, '2014-01-08 02:39:40', 'fa-eye', null);


insert into SYS_ROLE (ID, NAME, DESCN) values (1, 'ROLE_SYSTEM', '系统管理角色');

insert into SYS_ROLE_RESC (ROLE_ID, RESC_ID)
values (1, 1);
insert into SYS_ROLE_RESC (ROLE_ID, RESC_ID)
values (1, 2);
insert into SYS_ROLE_RESC (ROLE_ID, RESC_ID)
values (1, 3);
insert into SYS_ROLE_RESC (ROLE_ID, RESC_ID)
values (1, 4);
-- insert into SYS_ROLE_RESC (ROLE_ID, RESC_ID)
-- values (1, 5);
-- insert into SYS_ROLE_RESC (ROLE_ID, RESC_ID)
-- values (1, 6);
insert into SYS_ROLE_RESC (ROLE_ID, RESC_ID)
values (1, 2016093009);
insert into SYS_ROLE_RESC (ROLE_ID, RESC_ID)
values (1, 7);
insert into SYS_ROLE_RESC (ROLE_ID, RESC_ID)
values (1, 8);
insert into SYS_USER (ID, USERNAME, REAL_NAME, PASSWORD, SALT, USER_TYPE, EMAIL, MOBILE_NO, CREATE_TIME, LAST_MODIFY_TIME, EXPIRATION_TIME, UNLOCK_TIME, LOGIN_STATUS,LOGIN_FAIL_TIMES,LOGIN_TIME,STATUS, DESCN)
values (1, 'admin', '超级管理员', 'ab7cc1898fe24ef738595e56759b17893f2dbcc6', 'f10a5bda42097431', 1, 'zp820705@163.com', '13896177630', '2014-01-07', '2014-01-07', null, null, 1,0,null,1, null);

insert into SYS_USER_ROLE (ROLE_ID, USER_ID) values (1, 1);



