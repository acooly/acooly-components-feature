/*
 Navicat MySQL Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 127.0.0.1:3306
 Source Schema         : acooly-feature

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 28/04/2022 15:51:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for c_chart
-- ----------------------------
DROP TABLE IF EXISTS `c_chart`;
CREATE TABLE `c_chart`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态{enable:正常,disable:禁用}',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `comments` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图表-主题' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of c_chart
-- ----------------------------
INSERT INTO `c_chart` VALUES (1, '图表demo', 'enable', '2018-10-18 14:03:22', '2018-10-18 14:03:22', '');

-- ----------------------------
-- Table structure for c_chart_data
-- ----------------------------
DROP TABLE IF EXISTS `c_chart_data`;
CREATE TABLE `c_chart_data`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `chart_id` bigint(20) NOT NULL COMMENT '主题id',
  `items_id` bigint(20) NOT NULL COMMENT '图表选项id',
  `where_data` varchar(5120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'where条件数据',
  `sql_data` varchar(5120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'sql表达式',
  `field_mapped` varchar(5120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据字段',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `comments` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图表-数据项' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of c_chart_data
-- ----------------------------
INSERT INTO `c_chart_data` VALUES (1, 1, 1, '[{\"defaultValue\":\"\",\"dataType\":\"date_time\",\"name\":\"时间:\",\"pullDownMap\":{},\"conditParam\":\"create_time>\'$create_time$\'  \"}]', 'select create_time, loop_time/1000 as loop_time,height,width from c_chart_items where chart_id=1  and  create_time>\'$create_time$\'  ORDER BY type ', '{\"create_time\":\"创建时间\",\"width\":\"宽度\",\"loop_time\":\"循环时间\",\"height\":\"高度\"}', '2018-10-22 16:04:34', '2022-04-28 15:50:50', NULL);
INSERT INTO `c_chart_data` VALUES (2, 1, 2, '', 'select create_time, loop_time/1000 as loop_time,height,width from c_chart_items where chart_id=1 ORDER BY type ', '{\"create_time\":\"创建时间\",\"height\":\"高度\",\"loop_time\":\"循环时间\",\"width\":\"宽度\"}', '2018-10-22 16:04:34', '2018-10-22 16:10:31', NULL);
INSERT INTO `c_chart_data` VALUES (3, 1, 3, '', 'select create_time, loop_time/1000 as loop_time,height,width from c_chart_items where chart_id=1 ORDER BY type ', '{\"create_time\":\"创建时间\",\"height\":\"高度\",\"loop_time\":\"循环时间\",\"width\":\"宽度\"}', '2018-10-22 16:04:34', '2018-10-22 16:18:57', NULL);

-- ----------------------------
-- Table structure for c_chart_items
-- ----------------------------
DROP TABLE IF EXISTS `c_chart_items`;
CREATE TABLE `c_chart_items`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `chart_id` bigint(20) NOT NULL COMMENT '主题id',
  `is_show` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'NO' COMMENT '是否显示数据',
  `is_data_list_show` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'NO' COMMENT '是否显示列表数据和下载',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图表类型{line:折线图,bar:柱状图,pie:饼图}',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态{enable:正常,disable:禁用}',
  `loop_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '循环时间',
  `height` bigint(20) NOT NULL DEFAULT 50 COMMENT '高',
  `width` bigint(20) NOT NULL DEFAULT 50 COMMENT '宽',
  `x_shaft` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'x轴',
  `y_shaft` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'y轴',
  `order_time` datetime(0) NOT NULL COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `comments` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图表-图表选项' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of c_chart_items
-- ----------------------------
INSERT INTO `c_chart_items` VALUES (1, 1, 'YES', 'YES', '折线图demo', 'line', 'enable', 16000, 55, 65, '{\"create_time\":\"创建时间\"}', '{\"width\":\"宽度\",\"loop_time\":\"循环时间\",\"height\":\"高度\"}', '2018-10-22 16:04:34', '2018-10-22 16:04:34', '2022-04-28 15:50:50', '');
INSERT INTO `c_chart_items` VALUES (2, 1, 'YES', 'YES', '柱状图demo', 'bar', 'enable', 26000, 50, 60, '{\"create_time\":\"创建时间\"}', '{\"height\":\"高度\",\"loop_time\":\"循环时间\",\"width\":\"宽度\"}', '2018-10-22 16:04:34', '2018-10-22 16:04:34', '2018-10-22 16:10:31', '');
INSERT INTO `c_chart_items` VALUES (3, 1, 'YES', 'YES', '饼图demo', 'pie', 'enable', 36000, 40, 40, '{}', '{\"height\":\"高度\",\"loop_time\":\"循环时间\",\"width\":\"宽度\"}', '2018-10-22 16:04:34', '2018-10-22 16:04:34', '2018-10-22 16:18:57', '');

SET FOREIGN_KEY_CHECKS = 1;
