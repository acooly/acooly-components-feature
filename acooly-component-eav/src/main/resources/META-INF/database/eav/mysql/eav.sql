CREATE TABLE `eav_option` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父ID',
  `path` varchar(255) DEFAULT NULL,
  `code` varchar(64) NOT NULL COMMENT '编码',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `sort_time` bigint(20) DEFAULT NULL COMMENT '排序值',
  `status` varchar(16) NOT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

INSERT INTO `eav_option` (`id`, `parent_id`, `path`, `code`, `name`, `sort_time`, `status`, `create_time`, `update_time`, `memo`)
VALUES
	(1, NULL, '/', 'color', '颜色', 1551970787539, 'enable', '2019-03-07 22:59:48', '2019-03-07 23:18:19', ''),
	(2, 1, '/1/', 'red', '红色', 1551971954452, 'enable', '2019-03-07 23:19:14', '2019-03-07 23:19:14', NULL),
	(3, 1, '/1/', 'blue', '蓝色', 1551971954453, 'enable', '2019-03-07 23:19:14', '2019-03-07 23:19:14', NULL),
	(4, 1, '/1/', 'green', '绿色', 1551971954454, 'enable', '2019-03-07 23:19:14', '2019-03-07 23:19:14', NULL),
	(5, 1, '/1/', 'yellow', '黄色', 1551971954455, 'enable', '2019-03-07 23:19:14', '2019-03-07 23:19:14', NULL),
	(6, NULL, '/', 'goods_type', '商品类型', 1551971975540, 'enable', '2019-03-07 23:19:36', '2019-03-07 23:19:36', ''),
	(7, 6, '/6/', 'physical', '实物', 1551972147283, 'enable', '2019-03-07 23:22:27', '2019-03-07 23:22:27', NULL),
	(8, 6, '/6/', 'virtual', '虚拟', 1551972147284, 'enable', '2019-03-07 23:22:27', '2019-03-07 23:22:27', NULL),
	(9, NULL, '/', 'unit', '单位', 1551972178507, 'enable', '2019-03-07 23:22:59', '2019-03-10 02:46:37', ''),
	(10, 9, '/9/', 'pack', '包', 1552360661503, 'enable', '2019-03-07 23:23:21', '2019-03-12 11:17:42', NULL),
	(11, 9, '/9/', 'one', '个', 1552360661504, 'enable', '2019-03-07 23:23:21', '2019-03-12 11:17:42', NULL),
	(12, 9, '/9/', 'case', '箱', 1552360661505, 'enable', '2019-03-07 23:25:13', '2019-03-12 11:17:42', NULL),
	(13, 9, '/9/', '123', '啊啊', 1552360661506, 'enable', '2019-03-12 11:17:42', '2019-03-12 11:17:42', NULL);


CREATE TABLE `eav_scheme` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL COMMENT '方案名称',
  `title` varchar(128) NOT NULL DEFAULT '' COMMENT '方案标题',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `memo` varchar(128) DEFAULT NULL COMMENT '备注',
  `panel_width` int(11) DEFAULT NULL COMMENT '弹出框宽',
  `panel_height` int(11) DEFAULT NULL COMMENT '弹出框高',
  `permission` int(11) DEFAULT NULL COMMENT '权限值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_EAV_SCHEME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='方案定义';

INSERT INTO `eav_scheme` (`id`, `name`, `title`, `create_time`, `update_time`, `memo`, `panel_width`, `panel_height`, `permission`)
VALUES
	(1, 'goodsInfo', '商品信息', '2018-10-12 00:47:54', '2019-03-12 11:19:33', '商品信息', 500, 650, 31),
	(2, 'customerInfo', '客户信息', '2019-03-03 19:06:25', '2019-03-11 22:55:41', '', 500, 600, 7);

CREATE TABLE `eav_scheme_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `scheme_id` bigint(20) NOT NULL COMMENT '方案ID',
  `tag` varchar(64) NOT NULL COMMENT '标签',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='方案标签';

INSERT INTO `eav_scheme_tag` (`id`, `scheme_id`, `tag`, `create_time`, `update_time`, `memo`)
VALUES
	(1, 1, '基本信息', '2019-03-10 12:24:42', '2019-03-10 12:24:42', NULL),
	(2, 1, '扩展信息', '2019-03-10 12:28:00', '2019-03-10 12:28:00', NULL),
	(3, 1, '附属信息', '2019-03-10 12:28:21', '2019-03-10 12:28:21', NULL);


CREATE TABLE `eav_attribute` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `scheme_id` bigint(20) NOT NULL COMMENT '方案id',
  `scheme_name` varchar(128) DEFAULT NULL COMMENT '方案名称',
  `name` varchar(128) NOT NULL COMMENT '属性名称',
  `display_name` varchar(128) NOT NULL COMMENT '展示名称',
  `tag` varchar(64) DEFAULT NULL,
  `attribute_type` varchar(20) DEFAULT NULL COMMENT '属性类型 {LONG:整形,DOUBLE:浮点数,ENUM:枚举,STRING:字符串,BOOLEAN:布尔,DATE:时间}',
  `keyable` varchar(16) DEFAULT NULL COMMENT '是否主键(0:false,1:true)',
  `nullable` varchar(16) DEFAULT NULL COMMENT '是否为空(0:false,1:true)',
  `minimum` int(10) DEFAULT NULL COMMENT '最小值',
  `maximum` int(10) DEFAULT NULL COMMENT '最大值',
  `min_length` int(5) DEFAULT NULL COMMENT '最小长度',
  `max_length` int(5) DEFAULT NULL COMMENT '最大长度',
  `regex` varchar(128) DEFAULT NULL COMMENT '正则表达式',
  `regex_message` varchar(64) DEFAULT NULL COMMENT '正则验证错误消息',
  `show_type` int(11) DEFAULT NULL COMMENT '显示类型{1:列表,2:创建,3:编辑,4:查看}',
  `show_format` varchar(32) DEFAULT NULL COMMENT '显示格式',
  `enum_value` varchar(128) DEFAULT NULL COMMENT '枚举值定义',
  `default_value` varchar(255) DEFAULT NULL COMMENT '默认值',
  `sort_time` bigint(20) DEFAULT NULL COMMENT '排序值',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `memo` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `eav_shemaId_name_idx` (`scheme_id`,`name`),
  UNIQUE KEY `eav_shemaId_displayName_idx` (`scheme_id`,`display_name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

INSERT INTO `eav_attribute` (`id`, `scheme_id`, `scheme_name`, `name`, `display_name`, `tag`, `attribute_type`, `keyable`, `nullable`, `minimum`, `maximum`, `min_length`, `max_length`, `regex`, `regex_message`, `show_type`, `show_format`, `enum_value`, `default_value`, `sort_time`, `create_time`, `update_time`, `memo`)
VALUES
	(5, 1, 'goodsInfo', 'name', '商品名称', '基本信息', 'STRING', 'no', 'no', 0, 0, 2, 64, '', '', 7, 'NORMAL', '', '', -483, '2019-03-03 22:53:14', '2019-03-12 11:19:11', ''),
	(6, 1, 'goodsInfo', 'code', '编码', '基本信息', 'STRING', 'yes', 'no', 0, 0, 0, 16, '', '', 23, 'NORMAL', '', '', -563, '2019-03-03 23:41:47', '2019-03-12 11:18:57', ''),
	(7, 1, 'goodsInfo', 'inventory', '库存', '基本信息', 'NUMBER_INTEGER', 'yes', 'no', 0, 100, 0, 0, '', '', 14, 'NORMAL', '', '0', -388, '2019-03-03 23:42:52', '2019-03-10 12:27:05', ''),
	(8, 1, 'goodsInfo', 'price', '价格', '基本信息', 'NUMBER_MONEY', 'no', 'no', 1, 10000, 0, 0, '', '', 14, 'NORMAL', '', '0', -358, '2019-03-03 23:43:55', '2019-03-10 12:26:14', ''),
	(9, 1, 'goodsInfo', 'unit', '单位', '扩展信息', 'ENUM', 'no', 'no', 0, 0, 0, 0, '', '', 14, 'NORMAL', 'unit', 'cup', -301, '2019-03-03 23:47:59', '2019-03-10 12:28:00', ''),
	(10, 1, 'goodsInfo', 'comments', '备注', '', 'STRING', 'no', 'yes', 0, 0, 0, 255, '', '', 14, 'NORMAL', '', '', -72, '2019-03-03 23:55:03', '2019-03-10 15:57:16', ''),
	(11, 1, 'goodsInfo', 'desc', '商品说明', '', 'STRING', 'no', 'yes', 0, 0, 0, 255, '', '', 14, 'NORMAL', '', '', -150, '2019-03-04 01:56:26', '2019-03-10 15:57:08', ''),
	(12, 1, 'goodsInfo', 'goodsType', '品类', '扩展信息', 'ENUM', 'no', 'no', 0, 0, 0, 0, '', '', 15, 'NORMAL', 'goods_type', '', -213, '2019-03-04 01:58:20', '2019-03-10 12:41:23', ''),
	(13, 2, 'customerInfo', 'customerNo', '客户编码', NULL, 'STRING', 'yes', 'no', NULL, NULL, 16, 32, '', '', 15, 'NORMAL', '', '', 1, '2019-03-04 01:59:43', '2019-03-09 13:28:17', ''),
	(14, 1, 'goodsInfo', 'upTime', '上架时间', '扩展信息', 'DATE', 'no', 'yes', 0, 0, 0, 32, '', '', 31, 'FORMAT_DATA_TIME', '', '', -250, '2019-03-10 01:02:34', '2019-03-10 12:41:23', '');

CREATE TABLE `eav_entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '实体ID',
  `scheme_id` bigint(20) NOT NULL COMMENT '方案ID',
  `scheme_title` varchar(128) NOT NULL COMMENT '方案标题',
  `scheme_name` varchar(128) NOT NULL COMMENT '方案名称',
  `value` json DEFAULT NULL COMMENT '实体内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

INSERT INTO `eav_entity` (`id`, `scheme_id`, `scheme_title`, `scheme_name`, `value`, `create_time`, `update_time`, `memo`)
VALUES
	(4, 1, '商品信息', 'goodsInfo', CONVERT(X'7B22636F6465223A20223333333333333333222C202264657363223A2022323334323334222C20226E616D65223A2022333333333333333333333333222C2022756E6974223A20227061636B222C20227072696365223A2022322E3434222C2022757054696D65223A2022323031392D30332D32302030303A32323A3436222C2022636F6D6D656E7473223A20223133343234222C2022676F6F647354797065223A2022706879736963616C222C2022696E76656E746F7279223A20223232227D' using utf8mb4), '2019-03-09 16:08:02', '2019-03-12 00:22:52', NULL),
	(6, 1, '商品信息', 'goodsInfo', CONVERT(X'7B22636F6465223A20223434343434343434222C202264657363223A202234343434222C20226E616D65223A202234343434343434343434222C2022756E6974223A20226F6E65222C20227072696365223A202234342E3030222C2022757054696D65223A2022323031392D30332D31342031383A31393A3138222C2022636F6D6D656E7473223A2022343434222C2022676F6F647354797065223A20227669727475616C222C2022696E76656E746F7279223A202234227D' using utf8mb4), '2019-03-10 00:23:53', '2019-03-12 11:49:55', NULL),
	(8, 2, '客户信息', 'customerInfo', CONVERT(X'7B22637573746F6D65724E6F223A20223131323132313231323132313231323132313232227D' using utf8mb4), '2019-03-10 18:55:02', '2019-03-12 11:01:56', NULL),
	(10, 1, '商品信息', 'goodsInfo', CONVERT(X'7B22636F6465223A202236363636222C202264657363223A2022363636222C20226E616D65223A2022363636222C2022756E6974223A202263617365222C20227072696365223A202236362E3030222C2022757054696D65223A2022323031392D30332D32372031313A35303A3037222C2022636F6D6D656E7473223A202236363636222C2022676F6F647354797065223A20227669727475616C222C2022696E76656E746F7279223A202236227D' using utf8mb4), '2019-03-12 11:50:16', '2019-03-12 13:00:43', NULL);

