/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2019-11-03 12:13
 */
package com.acooly.module.treetype;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhangpu
 * @date 2019-11-03 12:13
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = TreeTypeProperties.PREFIX)
public class TreeTypeProperties {
    public static final String PREFIX = "acooly.tree-type";
}
