CREATE TABLE `ac_tree_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `theme` varchar(45) DEFAULT NULL COMMENT '主题(默认为default，通过主题可在一个系统中隔离多套treenode)',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父类型ID',
  `path` varchar(128) DEFAULT NULL COMMENT '搜索路径',
  `sort_time` bigint(20) DEFAULT NULL COMMENT '排序值',
  `code` varchar(64) DEFAULT NULL COMMENT '类型编码',
  `name` varchar(32) NOT NULL COMMENT '类型名称',
  `sub_count` int(11) DEFAULT '0' COMMENT '子节点数量',
  `comments` varchar(128) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_AC_TREE_TYPE` (`theme`,`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='树形分类';
