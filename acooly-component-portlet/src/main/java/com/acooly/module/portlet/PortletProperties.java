/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-10-27 23:31 创建
 */
package com.acooly.module.portlet;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author qiubo
 */
@ConfigurationProperties(PortletProperties.PREFIX)
@Data
public class PortletProperties {

    public static final String PREFIX = "acooly.portlet";
    /**
     * 是否启用
     */
    private boolean enable = true;
    /**
     * 访问日志
     */
    private ActionLog actionlog = new ActionLog();

    /**
     * 访问日志
     */
    @Getter
    @Setter
    public static class ActionLog {

        /**
         * 是否开启filter自动记录日志
         */
        private boolean filterEnable = false;
        /**
         * 需要拦截和记录的Url配置,支持AntPath,多个使用逗号分隔
         */
        private String filterUrlPatterns = "/portal/**";

        /**
         * 是否批量插入
         */
        private boolean cacheable = true;
        /**
         * 批量大小
         */
        private int cacheSize = 1000;

        /**
         * 用户会话key,多个使用逗号分隔
         */
        private String sessionUserKey = "sessionCustomer";
    }
}
