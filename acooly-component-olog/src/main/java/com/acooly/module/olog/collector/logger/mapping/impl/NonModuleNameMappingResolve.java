/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2016-11-01 02:52 创建
 */
package com.acooly.module.olog.collector.logger.mapping.impl;


import com.acooly.module.olog.collector.logger.mapping.NameMappingResolve;

/**
 * 默认模块名称映射
 *
 * @author acooly
 */
public class NonModuleNameMappingResolve implements NameMappingResolve {
    @Override
    public String getMappingName(String key) {
        return key;
    }
}
