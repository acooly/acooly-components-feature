CREATE TABLE `eav_entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `schema_id` bigint(20) NOT NULL COMMENT '方案id',
  `value` json NOT NULL COMMENT '内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `eav_schema` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL COMMENT '名称',
  `memo` varchar(128)  COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `eav_attribute` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `schema_id` bigint(20) NOT NULL COMMENT '方案id',
  `name` varchar(128) NOT NULL COMMENT '属性名称',
  `display_name` varchar(128) NOT NULL COMMENT '展示名称',
  `memo` varchar(128)  COMMENT '备注',
  `nullable` tinyint(1)  COMMENT '是否可以为空',
  `minimum` bigint(10)  COMMENT '最小值',
  `maximum` bigint(10)  COMMENT '最大值',
  `min_length` int(5)  COMMENT '最小长度',
  `max_length` int(5)  COMMENT '最大长度',
  `regex` varchar(30)  COMMENT '正则表达式',
  `enum_value` varchar(128)  COMMENT '枚举值',
  `attribute_type` varchar(20)  COMMENT '属性类型 {LONG:整形,DOUBLE:浮点数,ENUM:枚举,STRING:字符串,BOOLEAN:布尔,DATE:时间}',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `shemaId_name_idx` (`schema_id`,`name`),
   UNIQUE KEY `shemaId_displayName_idx` (`schema_id`,`display_name`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;