CREATE TABLE `sms_send_day` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `app_id` varchar(45) DEFAULT NULL COMMENT '应用ID',
  `provider` varchar(16) DEFAULT NULL COMMENT '{title:’提供方’,type:’option’,options:{alien:’阿里云’}}',
  `period` date DEFAULT NULL COMMENT '日期',
  `count` int(11) DEFAULT NULL COMMENT '发送数',
  `amount` bigint(20) DEFAULT NULL COMMENT '费用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `comments` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `IDX_SMS_DAY_DATE` (`period`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='发送统计';

