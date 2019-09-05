/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-14 16:11 创建
 */
package com.acooly.module.cms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.acooly.module.cms.CmsProperties.PREFIX;

/**
 * @author zhangpu@acooly.cn
 */
@ConfigurationProperties(prefix = PREFIX)
@Data
public class CmsProperties {
    public static final String PREFIX = "acooly.cms";
    private Boolean enable = true;

    /**
     * 查询内容缓存时长（秒s）,默认1小时表示不缓存，配置为：0表示无缓存
     */
    private long cacheTimeout = 60 * 60;

}
