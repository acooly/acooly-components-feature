/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-11-15 13:42 创建
 */
package com.acooly.module.safety.key.impl;

import com.acooly.module.safety.exception.SafetyException;
import com.acooly.module.safety.exception.SafetyResultCode;
import com.acooly.module.safety.key.AbstractKeyLoadManager;
import com.acooly.module.safety.key.KeyStoreLoader;
import com.acooly.module.safety.support.KeyStoreInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * KeyStoreLoader的空实现 （demo）
 *
 * @author zhangpu 2017-11-15 13:42
 */
@Slf4j
@Component
public class EmptyKeyStoreLoader extends AbstractKeyLoadManager<KeyStoreInfo> implements KeyStoreLoader {

    @Override
    public KeyStoreInfo doLoad(String principal) {
        log.warn("KeyStoreLoader的空实现，请在集成项目中实现：KeyStoreLoader接口并配置到spring容器中");
        throw new SafetyException(SafetyResultCode.NOT_EXSIST_KEYLOADER);
    }


    @Override
    public String getProvider() {
        return "EmptyKeyStoreLoader";
    }


}
