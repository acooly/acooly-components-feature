CREATE TABLE `cert_certification_record` (
  `id`          BIGINT(20)  NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  `real_name`   VARCHAR(32) NOT NULL
  COMMENT '真实姓名',
  `id_car_no`   VARCHAR(18) NOT NULL
  COMMENT '身份证号',
  `sex`         VARCHAR(18)          DEFAULT NULL
  COMMENT '性别',
  `address`     VARCHAR(512)         DEFAULT NULL
  COMMENT '所在地址',
  `birthday`    VARCHAR(32)          DEFAULT NULL
  COMMENT '出生日期',
  `status`      INT(2)               DEFAULT 0
  COMMENT '状态 {1:验证通过,0:未通过}',
  `create_time` TIMESTAMP            DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time` TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '修改时间',
  PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COMMENT = '实名认证记录表';


CREATE TABLE `bank_certification_record` (
  `id`          BIGINT(20)  NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  `real_name`   VARCHAR(20) NOT NULL
  COMMENT '持卡人姓名',
  `card_no`     VARCHAR(32) NOT NULL
  COMMENT '银行卡帐号',
  `cert_id`     VARCHAR(18)          DEFAULT NULL
  COMMENT '开卡使用的证件号码',
  `phone_num`   VARCHAR(32)          DEFAULT NULL
  COMMENT '绑定手机号',
  `cert_type`   VARCHAR(10)          DEFAULT NULL
  COMMENT '开卡使用的证件类型{cert_no:身份证,passport_no:护照} ',
  `belong_area` VARCHAR(32)          DEFAULT NULL
  COMMENT '银行卡归属地',
  `bank_tel`    VARCHAR(32)          DEFAULT NULL
  COMMENT '银行客服',
  `brand`       VARCHAR(32)          DEFAULT NULL
  COMMENT '银行卡产品名称',
  `bank_name`   VARCHAR(64)          DEFAULT NULL
  COMMENT '银行名称',
  `card_type`   VARCHAR(32)          DEFAULT NULL
  COMMENT '银行卡种',
  `bank_url`    VARCHAR(32)          DEFAULT NULL
  COMMENT '银行官网',
  `fail_reason`    VARCHAR(100)      DEFAULT NULL
  COMMENT '验证失败原因',
  `status`      INT(2)               DEFAULT 0
  COMMENT '状态 {1:验证通过,0:未通过}',
  `create_time` TIMESTAMP            DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time` TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UN_BANK_CERT_RECORD_CARD_NO` (`card_no`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COMMENT = '银行卡四要素记录表';



