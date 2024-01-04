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
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for c_chart
-- ----------------------------
DROP TABLE IF EXISTS `table_async_data`;
CREATE TABLE `table_async_data`
(
    `id`                 bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `type`               varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '类型',
    `table_title`         varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '表名描述',
    `table_name`         varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '表名',
    `primary_column_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主键字段名称',
    `query_type`         varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '查询类型{EQ:等于,GTE:大于等于,LTE:小于等于}\n',
    `query_column_type`  varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字段类型{type_long:long类型,type_date:时间类型}\n',
    `query_column_name`  varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字段名称',
    `query_column_value` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字段值',
    `query_rows`         bigint(20) DEFAULT NULL COMMENT '查询条数',
    `sort_time`          datetime                               DEFAULT NULL COMMENT '排序时间',
    `create_time`        datetime                               DEFAULT NULL COMMENT '创建时间',
    `update_time`        datetime                               DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='同步表数据信息';