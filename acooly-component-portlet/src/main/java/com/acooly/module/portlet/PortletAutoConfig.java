/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-14 17:04 创建
 */
package com.acooly.module.portlet;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.acooly.module.portlet.integration.FeedbackHandler;
import com.acooly.module.portlet.integration.PortalActionLogFilter;
import com.acooly.module.portlet.integration.impl.DefaultFeedbackHandler;
import com.acooly.module.portlet.service.ActionLogService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.EnumSet;
import java.util.List;

/**
 * @author qiubo@yiji.com
 */
@Configuration
@EnableConfigurationProperties({PortletProperties.class})
@ConditionalOnProperty(value = PortletProperties.PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.module.portlet")
public class PortletAutoConfig {

    @Autowired
    private PortletProperties portletProperties;

    /**
     * 数据库初始化
     *
     * @return
     */
    @Bean
    public StandardDatabaseScriptIniter PortletScriptIniter() {

        return new StandardDatabaseScriptIniter() {
            @Override
            public String getEvaluateTable() {
                return "portlet_feedback";
            }

            @Override
            public String getComponentName() {
                return "portlet";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("portlet", "portlet_urls");
            }
        };
    }

    @Bean
    @ConditionalOnProperty(
            value = PortletProperties.PREFIX + ".actionlog.filterEnable",
            matchIfMissing = true
    )
    public Filter portalActionLogFilter(ActionLogService actionLogService) throws Exception {
        PortalActionLogFilter portalActionLogFilter = new PortalActionLogFilter();
        portalActionLogFilter.setActionLogService(actionLogService);
        portalActionLogFilter.setSessionUserKey(portletProperties.getActionlog().getSessionUserKey());
        portalActionLogFilter.setFilterUrlPatterns(
                portletProperties.getActionlog().getFilterUrlPatterns());
        return portalActionLogFilter;
    }

    @Bean
    @ConditionalOnProperty(
            value = PortletProperties.PREFIX + ".actionlog.filterEnable",
            matchIfMissing = true
    )
    public FilterRegistrationBean portalActionLogFilterRegistrationBean(
            @Qualifier("portalActionLogFilter") Filter portalActionLogFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(portalActionLogFilter);
        registration.setOrder(Ordered.LOWEST_PRECEDENCE - 5);
        registration.addUrlPatterns(
                Lists.newArrayList("*.html", "*.jsp", "*.json").toArray(new String[0]));
        registration.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
        registration.setName("portalActionLogFilter");
        return registration;
    }

    /**
     * 注册默认的FeedbackHandler
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(FeedbackHandler.class)
    public FeedbackHandler feedbackHandler() {
        return new DefaultFeedbackHandler();
    }
}
