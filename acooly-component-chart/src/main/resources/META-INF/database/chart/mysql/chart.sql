/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : acooly

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-10-22 16:39:59
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='图表-主题';

-- ----------------------------
-- Records of c_chart
-- ----------------------------
INSERT INTO `c_chart` VALUES ('1', '图表demo', 'enable', '2018-10-18 14:03:22', '2018-10-18 14:03:22', '');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='图表-数据项';

-- ----------------------------
-- Records of c_chart_data
-- ----------------------------
INSERT INTO `c_chart_data` VALUES ('1', '1', '1', 'select create_time, loop_time/1000 as loop_time,height,width from c_chart_items where chart_id=1 ORDER BY type ', '{\"create_time\":\"创建时间\",\"width\":\"宽度\",\"loop_time\":\"循环时间\",\"height\":\"高度\"}', '2018-10-22 16:04:34', '2018-10-22 16:23:36', null);
INSERT INTO `c_chart_data` VALUES ('2', '1', '2', 'select create_time, loop_time/1000 as loop_time,height,width from c_chart_items where chart_id=1 ORDER BY type ', '{\"create_time\":\"创建时间\",\"height\":\"高度\",\"loop_time\":\"循环时间\",\"width\":\"宽度\"}', '2018-10-22 16:04:34', '2018-10-22 16:10:31', null);
INSERT INTO `c_chart_data` VALUES ('3', '1', '3', 'select create_time, loop_time/1000 as loop_time,height,width from c_chart_items where chart_id=1 ORDER BY type ', '{\"create_time\":\"创建时间\",\"height\":\"高度\",\"loop_time\":\"循环时间\",\"width\":\"宽度\"}', '2018-10-22 16:04:34', '2018-10-22 16:18:57', null);

-- ----------------------------
-- Table structure for `c_chart_items`
-- ----------------------------
DROP TABLE IF EXISTS `c_chart_items`;
CREATE TABLE `c_chart_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `chart_id` bigint(20) NOT NULL COMMENT '主题id',
  `is_show` varchar(40) DEFAULT 'NO' COMMENT '是否显示数据',
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='图表-图表选项';

-- ----------------------------
-- Records of c_chart_items
-- ----------------------------
INSERT INTO `c_chart_items` VALUES ('1', '1', 'YES','折线图demo', 'line', 'enable', '16000', '55', '65', '{\"create_time\":\"创建时间\"}', '{\"width\":\"宽度\",\"loop_time\":\"循环时间\",\"height\":\"高度\"}', '2018-10-22 16:04:34', '2018-10-22 16:04:34', '2018-10-22 16:23:36', '');
INSERT INTO `c_chart_items` VALUES ('2', '1','YES', '柱状图demo', 'bar', 'enable', '26000', '50', '60', '{\"create_time\":\"创建时间\"}', '{\"height\":\"高度\",\"loop_time\":\"循环时间\",\"width\":\"宽度\"}', '2018-10-22 16:04:34', '2018-10-22 16:04:34', '2018-10-22 16:10:31', '');
INSERT INTO `c_chart_items` VALUES ('3', '1', 'YES','饼图demo', 'pie', 'enable', '36000', '40', '40', '{}', '{\"height\":\"高度\",\"loop_time\":\"循环时间\",\"width\":\"宽度\"}', '2018-10-22 16:04:34', '2018-10-22 16:04:34', '2018-10-22 16:18:57', '');



