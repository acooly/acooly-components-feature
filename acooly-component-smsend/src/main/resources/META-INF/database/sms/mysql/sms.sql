CREATE TABLE `sms_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `BATCH_NO` varchar(2048) DEFAULT NULL,
  `MOBILE_NO` varchar(2048) NOT NULL COMMENT '手机号码',
  `CONTENT` varchar(512) NOT NULL COMMENT '短信内容',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `SEND_TIME` datetime DEFAULT NULL COMMENT '发送时间',
  `TIMER_TIME` datetime DEFAULT NULL COMMENT '定时发送时间',
  `PROVIDER` varchar(2048) DEFAULT NULL,
  `PROVIDER_STATUS` varchar(2048) DEFAULT NULL COMMENT '提供商状态',
  `PROVIDER_MEMO` varchar(2048) DEFAULT NULL,
  `STATUS` int(11) NOT NULL COMMENT '状态 {1:未发送,2:发送失败,3:发送成功}',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='短信发送记录';

ALTER TABLE `sms_log`
ADD COLUMN `CLIENT_IP`  varchar(16) NULL COMMENT '客户IP' AFTER `STATUS`,
ADD COLUMN `COMMENTS`  varchar(256) NULL COMMENT '备注' AFTER `CLIENT_IP`;

CREATE TABLE `sms_black_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `mobile` varchar(20) NOT NULL,
  `status` varchar(255) NOT NULL COMMENT '状态{enable:正常,disable:作废}',
  `description` varchar(512) DEFAULT NULL,
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sms_black_list_mobile_idx` (`mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信黑名单';