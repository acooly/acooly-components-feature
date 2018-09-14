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
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认操作名称映射
 *
 * @author acooly
 */
@Component
public class DefaultActionNameMappingResolve implements NameMappingResolve {

    protected Map<String, String> methodNamesMapping = new HashMap<String, String>();

    @Override
    public String getMappingName(String key) {
        return methodNamesMapping.get(key);
    }

    public void setMethodNamesMapping(Map<String, String> methodNamesMapping) {
        this.methodNamesMapping = methodNamesMapping;
    }
}
