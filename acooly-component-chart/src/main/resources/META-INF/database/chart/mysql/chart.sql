/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : acooly

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-10-10 11:06:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `c_chart`
-- ----------------------------
DROP TABLE IF EXISTS `c_chart`;
CREATE TABLE `c_chart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(64) DEFAULT NULL COMMENT '标题',
  `status` varchar(32) DEFAULT NULL COMMENT '状态{enable:正常,disable:禁用}',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `comments` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图表-主题';

-- ----------------------------
-- Records of c_chart
-- ----------------------------

-- ----------------------------
-- Table structure for `c_chart_data`
-- ----------------------------
DROP TABLE IF EXISTS `c_chart_data`;
CREATE TABLE `c_chart_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `chart_id` bigint(20) NOT NULL COMMENT '主题id',
  `items_id` bigint(20) NOT NULL COMMENT '图表选项id',
  `sql_data` varchar(2048) NOT NULL COMMENT 'sql表达式',
  `field_mapped` varchar(512) NOT NULL COMMENT '数据字段',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `comments` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图表-数据项';

-- ----------------------------
-- Records of c_chart_data
-- ----------------------------

-- ----------------------------
-- Table structure for `c_chart_items`
-- ----------------------------
DROP TABLE IF EXISTS `c_chart_items`;
CREATE TABLE `c_chart_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `chart_id` bigint(20) NOT NULL COMMENT '主题id',
  `title` varchar(64) NOT NULL COMMENT '标题',
  `type` varchar(64) NOT NULL COMMENT '图表类型{line:折线图,bar:柱状图,pie:饼图}',
  `status` varchar(32) NOT NULL COMMENT '状态{enable:正常,disable:禁用}',
  `loop_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '循环时间',
  `height` bigint(20) NOT NULL DEFAULT '50' COMMENT '高',
  `width` bigint(20) NOT NULL DEFAULT '50' COMMENT '宽',
  `x_shaft` varchar(128) DEFAULT NULL COMMENT 'x轴',
  `y_shaft` varchar(128) NOT NULL COMMENT 'y轴',
  `order_time` datetime NOT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `comments` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='图表-图表选项';



-- ----------------------------
-- Records of c_chart_items
-- ----------------------------
