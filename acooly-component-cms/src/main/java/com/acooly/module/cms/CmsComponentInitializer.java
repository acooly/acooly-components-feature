/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-02-18 00:37 创建
 */
package com.acooly.module.cms;

import com.acooly.core.common.boot.component.ComponentInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author acooly
 */
public class CmsComponentInitializer implements ComponentInitializer {

    private static final Logger logger = LoggerFactory.getLogger(CmsComponentInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        setPropertyIfMissing("acooly.ds.Checker.excludedColumnTables.cms", "cms_content_body");
        setPropertyIfMissing("acooly.security.xss.exclusions.cms[0]", "/manage/module/cms/**");
        setPropertyIfMissing("acooly.security.csrf.exclusions.cms", "/manage/module/cms/**");

        /** 升级cms，增加网页专用标题 */
        setPropertyIfMissing("acooly.ds.dbPatchs.cms_content[0].columnName", "web_title");
        setPropertyIfMissing("acooly.ds.dbPatchs.cms_content[0].patchSql", "ALTER TABLE `cms_content` ADD COLUMN `web_title` VARCHAR(128) NULL COMMENT '网页标题';");

        /**
         * 升级编码、app封面
         */
        setPropertyIfMissing("acooly.ds.dbPatchs.cms_content[1].columnName", "appcover");
        setPropertyIfMissing("acooly.ds.dbPatchs.cms_content[1].patchSql", "ALTER TABLE `cms_content` ADD COLUMN `appcover` VARCHAR(255) NULL COMMENT 'app封面';");

        setPropertyIfMissing("acooly.ds.dbPatchs.cms_code[0].columnName", "descn");
        setPropertyIfMissing("acooly.ds.dbPatchs.cms_code[0].patchSql", "ALTER TABLE `cms_code` ADD COLUMN `descn` VARCHAR(255) NULL COMMENT '编码描述';");

        setPropertyIfMissing("acooly.ds.dbPatchs.cms_code[1].columnName", "type_code");
        setPropertyIfMissing("acooly.ds.dbPatchs.cms_code[1].patchSql", "ALTER TABLE `cms_code` ADD COLUMN `type_code` VARCHAR(255) NULL COMMENT '类型编码';");
    }
}
