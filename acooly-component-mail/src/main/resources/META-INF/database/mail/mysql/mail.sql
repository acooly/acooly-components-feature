DROP TABLE IF EXISTS email_template;

CREATE TABLE `email_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '模板id',
  `name` varchar(45) DEFAULT NULL COMMENT '模版名称',
  `subject` varchar(1024) NOT NULL COMMENT '模板邮件主题',
  `content` mediumtext NOT NULL COMMENT '模板邮件内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='邮件模板表';

COMMIT;
