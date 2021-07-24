/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-24 21:59 创建
 */
package com.acooly.component.content.audit;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author zhangpu@acooly.cn
 */
@ConfigurationProperties(prefix = ContentAuditProperties.PREFIX)
@Data
@Slf4j
@Validated
public class ContentAuditProperties {
    public static final String PREFIX = "acooly.content.audit";

    private Boolean enable = true;

    private Boolean mock = false;

    private Aliyun aliyun = new Aliyun();

    @Data
    static class Aliyun {
        private String url;
        private String accessKey;
        private String secretKey;
        private String regionId;
    }

}
