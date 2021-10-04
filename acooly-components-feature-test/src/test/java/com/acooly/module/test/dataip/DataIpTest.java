/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-10-04 21:22
 */
package com.acooly.module.test.dataip;

import com.acooly.component.data.ip.ipblock.IpBlockLoader;
import com.acooly.component.data.ip.service.IpSearchService;
import com.acooly.module.test.NoWebTestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;

/**
 * @author zhangpu
 * @date 2021-10-04 21:22
 */
@Slf4j
public class DataIpTest extends NoWebTestBase {

    @Autowired
    private IpBlockLoader ipBlockLoader;
    @Autowired
    private IpSearchService ipSearchService;

    @Test
    public void testIpBlockLoader() {
        List<String> ipblocks = ipBlockLoader.load(Locale.CHINA);
        log.info("ipblock load 1 count: {}", ipblocks.size());
        ipblocks = ipBlockLoader.load(Locale.CHINA);
        log.info("ipblock load 2 count: {}", ipblocks.size());
        ipblocks = ipBlockLoader.load(Locale.CHINA);
        log.info("ipblock load 3 count: {}", ipblocks.size());
    }

    @Test
    public void testIpSearch() {
        // 中国大陆IP
        String publicIp = "125.85.16.220";
        log.info("Test {} is China ip : {}", publicIp, ipSearchService.isChinaIp(publicIp));
        // 中国香港IP
        publicIp = "47.57.232.42";
        log.info("Test {} is China ip : {}", publicIp, ipSearchService.isChinaIp(publicIp));
    }

}
