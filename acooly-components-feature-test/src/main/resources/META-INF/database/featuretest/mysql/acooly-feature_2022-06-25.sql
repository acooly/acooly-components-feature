# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.21)
# Database: acooly-feature
# Generation Time: 2022-06-25 15:02:06 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table dm_customer
# ------------------------------------------------------------

DROP TABLE IF EXISTS `dm_customer`;

CREATE TABLE `dm_customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `age` tinyint(4) DEFAULT NULL COMMENT '年龄',
  `birthday` date NOT NULL COMMENT '生日',
  `gender` varchar(16) NOT NULL COMMENT '性别 {male:男,female:女}',
  `real_name` varchar(16) NOT NULL COMMENT '姓名',
  `idcard_type` varchar(18) NOT NULL COMMENT '证件类型 {cert:身份证,pass:护照,other:其他}',
  `idcard_no` varchar(48) NOT NULL COMMENT '身份证号码',
  `mobile_no` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `mail` varchar(64) DEFAULT NULL COMMENT '邮件',
  `photo` varchar(128) DEFAULT NULL COMMENT '照片',
  `photo_thum` varchar(128) DEFAULT NULL COMMENT '照片缩略图',
  `subject` varchar(64) DEFAULT NULL COMMENT '摘要',
  `customer_type` varchar(16) DEFAULT NULL COMMENT '客户类型 {normal:普通,vip:重要,sepc:特别}',
  `fee` decimal(12,2) DEFAULT NULL COMMENT '手续费',
  `content` text COMMENT '测试Text类型',
  `salary` int(11) DEFAULT NULL COMMENT '薪水(元)',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 {0:无效,1:有效}',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL,
  `comments` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='acoolycoder测试';

LOCK TABLES `dm_customer` WRITE;
/*!40000 ALTER TABLE `dm_customer` DISABLE KEYS */;

INSERT INTO `dm_customer` (`id`, `username`, `age`, `birthday`, `gender`, `real_name`, `idcard_type`, `idcard_no`, `mobile_no`, `mail`, `photo`, `photo_thum`, `subject`, `customer_type`, `fee`, `content`, `salary`, `status`, `create_time`, `update_time`, `comments`)
VALUES
	(28,'zhangpu28',21,'2016-06-08','male','苦力','cert','5102222222222222','13876764763','pu.zhang@foxmai.com','',NULL,'我就是试试而已~','normal',12.00,'',21000,1,'2016-08-22 01:17:16','2020-04-28 20:02:21','我是人1231231231231231231'),
	(34,'a1111111',39,'1978-06-03','male','张二二','cert','51022119780603641X','13897678463','','',NULL,'大法师打发是阿斯顿发阿斯顿发','vip',1.00,'',100000,0,'2017-09-20 16:35:15','2020-04-30 12:15:06',''),
	(35,'acooly1',27,'1990-01-05','male','苦乐','cert','510221199001056410','13798788781','customer1@acooly.cn','',NULL,'','normal',1.00,'',100,0,'2017-09-20 16:45:19','2020-04-28 17:56:47',''),
	(36,'acooly2',26,'1991-01-05','female','苦乐','cert','51022119910105641X','13798788781','customer2@acooly.cn','','','','normal',1.20,'',100,0,'2017-09-20 16:45:19','2020-04-28 19:56:04',''),
	(37,'acooly3',25,'1992-01-05','male','苦乐','cert','51022119920105641x','13798788781','customer3@acooly.cn','','','','normal',1.00,'',100,0,'2017-09-20 16:45:19','2020-04-30 18:07:18',''),
	(38,'acooly4',35,'1982-01-20','female','苦乐','pass','51022119820915641X','13798788781','customer4@acooly.cn','','','','vip',100.00,'',100000,0,'2017-09-20 16:45:19','2020-05-01 23:13:54','');

/*!40000 ALTER TABLE `dm_customer` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table dm_customer_basic
# ------------------------------------------------------------

DROP TABLE IF EXISTS `dm_customer_basic`;

CREATE TABLE `dm_customer_basic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `marital_status` varchar(16) DEFAULT NULL COMMENT '婚姻状况 {married:已婚, unmarried:未婚}',
  `children_count` varchar(16) DEFAULT NULL COMMENT '子女情况 {none:无,one:1个,two:2个,three:3个,more:3个以上}',
  `education_level` varchar(16) DEFAULT NULL COMMENT '教育背景(学历) {primary:小学,middle:中学,college:大专,university:本科,master:硕士,doctor:博士,other:其他}',
  `income_month` varchar(16) DEFAULT NULL COMMENT '月收入 {below3k:3000以下, bt3to5K:3000-5000(含),bt5Kto1W:5000-10000(含),bt1Wto2W:10000-20000(含),bt2Wto5W:20000-50000,above5W:50000以上}',
  `social_insurance` varchar(4) DEFAULT NULL COMMENT '社会保险 {yes:有,no:无}',
  `house_fund` varchar(4) DEFAULT NULL COMMENT '公积金 {yes:有,no:无}',
  `house_statue` varchar(16) DEFAULT NULL COMMENT '住房情况 {rental:租房,own:自有,other:其他}',
  `car_status` varchar(11) DEFAULT NULL COMMENT '是否购车  {yes:有,no:无}',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `comments` varchar(255) DEFAULT NULL COMMENT '备注',
  `hous_statue` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户基本信息';

LOCK TABLES `dm_customer_basic` WRITE;
/*!40000 ALTER TABLE `dm_customer_basic` DISABLE KEYS */;

INSERT INTO `dm_customer_basic` (`id`, `username`, `marital_status`, `children_count`, `education_level`, `income_month`, `social_insurance`, `house_fund`, `house_statue`, `car_status`, `create_time`, `update_time`, `comments`, `hous_statue`)
VALUES
	(12,'zhangpu','married','1','primary','k3_below','yes','no','rental','yes','2016-10-18 15:56:08','2016-12-26 18:53:04','',NULL),
	(14,'1232331','unmarried','2','hight_university','k10_below','no','no','own','yes','2017-09-11 23:41:09','2017-09-12 00:07:50','',NULL),
	(17,'a1232331','unmarried','11','primary','k20_below','yes','yes','rental','yes','2017-09-18 17:20:38','2017-09-18 17:20:47','',NULL),
	(18,'zhangpu1','unmarried','12','primary','k5_below','yes','yes','other','yes','2017-09-20 16:50:39','2017-09-20 16:50:39','阿斯顿发啥的',NULL);

/*!40000 ALTER TABLE `dm_customer_basic` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table dm_customer_extend
# ------------------------------------------------------------

DROP TABLE IF EXISTS `dm_customer_extend`;

CREATE TABLE `dm_customer_extend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `comments` varchar(255) DEFAULT NULL COMMENT '备注',
  `context` text COMMENT '扩展信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `dm_customer_extend` WRITE;
/*!40000 ALTER TABLE `dm_customer_extend` DISABLE KEYS */;

INSERT INTO `dm_customer_extend` (`id`, `username`, `update_time`, `create_time`, `comments`, `context`)
VALUES
	(2,'zhangpu','2016-10-13 01:56:01','2016-10-13 00:01:34','asdfasdf','');

/*!40000 ALTER TABLE `dm_customer_extend` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
