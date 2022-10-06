# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.21)
# Database: acooly-feature
# Generation Time: 2022-10-06 04:57:13 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table rbac_org
# ------------------------------------------------------------

DROP TABLE IF EXISTS `rbac_org`;

CREATE TABLE `rbac_org` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `parent_id` bigint(20) NOT NULL COMMENT '父类id',
  `code` varchar(64) DEFAULT NULL COMMENT '机构编码',
  `name` varchar(32) DEFAULT NULL COMMENT '机构名称',
  `path` varchar(128) NOT NULL COMMENT '搜索路径',
  `order_time` bigint(20) NOT NULL COMMENT '排序值',
  `province` varchar(64) DEFAULT NULL COMMENT '省',
  `city` varchar(64) DEFAULT NULL COMMENT '市',
  `district` varchar(64) DEFAULT NULL COMMENT '区/县',
  `mobile_no` varchar(20) DEFAULT NULL COMMENT '{title:’手机号码’,type:’mobile’}',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `contacts` varchar(64) DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(20) DEFAULT NULL COMMENT '固定电话',
  `email` varchar(64) DEFAULT NULL COMMENT '{title:’邮件’,type:’email’}',
  `status` varchar(16) NOT NULL COMMENT '{title:’状态’,alias:’able’}',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组织机构';



# Dump of table rbac_resource
# ------------------------------------------------------------

DROP TABLE IF EXISTS `rbac_resource`;

CREATE TABLE `rbac_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父主键',
  `name` varchar(64) NOT NULL COMMENT '资源名称',
  `value` varchar(256) DEFAULT NULL COMMENT '资源值',
  `type` varchar(64) NOT NULL COMMENT '资源类型{MENU:‘菜单’,FUNC:‘功能’, URL:‘链接’}',
  `show_status` varchar(16) NOT NULL COMMENT '{title:‘是否显示‘,alias:‘whether‘}',
  `show_mode` varchar(16) DEFAULT NULL COMMENT '加载方式 {AJAX:‘AJAX’,IFRAME:‘IFRAME’}',
  `order_time` bigint(20) NOT NULL COMMENT '排序时间',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `memo` varchar(256) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限资源表';

LOCK TABLES `rbac_resource` WRITE;
/*!40000 ALTER TABLE `rbac_resource` DISABLE KEYS */;

INSERT INTO `rbac_resource` (`id`, `parent_id`, `name`, `value`, `type`, `show_status`, `show_mode`, `order_time`, `icon`, `memo`, `create_time`, `update_time`)
VALUES
	(1,NULL,'系统管理',NULL,'MENU','yes','AJAX',123123123,NULL,NULL,'2022-10-05 23:53:28','2022-10-05 23:54:08'),
	(2,1,'用户管理','/manage/rbac/user/index.html','URL','yes','AJAX',1123123123,NULL,NULL,'2022-10-05 23:56:29','2022-10-05 23:56:29'),
	(3,1,'角色管理','/manage/rbac/role/index.html','URL','yes','AJAX',1123123123,NULL,NULL,'2022-10-05 23:56:29','2022-10-05 23:56:29'),
	(4,1,'资源管理','/manage/rbac/resource/index.html','URL','yes','AJAX',1123123123,NULL,NULL,'2022-10-05 23:56:29','2022-10-05 23:56:29'),
	(5,1,'添加用户','user:create','FUNC','yes','AJAX',1123123123,NULL,NULL,'2022-10-05 23:56:29','2022-10-05 23:56:29'),
	(6,1,'资源操作','resource:*','FUNC','yes','AJAX',12341234,NULL,NULL,'2022-10-05 23:58:53','2022-10-05 23:58:53'),
	(20,NULL,'会员管理',NULL,'MENU','yes','AJAX',1234123412,NULL,NULL,'2022-10-06 00:00:00','2022-10-06 00:00:00'),
	(21,20,'会员信息','/manage/member/index.html','URL','yes','AJAX',1234123412,NULL,NULL,'2022-10-06 00:00:57','2022-10-06 00:00:57');

/*!40000 ALTER TABLE `rbac_resource` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table rbac_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `rbac_role`;

CREATE TABLE `rbac_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL COMMENT '角色名称',
  `title` varchar(64) NOT NULL COMMENT '角色标题',
  `memo` varchar(256) DEFAULT NULL COMMENT '角色说明',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

LOCK TABLES `rbac_role` WRITE;
/*!40000 ALTER TABLE `rbac_role` DISABLE KEYS */;

INSERT INTO `rbac_role` (`id`, `name`, `title`, `memo`, `create_time`, `update_time`)
VALUES
	(1,'ROLE_ADMIN','系统管理员',NULL,'2022-10-05 23:51:19','2022-10-05 23:51:19'),
	(2,'ROLE_MEMBER','会员管理',NULL,'2022-10-06 00:01:35','2022-10-06 00:01:35');

/*!40000 ALTER TABLE `rbac_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table rbac_role_resc
# ------------------------------------------------------------

DROP TABLE IF EXISTS `rbac_role_resc`;

CREATE TABLE `rbac_role_resc` (
  `role_id` bigint(20) NOT NULL,
  `resc_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`resc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限表';

LOCK TABLES `rbac_role_resc` WRITE;
/*!40000 ALTER TABLE `rbac_role_resc` DISABLE KEYS */;

INSERT INTO `rbac_role_resc` (`role_id`, `resc_id`)
VALUES
	(1,1),
	(1,2),
	(1,3),
	(1,4),
	(1,5),
	(1,6),
	(1,20),
	(1,21);

/*!40000 ALTER TABLE `rbac_role_resc` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table rbac_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `rbac_user`;

CREATE TABLE `rbac_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(16) NOT NULL COMMENT '用户名',
  `real_name` varchar(32) DEFAULT NULL COMMENT '{title:’姓名’,type:’chinese’}',
  `pinyin` varchar(32) DEFAULT NULL COMMENT '姓名拼音',
  `password` varchar(128) NOT NULL COMMENT '登录密码',
  `salt` varchar(32) DEFAULT NULL COMMENT '密码加盐',
  `user_type` int(11) NOT NULL COMMENT '用户类型 {1:管理员,2:操作员}',
  `email` varchar(64) DEFAULT NULL COMMENT '{title:’邮件’,type:’email’}',
  `mobile_no` varchar(20) DEFAULT NULL COMMENT '{title:’手机号码’,type:’mobile’}',
  `org_id` bigint(20) DEFAULT NULL COMMENT '组织ID',
  `org_name` varchar(128) DEFAULT NULL COMMENT '组织名称',
  `expire_time` datetime DEFAULT NULL COMMENT '密码过期时间',
  `unlock_time` datetime DEFAULT NULL COMMENT '解锁时间',
  `login_fail_count` int(11) DEFAULT NULL COMMENT '登录失败次数',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` varchar(16) NOT NULL COMMENT '状态 {enable:’有效’,expired:’过期’,locked:’锁定’,disable:’禁用’}',
  `memo` varchar(256) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_rbac_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

LOCK TABLES `rbac_user` WRITE;
/*!40000 ALTER TABLE `rbac_user` DISABLE KEYS */;

INSERT INTO `rbac_user` (`id`, `username`, `real_name`, `pinyin`, `password`, `salt`, `user_type`, `email`, `mobile_no`, `org_id`, `org_name`, `expire_time`, `unlock_time`, `login_fail_count`, `login_time`, `status`, `memo`, `create_time`, `update_time`)
VALUES
	(1,'admin','张浦','zhangpu','123123123','123123',1,'zhangpu@acooly.cn','13896177630',NULL,NULL,NULL,NULL,NULL,NULL,'enable',NULL,'2022-10-05 23:50:20','2022-10-05 23:50:20');

/*!40000 ALTER TABLE `rbac_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table rbac_user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `rbac_user_role`;

CREATE TABLE `rbac_user_role` (
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';

LOCK TABLES `rbac_user_role` WRITE;
/*!40000 ALTER TABLE `rbac_user_role` DISABLE KEYS */;

INSERT INTO `rbac_user_role` (`role_id`, `user_id`)
VALUES
	(1,1),
	(2,1);

/*!40000 ALTER TABLE `rbac_user_role` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
