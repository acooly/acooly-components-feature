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
    }
}
