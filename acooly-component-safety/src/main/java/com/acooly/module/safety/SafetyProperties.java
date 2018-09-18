/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-10-14 17:04 创建
 */
package com.acooly.module.safety;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhangpu
 */
@ConfigurationProperties(SafetyProperties.PREFIX)
@Data
public class SafetyProperties {

    public static final String PREFIX = "acooly.safety";
    /**
     * 是否启用
     */
    private boolean enable = true;
}
