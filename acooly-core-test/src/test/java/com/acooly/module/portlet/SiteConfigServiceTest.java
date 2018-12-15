/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-05-16 02:16 创建
 */
package com.acooly.module.portlet;

import com.acooly.core.test.TestBase;
import com.acooly.module.portlet.entity.SiteConfig;
import com.acooly.module.portlet.enums.SiteConfigKeyEnum;
import com.acooly.module.portlet.service.SiteConfigService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author acooly
 */
@Slf4j
public class SiteConfigServiceTest extends TestBase {

    @Autowired
    private SiteConfigService siteConfigService;

    @Test
    public void testFindUnique() {
        SiteConfig siteConfig = siteConfigService.findUnique(SiteConfigKeyEnum.serviceEmail.code());
        log.info("findUnique: {}", siteConfig);
    }


}
