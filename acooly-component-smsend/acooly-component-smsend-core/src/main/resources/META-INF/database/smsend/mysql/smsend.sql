CREATE TABLE `sms_app` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `app_id` varchar(32) DEFAULT NULL COMMENT '应用ID',
   `app_name` varchar(32) DEFAULT NULL COMMENT '应用名称',
   `status` varchar(45) DEFAULT NULL COMMENT '{title:’状态’,alias:’simple’}',
   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
   `update_time` datetime DEFAULT NULL COMMENT '更新时间',
   `comments` varchar(127) DEFAULT NULL COMMENT '备注',
   PRIMARY KEY (`id`),
   UNIQUE KEY `UK_SMS_APP_ID` (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='短信发送应用';

CREATE TABLE `sms_send_log` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `batch_no` varchar(32) DEFAULT NULL COMMENT '批次号',
    `mobile_no` varchar(16) NOT NULL COMMENT '手机号码',
    `app_id` varchar(45) DEFAULT NULL,
    `send_type` varchar(16) NOT NULL COMMENT '{title:’发送类型’,type:’option’,options:{content:’内容发送’,template:’模板发送’}}',
    `content` varchar(255) DEFAULT NULL COMMENT '短信内容',
    `send_time` datetime DEFAULT NULL COMMENT '发送时间',
    `provider` varchar(16) DEFAULT NULL COMMENT '{title:’提供方’,type:’option’,options:{alien:’阿里云’}}',
    `request_id` varchar(64) DEFAULT NULL,
    `retry_count` int(11) DEFAULT NULL COMMENT '重试次数',
    `result_code` varchar(32) DEFAULT NULL COMMENT '消息编码',
    `result_message` varchar(127) DEFAULT NULL COMMENT '消息内容',
    `client_ip` varchar(16) DEFAULT NULL COMMENT '客户IP',
    `template_code` varchar(64) DEFAULT NULL COMMENT '模板编码',
    `template_provider` varchar(45) DEFAULT NULL COMMENT '渠道模板编码',
    `template_json_params` json DEFAULT NULL COMMENT '模板数据',
    `status` varchar(16) NOT NULL COMMENT '状态',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `comments` varchar(256) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='短信发送记录';


CREATE TABLE `sms_template` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `app_id` varchar(32) NOT NULL COMMENT '应用ID',
    `app_name` varchar(45) DEFAULT NULL COMMENT '应用名称',
    `template_code` varchar(32) NOT NULL COMMENT '模板编码',
    `template_name` varchar(45) NOT NULL COMMENT '模板名称',
    `template_content` varchar(127) DEFAULT NULL COMMENT '模板内容',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `comments` varchar(127) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`,`template_code`,`template_name`),
    UNIQUE KEY `UK_APP_TEMP_CODE` (`app_id`,`template_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='短信模板';

CREATE TABLE `sms_template_provider` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
     `template_code` varchar(32) NOT NULL COMMENT '模板编码',
     `provider` varchar(16) NOT NULL COMMENT '渠道',
     `provider_template_code` varchar(64) NOT NULL COMMENT '渠道模板编码',
     `create_time` datetime DEFAULT NULL COMMENT '创建时间',
     `update_time` datetime DEFAULT NULL COMMENT '更新时间',
     `comments` varchar(127) DEFAULT NULL COMMENT '备注',
     PRIMARY KEY (`id`),
     UNIQUE KEY `UK_SMS_TEMPALTE` (`template_code`,`provider`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='模板渠道';

CREATE TABLE `sms_black_list` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
      `mobile` varchar(20) NOT NULL,
      `status` varchar(255) NOT NULL COMMENT '状态{enable:正常,disable:作废}',
      `description` varchar(512) DEFAULT NULL,
      `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
      PRIMARY KEY (`id`),
      UNIQUE KEY `sms_black_list_mobile_idx` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='短信黑名单';
