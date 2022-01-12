/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-16 17:04 创建
 */
package com.acooly.module.mail;

import com.acooly.core.common.boot.component.ComponentInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author qiubo@yiji.com
 */
@Slf4j
public class MailComponentInitializer implements ComponentInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        setPropertyIfMissing("acooly.security.xss.exclusions.mail", "/manage/module/mail/emailTemplate/*");

        /** 升级邮件模板表，增加模板title字段 */
        setPropertyIfMissing("acooly.ds.dbPatchs.email_template[0].columnName", "title");
        setPropertyIfMissing("acooly.ds.dbPatchs.email_template[0].patchSql", "ALTER TABLE `email_template` ADD COLUMN `title` VARCHAR(45) NULL COMMENT '模版标题';");
    }
}
