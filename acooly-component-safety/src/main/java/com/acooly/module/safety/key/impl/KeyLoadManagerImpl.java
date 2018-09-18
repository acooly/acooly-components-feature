/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-11-15 13:50 创建
 */
package com.acooly.module.safety.key.impl;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.utils.Strings;
import com.acooly.module.safety.exception.SafetyException;
import com.acooly.module.safety.exception.SafetyResultCode;
import com.acooly.module.safety.key.KeyLoadManager;
import com.acooly.module.safety.key.KeyLoader;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhangpu 2017-11-15 13:50
 */
@Slf4j
@Component
public class KeyLoadManagerImpl implements KeyLoadManager, InitializingBean {

    private static final String SYSTEM_INTENER_PROVIDER = "SYSTEM_INTENER_PROVIDER";
    private Map<String, KeyLoader> container = Maps.newHashMap();

    @Override
    public <T> T load(String principal, String provider) {
        KeyLoader keyLoader = container.get(provider);
        if (keyLoader == null) {
            throw new SafetyException(SafetyResultCode.NOT_EXSIST_KEYLOADMANAGER_PROVIDER_IMPL);
        }
        return (T) keyLoader.load(principal);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, KeyLoader> map = Apps.getApplicationContext().getBeansOfType(KeyLoader.class);
        for (Map.Entry<String, KeyLoader> entry : map.entrySet()) {
            if (Strings.equals(entry.getValue().getProvider(), SYSTEM_INTENER_PROVIDER)) {
                continue;
            }
            container.put(entry.getValue().getProvider(), entry.getValue());
            log.info("成功注册KeyStoreLoader的托管实现。provider:{}, bean:{}", entry.getValue().getProvider(), entry.getValue());
        }
    }
}
