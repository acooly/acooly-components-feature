/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-14 17:04 创建
 */
package com.acooly.module.ofile;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.acooly.module.ofile.auth.OFileAccessAuthFilter;
import com.acooly.module.ofile.auth.OFileUploadAuthenticate;
import com.acooly.module.ofile.support.DefaultOfileSupportService;
import com.acooly.module.ofile.support.OfileSupportService;
import com.acooly.module.security.config.SecurityAutoConfig;
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
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

import static com.acooly.module.ofile.OFileProperties.PREFIX;

/**
 * @author qiubo@yiji.com
 */
@Configuration
@EnableConfigurationProperties({OFileProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.module.ofile")
@AutoConfigureAfter(SecurityAutoConfig.class)
@Slf4j
public class OFileAutoConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private OFileProperties oFileProperties;

    @Bean
    public StandardDatabaseScriptIniter ofileScriptIniter() {

        return new StandardDatabaseScriptIniter() {
            @Override
            public String getEvaluateTable() {
                return "ofile";
            }

            @Override
            public String getComponentName() {
                return "ofile";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("ofile", "ofile_urls");
            }
        };
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (oFileProperties.isEnableLocalMapping()) {
            boolean useResourceCache = !Apps.isDevMode();
            String pathPatterns = oFileProperties.getServerRootMappingPath() + "/**";
            String resourceLocations = "file:" + oFileProperties.getStorageRoot();
            log.info("ofile pathPatterns={},resourceLocations={}", pathPatterns, resourceLocations);
            registry
                    .addResourceHandler(pathPatterns)
                    .addResourceLocations(resourceLocations)
                    .resourceChain(useResourceCache);
        }
    }

    @Configuration
    @ConditionalOnProperty(value = "acooly.obs.enable", matchIfMissing = true)
    public static class ApiLoginConfiguration {
        @ConditionalOnMissingBean(OfileSupportService.class)
        @Bean
        public OfileSupportService defaultObsSupportService() {
            return new DefaultOfileSupportService();
        }
    }

    @Configuration
    @ConditionalOnProperty(value = "acooly.ofile.accessAuthEnable", matchIfMissing = false)
    public static class OFileAccessAuthConfiguration {

        @Autowired
        private OFileProperties oFileProperties;

        @Bean
        public OFileAccessAuthFilter oFileAccessAuthFilter(OFileUploadAuthenticate oFileUploadAuthenticate) {
            OFileAccessAuthFilter oFileAccessAuthFilter = new OFileAccessAuthFilter();
            oFileAccessAuthFilter.setAuthIncludes(oFileProperties.getServerRoot() + "/**/*");
            return oFileAccessAuthFilter;
        }

        @Bean
        public FilterRegistrationBean ofileAccessAuthFilterRegistrationBean(OFileAccessAuthFilter oFileAccessAuthFilter) {
            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
            filterRegistrationBean.setFilter(oFileAccessAuthFilter);
            return filterRegistrationBean;
        }
    }


}
