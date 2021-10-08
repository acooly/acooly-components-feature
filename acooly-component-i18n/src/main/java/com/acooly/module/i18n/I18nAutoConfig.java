/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-14 17:04 创建
 */
package com.acooly.module.i18n;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.List;
import java.util.Locale;

import static com.acooly.module.i18n.I18nProperties.PREFIX;


/**
 * @author zhangpu@acooly.cn
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({I18nProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
public class I18nAutoConfig {
    @Autowired
    private I18nProperties oFileProperties;

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
