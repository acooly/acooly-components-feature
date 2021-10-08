/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-14 16:11 创建
 */
package com.acooly.module.i18n;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.exception.AppConfigException;
import com.acooly.core.utils.Strings;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.io.File;
import java.util.Locale;


/**
 *
 */
@ConfigurationProperties(prefix = I18nProperties.PREFIX)
@Data
public class I18nProperties {
    public static final String PREFIX = "acooly.i18n";

    /**
     * 配置国际化语言
     */
    @Configuration
    public static class LocaleConfig {

        /**
         * 默认解析器 其中locale表示默认语言
         */
        @Bean
        public LocaleResolver localeResolver() {
            SessionLocaleResolver localeResolver = new SessionLocaleResolver();
            localeResolver.setDefaultLocale(Locale.CHINA);
            return localeResolver;
        }

        /**
         * 默认拦截器 其中lang表示切换语言的参数名
         */
        @Bean
        public WebMvcConfigurer localeInterceptor() {
            return new WebMvcConfigurer() {
                @Override
                public void addInterceptors(InterceptorRegistry registry) {
                    LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
                    localeInterceptor.setParamName("lang");
                    registry.addInterceptor(localeInterceptor);
                }
            };
        }
    }
}
