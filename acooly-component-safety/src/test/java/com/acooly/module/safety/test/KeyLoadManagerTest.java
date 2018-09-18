/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-10-08 00:46 创建
 */
package com.acooly.module.safety.test;

import com.acooly.core.common.BootApp;
import com.acooly.core.common.boot.Apps;
import com.acooly.module.safety.key.KeyLoadManager;
import com.acooly.module.test.AppTestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangpu 2017-10-08 00:46
 */
@Slf4j
@SpringBootApplication
@BootApp(sysName = "saftyTest")
public class KeyLoadManagerTest extends AppTestBase {

    static {
        Apps.setProfileIfNotExists("sdev");
    }

    @Autowired
    KeyLoadManager keyStoreLoadManager;


    @Test
    public void testLoadKeyStore() {
        keyStoreLoadManager.load("123123123", "EmptyKeyStoreLoader");
    }


}
