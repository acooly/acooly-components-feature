/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-10-04 22:12
 */
package com.acooly.component.data.ip.service;

import com.acooly.component.data.ip.ipblock.IpBlockLoader;
import com.acooly.core.utils.system.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

/**
 * IP 查询相关服务
 *
 * @author zhangpu
 * @date 2021-10-04 22:12
 */
@Slf4j
@Component
public class IpSearchServiceImpl implements IpSearchService {

    @Autowired
    private IpBlockLoader ipBlockLoader;

    @Override
    public Boolean isInRange(Locale locale, String publicIp) {
        List<String> ipBlocks = ipBlockLoader.load(locale);
        for (String ipBlock : ipBlocks) {
            if (IPUtil.isInRange(publicIp, ipBlock)) {
                return true;
            }
        }
        return false;
    }
}
