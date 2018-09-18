CREATE TABLE `sys_app_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `config_name` varchar(255) NOT NULL DEFAULT '' COMMENT '配置项名称',
  `config_value` varchar(2048) NOT NULL COMMENT '配置值',
  `comments` varchar(255) NOT NULL COMMENT '配置描述',
  `local_cache_expire` int(11) DEFAULT '0' COMMENT '本地缓存过期时间(单位:ms)',
  `redis_cache_expire` int(11) DEFAULT '600000' COMMENT 'redis缓存过期时间(单位:ms)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_name_idx` (`config_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;