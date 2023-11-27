/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 127.0.0.1:3306
 Source Schema         : dms_factory_dev

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 10/11/2023 16:27:19
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for c_chart
-- ----------------------------
DROP TABLE IF EXISTS `c_chart`;
CREATE TABLE `c_chart` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                           `title` varchar(64) DEFAULT NULL COMMENT '标题',
                           `status` varchar(32) DEFAULT NULL COMMENT '状态{enable:正常,disable:禁用}',
                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                           `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                           `comments` varchar(255) DEFAULT NULL COMMENT '备注',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='图表-主题';

-- ----------------------------
-- Records of c_chart
-- ----------------------------
BEGIN;
INSERT INTO `c_chart` (`id`, `title`, `status`, `create_time`, `update_time`, `comments`) VALUES (1, '图表demo', 'enable', '2018-10-18 14:03:22', '2018-10-18 14:03:22', '');
COMMIT;

-- ----------------------------
-- Table structure for c_chart_data
-- ----------------------------
DROP TABLE IF EXISTS `c_chart_data`;
CREATE TABLE `c_chart_data` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `chart_id` bigint(20) NOT NULL COMMENT '主题id',
                                `items_id` bigint(20) NOT NULL COMMENT '图表选项id',
                                `where_data` varchar(5120) DEFAULT NULL COMMENT 'where条件数据',
                                `sql_data` varchar(5120) NOT NULL COMMENT 'sql表达式',
                                `field_mapped` varchar(5120) NOT NULL COMMENT '数据字段',
                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                `comments` varchar(255) DEFAULT NULL COMMENT '备注',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='图表-数据项';

-- ----------------------------
-- Records of c_chart_data
-- ----------------------------
BEGIN;
INSERT INTO `c_chart_data` (`id`, `chart_id`, `items_id`, `where_data`, `sql_data`, `field_mapped`, `create_time`, `update_time`, `comments`) VALUES (1, 1, 1, '[{\"defaultValue\":\"\",\"dataType\":\"date_time\",\"name\":\"时间:\",\"pullDownMap\":{},\"conditParam\":\"create_time>\'$create_time$\'  \"}]', 'select create_time, loop_time/1000 as loop_time,height,width from c_chart_items where chart_id=1  and  create_time>\'$create_time$\'  ORDER BY type ', '{\"create_time\":\"创建时间\",\"height\":\"高度\",\"loop_time\":\"循环时间\",\"width\":\"宽度\"}', '2018-10-22 16:04:34', '2023-11-10 13:14:42', NULL);
INSERT INTO `c_chart_data` (`id`, `chart_id`, `items_id`, `where_data`, `sql_data`, `field_mapped`, `create_time`, `update_time`, `comments`) VALUES (2, 1, 2, '[]', 'select create_time, loop_time/1000 as loop_time,height,width from c_chart_items where chart_id=1 ORDER BY type ', '{\"create_time\":\"创建时间\",\"height\":\"高度\",\"loop_time\":\"循环时间\",\"width\":\"宽度\"}', '2018-10-22 16:04:34', '2023-11-10 13:14:34', NULL);
INSERT INTO `c_chart_data` (`id`, `chart_id`, `items_id`, `where_data`, `sql_data`, `field_mapped`, `create_time`, `update_time`, `comments`) VALUES (3, 1, 3, '[]', 'select create_time, loop_time/1000 as loop_time,height,width from c_chart_items where chart_id=1 ORDER BY type ', '{\"height\":\"高度\",\"loop_time\":\"循环时间\",\"width\":\"宽度\"}', '2018-10-22 16:04:34', '2023-11-10 13:14:26', NULL);
INSERT INTO `c_chart_data` (`id`, `chart_id`, `items_id`, `where_data`, `sql_data`, `field_mapped`, `create_time`, `update_time`, `comments`) VALUES (4, 1, 4, '[]', 'select create_time, loop_time/1000 as loop_time,height,width from c_chart_items where chart_id=1 ORDER BY type ', '{\"height\":\"高度\",\"loop_time\":\"循环时间\",\"width\":\"宽度\"}', '2023-10-27 00:18:38', '2023-11-10 13:14:50', NULL);
INSERT INTO `c_chart_data` (`id`, `chart_id`, `items_id`, `where_data`, `sql_data`, `field_mapped`, `create_time`, `update_time`, `comments`) VALUES (5, 1, 5, '[]', 'select create_time, loop_time/1000 as 1loop_time,height as 2height,width as 3width from c_chart_items where chart_id=1 ORDER BY type ', '{\"1loop_time\":\"循环时间\",\"2height\":\"高度\",\"3width\":\"宽度\",\"create_time\":\"时间\"}', '2023-10-27 12:10:51', '2023-11-10 16:25:16', NULL);
INSERT INTO `c_chart_data` (`id`, `chart_id`, `items_id`, `where_data`, `sql_data`, `field_mapped`, `create_time`, `update_time`, `comments`) VALUES (6, 1, 6, '[]', '(select \'北京\' as province, \'999\' as num) UNION ALL \n(select \'上海\' as province, \'888\' as num) UNION ALL \n(select \'天津\' as province, \'588\' as num) UNION ALL \n(select \'重庆\' as province, \'799\' as num) UNION ALL \n(select \'四川\' as province, \'88\' as num) UNION ALL \n(select \'新疆\' as province, \'66\' as num) UNION ALL \n(select \'广东\' as province, \'33\' as num);', '{\"num\":\"虚拟示例数据\",\"province\":\"省份\"}', '2023-10-27 13:15:05', '2023-11-10 10:59:55', NULL);
INSERT INTO `c_chart_data` (`id`, `chart_id`, `items_id`, `where_data`, `sql_data`, `field_mapped`, `create_time`, `update_time`, `comments`) VALUES (7, 1, 7, '[]', '(select \'中国\' as country, \'9999\' as num) UNION ALL \n(select \'美国\' as country, \'8888\' as num) UNION ALL \n(select \'俄罗斯\' as country, \'8588\' as num) UNION ALL \n(select \'加拿大\' as country, \'7799\' as num) UNION ALL \n(select \'英国\' as country, \'5588\' as num) UNION ALL \n(select \'韩国\' as country, \'6446\' as num) UNION ALL \n(select \'新加坡\' as country, \'2366\' as num) UNION ALL \n(select \'南非\' as country, \'6556\' as num) UNION ALL \n(select \'澳大利亚\' as country, \'6336\' as num) UNION ALL \n(select \'以色列\' as country, \'9006\' as num) UNION ALL \n(select \'古巴\' as country, \'2966\' as num) UNION ALL \n(select \'巴西\' as country, \'4566\' as num) UNION ALL \n(select \'泰国\' as country, \'6677\' as num) UNION ALL \n(select \'刚果金\' as country, \'6677\' as num) UNION ALL \n(select \'马里\' as country, \'6677\' as num) UNION ALL \n(select \'秘录\' as country, \'6677\' as num) UNION ALL \n(select \'法国\' as country, \'6600\' as num) UNION ALL \n(select \'德国\' as country, \'3003\' as num);', '{\"country\":\"国家\",\"num\":\"虚拟示例数据\"}', '2023-11-10 10:25:13', '2023-11-10 12:12:29', NULL);
COMMIT;


-- ----------------------------
-- Table structure for c_chart_items
-- ----------------------------
DROP TABLE IF EXISTS `c_chart_items`;
CREATE TABLE `c_chart_items` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                 `chart_id` bigint(20) NOT NULL COMMENT '主题id',
                                 `is_show` varchar(40) DEFAULT 'NO' COMMENT '是否显示数据',
                                 `is_data_list_show` varchar(40) DEFAULT 'NO' COMMENT '是否显示列表数据和下载',
                                 `data_list_show_x_y` varchar(8) DEFAULT 'Y' COMMENT '表头数据项',
                                 `data_cache_time` bigint(20) DEFAULT '5' COMMENT '数据缓存时间',
                                 `items_style` varchar(2048) DEFAULT NULL COMMENT '图形表样式',
                                 `title` varchar(64) NOT NULL COMMENT '标题',
                                 `type` varchar(64) NOT NULL COMMENT '图表类型{line:折线图,bar:柱状图,pie:饼图}',
                                 `status` varchar(32) NOT NULL COMMENT '状态{enable:正常,disable:禁用}',
                                 `loop_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '循环时间',
                                 `height` bigint(20) NOT NULL DEFAULT '50' COMMENT '高',
                                 `width` bigint(20) NOT NULL DEFAULT '50' COMMENT '宽',
                                 `x_shaft` varchar(4096) DEFAULT NULL COMMENT 'x轴',
                                 `y_shaft` varchar(4096) NOT NULL COMMENT 'y轴',
                                 `order_time` datetime NOT NULL COMMENT '排序',
                                 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                 `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                 `comments` varchar(255) DEFAULT NULL COMMENT '备注',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='图表-图表选项';

-- ----------------------------
-- Records of c_chart_items
-- ----------------------------
BEGIN;
INSERT INTO `c_chart_items` (`id`, `chart_id`, `is_show`, `is_data_list_show`, `data_list_show_x_y`, `data_cache_time`, `items_style`, `title`, `type`, `status`, `loop_time`, `height`, `width`, `x_shaft`, `y_shaft`, `order_time`, `create_time`, `update_time`, `comments`) VALUES (1, 1, 'YES', 'YES', 'Y', 5, '{\"legendLabelShow\":\"YES\",\"titleColor\":\"#000000\",\"legendTop\":\"bottom\",\"legendAlign\":\"center\",\"titleShow\":\"YES\",\"titleAlign\":\"center\"}', '折线图demo', 'line', 'enable', 16000, 90, 100, '{\"create_time\":\"创建时间\"}', '{\"height\":\"高度\",\"loop_time\":\"循环时间\",\"width\":\"宽度\"}', '2018-10-22 16:04:34', '2018-10-22 16:04:34', '2023-11-10 13:14:42', '');
INSERT INTO `c_chart_items` (`id`, `chart_id`, `is_show`, `is_data_list_show`, `data_list_show_x_y`, `data_cache_time`, `items_style`, `title`, `type`, `status`, `loop_time`, `height`, `width`, `x_shaft`, `y_shaft`, `order_time`, `create_time`, `update_time`, `comments`) VALUES (2, 1, 'YES', 'YES', 'Y', 5, '{\"legendLabelShow\":\"YES\",\"titleColor\":\"#000000\",\"legendTop\":\"bottom\",\"legendAlign\":\"center\",\"titleShow\":\"YES\",\"titleAlign\":\"center\"}', '柱状图demo', 'bar', 'enable', 26000, 90, 100, '{\"create_time\":\"创建时间\"}', '{\"height\":\"高度\",\"loop_time\":\"循环时间\",\"width\":\"宽度\"}', '2018-10-22 16:04:34', '2018-10-22 16:04:34', '2023-11-10 13:14:34', '');
INSERT INTO `c_chart_items` (`id`, `chart_id`, `is_show`, `is_data_list_show`, `data_list_show_x_y`, `data_cache_time`, `items_style`, `title`, `type`, `status`, `loop_time`, `height`, `width`, `x_shaft`, `y_shaft`, `order_time`, `create_time`, `update_time`, `comments`) VALUES (3, 1, 'YES', 'YES', 'Y', 5, '{\"legendLabelShow\":\"YES\",\"titleColor\":\"#000000\",\"legendTop\":\"bottom\",\"legendAlign\":\"center\",\"titleShow\":\"YES\",\"titleAlign\":\"center\"}', '饼图demo', 'pie', 'enable', 36000, 90, 100, '{}', '{\"height\":\"高度\",\"loop_time\":\"循环时间\",\"width\":\"宽度\"}', '2018-10-22 16:04:34', '2018-10-22 16:04:34', '2023-11-10 13:14:26', '');
INSERT INTO `c_chart_items` (`id`, `chart_id`, `is_show`, `is_data_list_show`, `data_list_show_x_y`, `data_cache_time`, `items_style`, `title`, `type`, `status`, `loop_time`, `height`, `width`, `x_shaft`, `y_shaft`, `order_time`, `create_time`, `update_time`, `comments`) VALUES (4, 1, 'YES', 'YES', 'Y', 5, '{\"legendLabelShow\":\"YES\",\"titleColor\":\"#000000\",\"legendTop\":\"bottom\",\"legendAlign\":\"center\",\"titleShow\":\"YES\",\"titleAlign\":\"center\"}', '环形图', 'annular', 'enable', 0, 90, 100, '{}', '{\"height\":\"高度\",\"loop_time\":\"循环时间\",\"width\":\"宽度\"}', '2023-10-27 00:18:38', '2023-10-27 00:18:38', '2023-11-10 13:14:50', '');
INSERT INTO `c_chart_items` (`id`, `chart_id`, `is_show`, `is_data_list_show`, `data_list_show_x_y`, `data_cache_time`, `items_style`, `title`, `type`, `status`, `loop_time`, `height`, `width`, `x_shaft`, `y_shaft`, `order_time`, `create_time`, `update_time`, `comments`) VALUES (5, 1, 'NO', 'YES', 'Y', 5, '{\"legendLabelShow\":\"YES\",\"titleColor\":\"#000000\",\"legendTop\":\"bottom\",\"legendAlign\":\"center\",\"titleShow\":\"YES\",\"titleAlign\":\"center\"}', '柱状图-堆叠', 'bar_stack', 'enable', 0, 90, 100, '{\"create_time\":\"时间\"}', '{\"1loop_time\":\"循环时间\",\"2height\":\"高度\",\"3width\":\"宽度\"}', '2023-10-27 12:10:51', '2023-10-27 12:10:51', '2023-11-10 16:25:16', '支持-数据项标签-排序');
INSERT INTO `c_chart_items` (`id`, `chart_id`, `is_show`, `is_data_list_show`, `data_list_show_x_y`, `data_cache_time`, `items_style`, `title`, `type`, `status`, `loop_time`, `height`, `width`, `x_shaft`, `y_shaft`, `order_time`, `create_time`, `update_time`, `comments`) VALUES (6, 1, 'YES', 'YES', 'Y', 5, '{\"legendLabelShow\":\"YES\",\"titleColor\":\"#cb1010\",\"legendTop\":\"bottom\",\"legendAlign\":\"left\",\"titleShow\":\"NO\",\"titleAlign\":\"center\"}', '中国地图', 'map_China', 'enable', 0, 95, 100, '{\"province\":\"省份\"}', '{\"num\":\"虚拟示例数据\"}', '2023-10-27 13:15:05', '2023-10-27 13:15:05', '2023-11-10 10:59:55', '');
INSERT INTO `c_chart_items` (`id`, `chart_id`, `is_show`, `is_data_list_show`, `data_list_show_x_y`, `data_cache_time`, `items_style`, `title`, `type`, `status`, `loop_time`, `height`, `width`, `x_shaft`, `y_shaft`, `order_time`, `create_time`, `update_time`, `comments`) VALUES (7, 1, 'NO', 'YES', 'X', 100, '{\"legendLabelShow\":\"YES\",\"titleColor\":\"#000000\",\"legendTop\":\"bottom\",\"legendAlign\":\"left\",\"titleShow\":\"YES\",\"titleAlign\":\"center\"}', '世界地图', 'map_world', 'enable', 0, 95, 100, '{\"country\":\"国家\"}', '{\"num\":\"虚拟示例数据\"}', '2023-11-10 10:25:13', '2023-11-10 10:25:13', '2023-11-10 12:12:29', '');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
