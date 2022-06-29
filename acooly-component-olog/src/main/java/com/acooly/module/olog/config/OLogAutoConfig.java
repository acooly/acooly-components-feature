/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-14 17:04 创建
 */
package com.acooly.module.olog.config;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.acooly.module.olog.collector.intercept.OlogHandleInterceptor;
import com.acooly.module.olog.collector.intercept.ResponseBodyFilter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.List;

import static com.acooly.module.olog.config.OLogProperties.PREFIX;

/**
 * @author qiubo@yiji.com
 */
@Configuration
@EnableConfigurationProperties({OLogProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@Slf4j
public class OLogAutoConfig {
    @Autowired
    private OLogProperties oLogProperties;

    @Configuration
    @ComponentScan("com.acooly.module.olog.collector")
    public static class CollectorConfig implements WebMvcConfigurer {

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            InterceptorRegistration registration = registry.addInterceptor(ologHandleInterceptor());
            registration.addPathPatterns("/manage/**");
            registration.excludePathPatterns("**.js", "**.css");
        }

        @Bean
        public OlogHandleInterceptor ologHandleInterceptor() {
            return new OlogHandleInterceptor();
        }

        @Bean
        public ResponseBodyFilter responseBodyFilter() {
            return new ResponseBodyFilter();
        }

        @Bean
        public FilterRegistrationBean responseBodyFilterReg() {
            FilterRegistrationBean registration = new FilterRegistrationBean();
            registration.setFilter(responseBodyFilter());
            registration.addUrlPatterns(Lists.newArrayList("/manage/*").toArray(new String[0]));
            registration.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST));
            return registration;
        }
    }

    @Configuration
    @ConditionalOnProperty(value = "acooly.olog.storage.enable", matchIfMissing = true)
    @ComponentScan("com.acooly.module.olog.storage")
    public static class StorageConfig {
        @Bean
        public StandardDatabaseScriptIniter ologScriptIniter() {

            return new StandardDatabaseScriptIniter() {
                @Override
                public String getEvaluateTable() {
                    return "sys_olog";
                }

                @Override
                public String getComponentName() {
                    return "olog";
                }

                @Override
                public List<String> getInitSqlFile() {
                    return Lists.newArrayList("olog", "olog_urls");
                }
            };
        }

        @Configuration
        @ConditionalOnProperty(value = "dubbo.provider.enable")
        @ImportResource("classpath:/META-INF/spring/olog-storage-dubbo.xml")
        public static class StrageDubboConfig {

        }
    }
}
