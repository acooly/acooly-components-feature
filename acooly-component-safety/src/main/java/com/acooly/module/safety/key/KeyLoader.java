/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-11-15 00:32 创建
 */
package com.acooly.module.safety.key;

/**
 * @author zhangpu 2017-11-15 00:32
 */
public interface KeyLoader<T> {

    /**
     * 加载秘钥信息
     * <p>
     * 外部集成系统加载实现，可以不考虑缓存。
     *
     * @param principal
     * @return
     */
    T load(String principal);

    /**
     * 加载器的提供方名称
     * <p>
     * 唯一标志一个加载器，解决：统一系统使用多个provider; 同意个provider使用多次的问题。
     *
     * @return
     */
    String getProvider();

}
