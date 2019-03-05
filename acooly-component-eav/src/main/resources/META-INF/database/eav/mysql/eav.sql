CREATE TABLE `eav_scheme` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL COMMENT '方案名称',
  `title` varchar(128) NOT NULL DEFAULT '' COMMENT '方案标题',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `memo` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_EAV_SCHEME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='方案定义';

CREATE TABLE `eav_attribute` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `scheme_id` bigint(20) NOT NULL COMMENT '方案id',
  `scheme_name` varchar(128) DEFAULT NULL COMMENT '方案名称',
  `name` varchar(128) NOT NULL COMMENT '属性名称',
  `display_name` varchar(128) NOT NULL COMMENT '展示名称',
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
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `memo` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `shemaId_name_idx` (`scheme_id`,`name`),
  UNIQUE KEY `shemaId_displayName_idx` (`scheme_id`,`display_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `eav_option` (
  `id` bigint(20) NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='属性选项';

CREATE TABLE `eav_scheme_tag` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `schemeId` bigint(20) NOT NULL COMMENT '方案ID',
  `tag` varchar(64) NOT NULL COMMENT '标签',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='方案标签';
