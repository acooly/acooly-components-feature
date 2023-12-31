CREATE TABLE `sys_olog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `system` varchar(32) DEFAULT NULL COMMENT '系统',
  `module` varchar(256) DEFAULT NULL COMMENT '模块',
  `module_name` varchar(256) DEFAULT NULL COMMENT '模块名称',
  `action` varchar(32) DEFAULT NULL COMMENT '操作',
  `action_name` varchar(32) DEFAULT NULL COMMENT '操作名称',
  `execute_milliseconds` int(11) NOT NULL COMMENT '执行时间',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `operate_user` varchar(64) DEFAULT NULL COMMENT '操作员',
  `operate_user_id` bigint(20) DEFAULT NULL COMMENT '操作员ID',
  `request_parameters` varchar(512) DEFAULT NULL COMMENT '请求参数',
  `operate_result` int(11) NOT NULL DEFAULT '1' COMMENT '操作结果{1:成功,2:失败}',
  `operate_message` varchar(4096) DEFAULT NULL COMMENT '消息',
  `client_informations` varchar(256) DEFAULT NULL COMMENT '客户端信息',
  `descn` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_sys_olog_opt_time` (`operate_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COMMENT='操作日志';
