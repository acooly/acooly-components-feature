/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-11-15 13:21 创建
 */
package com.acooly.module.safety.key;

/**
 * 标记KeyStoreInfo的load 标记接口
 *
 * @author zhangpu 2017-11-15 13:21
 */
public interface KeyLoadManager {

    <T> T load(String principal, String provider);
}
