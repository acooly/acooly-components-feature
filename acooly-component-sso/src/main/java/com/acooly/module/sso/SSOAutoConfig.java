package com.acooly.module.sso;

import com.acooly.core.utils.Collections3;
import com.acooly.module.security.config.FrameworkProperties;
import com.acooly.module.sso.filter.AuthenticationFilter;
import com.acooly.module.sso.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import java.util.EnumSet;
import java.util.Set;

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
    @Autowired
    private FrameworkProperties frameworkProperties;

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
        // 确保认证在OrderedCharacterEncodingFilter后
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean autzRegistrationBean(AuthorizationFilter autzFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(autzFilter);
        registration.addUrlPatterns("/manage/*");
        registration.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST));
        registration.setName("autzFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 2);
        return registration;
    }

    @Bean
    public ServletContextInitializer ssoContextInitializer() {
        return (servletContext) -> {
            servletContext.setInitParameter("ssoEnable", "true");
            doExtendResources(servletContext);
        };
    }

    private void doExtendResources(ServletContext servletContext) {
        Set<String> scripts = frameworkProperties.mergeScripts();
        if (Collections3.isNotEmpty(scripts)) {
            StringBuilder sb = new StringBuilder();
            for (String script : scripts) {
                sb.append("<script type=\"text/javascript\" src=\"" + script + "\" charset=\"utf-8\"></script>\n");
            }
            servletContext.setAttribute("extendScripts", sb.toString());
        }
        Set<String> styles = frameworkProperties.mergeStyles();
        if (Collections3.isNotEmpty(styles)) {
            StringBuilder sb = new StringBuilder();
            for (String style : styles) {
                sb.append("<link rel=\"stylesheet\" href=\"" + style + "\">\n");
            }
            servletContext.setAttribute("extendStyles", sb.toString());
        }
    }
}
