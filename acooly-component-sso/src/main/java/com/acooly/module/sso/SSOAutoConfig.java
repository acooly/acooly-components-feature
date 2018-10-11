package com.acooly.module.sso;

import com.acooly.module.sso.filter.AuthenticationFilter;
import com.acooly.module.sso.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

import static com.acooly.module.sso.SSOProperties.PREFIX;

/**
 * @author shuijing
 */
@Configuration
@EnableConfigurationProperties({SSOProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
public class SSOAutoConfig {

    @Autowired
    private SSOProperties ssoProperties;

    @Bean
    public AuthenticationFilter ssoFilter() {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        //修复上传组件flash无法加载
        authenticationFilter.setSSOExcludeUrl(
                ssoProperties.getSsoExcludeUrl() + ",/manage/assert/plugin/**");
        authenticationFilter.setLoginUrl(ssoProperties.getSsoServerUrl());
        return authenticationFilter;
    }

    @Bean
    public AuthorizationFilter autzFilter() {
        AuthorizationFilter autzFilter = new AuthorizationFilter(ssoProperties);
        return autzFilter;
    }

    @Bean
    public FilterRegistrationBean ssoRegistrationBean(AuthenticationFilter ssoFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(ssoFilter);
        registration.addUrlPatterns("/manage/*");
        registration.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST));
        registration.setName("ssoFilter");
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }

    @Bean
    public FilterRegistrationBean autzRegistrationBean(AuthorizationFilter autzFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(autzFilter);
        registration.addUrlPatterns("/manage/*");
        registration.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST));
        registration.setName("autzFilter");
        registration.setOrder(Integer.MIN_VALUE + 1);
        return registration;
    }

    @Bean
    public ServletContextInitializer ssoContextInitializer() {
        return (servletContext) -> {
            servletContext.setInitParameter("ssoEnable", "true");
        };
    }
}