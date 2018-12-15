/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-03-10 16:16 创建
 */
package com.acooly.core.test;

import com.acooly.core.common.boot.Apps;
import com.acooly.module.test.AppTestBase;

/**
 * @author qiubo@yiji.com
 */
public abstract class TestNoWebBase extends AppTestBase {
    protected static final String PROFILE = "sdev";

    static {
        Apps.setProfileIfNotExists(PROFILE);
    }
}
