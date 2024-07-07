/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-14 16:11 创建
 */
package com.acooly.component.i18n;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;


/**
 * I18n国际化组件配置
 *
 * @author zhangpu
 */
@Data
@ConfigurationProperties(prefix = I18nProperties.PREFIX)
public class I18nProperties {
    public static final String PREFIX = "acooly.i18n";

    /**
     * 开关
     */
    private boolean enable = false;

    /**
     * 默认语言
     */
    private Locale defaultLocale = Locale.CHINA;

    /**
     * 修改语言的request参数名
     */
    private String changeLocaleParamName = "lang";
}
