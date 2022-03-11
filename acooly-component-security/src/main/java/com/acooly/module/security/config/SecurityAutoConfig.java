/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-10-27 23:33 创建
 */
package com.acooly.module.security.config;

import com.acooly.core.common.boot.ApplicationContextHolder;
import com.acooly.core.common.boot.EnvironmentHolder;
import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.acooly.core.utils.security.JWTUtils;
import com.acooly.module.jpa.JPAAutoConfig;
import com.acooly.module.security.captche.CaptchaServlet;
import com.acooly.module.security.captche.SecurityCaptchaManager;
import com.acooly.module.security.health.HealthCheckServlet;
import com.acooly.module.security.shiro.cache.ShiroCacheManager;
import com.acooly.module.security.shiro.filter.CaptchaFormAuthenticationFilter;
import com.acooly.module.security.shiro.filter.KickoutSessionFilter;
import com.acooly.module.security.shiro.filter.NotifyLogoutFilter;
import com.acooly.module.security.shiro.filter.UrlResourceAuthorizationFilter;
import com.acooly.module.security.shiro.freemarker.ShiroFreemarkerTags;
import com.acooly.module.security.shiro.freemarker.tags.PrincipalTag;
import com.acooly.module.security.shiro.listener.ShireLoginLogoutSubject;
import com.acooly.module.security.shiro.realm.PathMatchPermissionResolver;
import com.acooly.module.security.shiro.realm.ShiroDbRealm;
import com.acooly.module.web.WebAutoConfig;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.config.ReflectionBuilder;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author qiubo
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties({SecurityProperties.class, FrameworkProperties.class})
@ConditionalOnProperty(value = SecurityProperties.PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = {"com.acooly.module.security"})
@AutoConfigureAfter(WebAutoConfig.class)
public class SecurityAutoConfig {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private freemarker.template.Configuration configuration;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;


    /**
     * 数据库脚本初始化
     *
     * @return
     */
    @Bean
    public StandardDatabaseScriptIniter securityScriptIniter() {
        return new SecurityDatabaseScriptIniter();
    }

    @Bean
    @ConditionalOnProperty(value = SecurityProperties.PREFIX + ".druid-stat-view-enable", matchIfMissing = true)
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean();

        StatViewServlet statViewServlet = new StatViewServlet();
        bean.setUrlMappings(Lists.newArrayList("/manage/druid/*"));
        bean.setServlet(statViewServlet);
        return bean;
    }

    @PostConstruct
    public void ssoContextInitializer() {
        // sms login auth
        if (securityProperties.isEnableSmsAuth()) {
            servletContext.setAttribute("enableSmsAuth", true);
        }

        // shiro freemarker tag
        configuration.setSharedVariable("shiroPrincipal", new PrincipalTag());
        configuration.setSharedVariable("shiro", new ShiroFreemarkerTags());
        LoggerFactory.getLogger("Main").info("shiro freemarker tag initialized.");

        //set redisTemplate
        JWTUtils.setRedisTemplate(ApplicationContextHolder.get().getBean("redisTemplate", RedisTemplate.class));
    }


    /**
     * Shiro配置
     * 1、采用Shiro-redis-session方案
     * 2、升级增加支持URL+FUNCTION权限，增强PATH匹配原则
     */
    @Configuration
    @ConditionalOnWebApplication
    @ConditionalOnProperty(value = SecurityProperties.PREFIX + ".shiro.enable", matchIfMissing = true)
    @AutoConfigureAfter({JPAAutoConfig.class, DataSourceTransactionManagerAutoConfiguration.class})
    public static class ShiroAutoConfigration {

        public static boolean isShiroFilterAnon() {
            return EnvironmentHolder.get().getProperty("acooly.security.shiroFilterAnon",
                    Boolean.class, SecurityProperties.DEFAULT_SHIRO_FILTER_ANON);
        }


        /**
         * Shiro专用的RedisTemplate，
         * 保持默认的序列化实现（JdkSerializationRedisSerializer），以支持Shiro的SimpleSession类可以正常序列化保持到redis
         * （注意：我们框架统一使用的是acooly-component-cache组件中的`RedisTemplate`配置，序列化采用：Kryo）
         *
         * @param factory
         * @return
         */
        @Bean
        public RedisTemplate<Object, Object> defaultRedisTemplate(RedisConnectionFactory factory) {
            RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
            template.setConnectionFactory(factory);
            return template;
        }


        /**
         * 基于Redis CacheManager
         * 包括RedisCache实现
         *
         * @param defaultRedisTemplate
         * @return
         */
        @Bean
        public CacheManager shiroCacheManager(RedisTemplate defaultRedisTemplate, SecurityProperties securityProperties) {
            ShiroCacheManager shiroCacheManager = new ShiroCacheManager();
            shiroCacheManager.setDefaultTimeout(securityProperties.getSession().getRedisTimeout());
            shiroCacheManager.setRedisTemplate(defaultRedisTemplate);
            return shiroCacheManager;
        }


        /**
         * 配置会话ID生成器
         *
         * @return
         */
        @Bean
        public SessionIdGenerator sessionIdGenerator() {
            return new JavaUuidSessionIdGenerator();
        }

        /**
         * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
         * MemorySessionDAO 直接在内存中进行会话维护
         * EnterpriseCacheSessionDAO  提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
         *
         * @return
         */
        @Bean
        public SessionDAO sessionDAO(CacheManager shiroCacheManager) {
            EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
            //使用ehCacheManager
            enterpriseCacheSessionDAO.setCacheManager(shiroCacheManager);
            //设置session缓存的名字 默认为 shiro-activeSessionCache
            enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
            //sessionId生成器
            enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator());
            return enterpriseCacheSessionDAO;
        }


        /**
         * 配置保存sessionId的cookie
         * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
         *
         * @return
         */
        @Bean("sessionIdCookie")
        public SimpleCookie sessionIdCookie() {
            //这个参数是cookie的名称
            SimpleCookie simpleCookie = new SimpleCookie("acooly");
            //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

            //setcookie()的第七个参数
            //设为true后，只能通过http访问，javascript无法访问
            //防止xss读取cookie
            simpleCookie.setHttpOnly(true);
            simpleCookie.setPath("/");
            //maxAge=-1表示浏览器关闭时失效此Cookie
            simpleCookie.setMaxAge(-1);
            return simpleCookie;
        }


        /**
         * 配置会话管理器，设定会话超时及保存
         *
         * @return
         */
        @Bean("sessionManager")
        public SessionManager sessionManager(SessionDAO sessionDAO, CacheManager shiroCacheManager, SecurityProperties securityProperties) {

            DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//            Collection<SessionListener> listeners = new ArrayList<SessionListener>();
            //配置监听
//            listeners.add(sessionListener());
//            sessionManager.setSessionListeners(listeners);
            sessionManager.setSessionIdCookie(sessionIdCookie());
            sessionManager.setSessionDAO(sessionDAO);
            sessionManager.setCacheManager(shiroCacheManager);

            //全局会话超时时间（单位毫秒），默认30分钟
            sessionManager.setGlobalSessionTimeout(TimeUnit.SECONDS.toMillis(securityProperties.getSession().getTimeout()));
            //是否开启删除无效的session对象  默认为true
            sessionManager.setDeleteInvalidSessions(true);
            //是否开启定时调度器进行检测过期session 默认为true
            sessionManager.setSessionValidationSchedulerEnabled(true);
            //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1分钟
            //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
            sessionManager.setSessionValidationInterval(TimeUnit.SECONDS.toMillis(securityProperties.getSession().getCheckInterval()));
            return sessionManager;
        }


        @Bean
        public WebSecurityManager shiroSecurityManager(
                CacheManager shiroCacheManager, Realm shiroRealm, SessionManager sessionManager) {

            DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
            securityManager.setCacheManager(shiroCacheManager);
            securityManager.setRealm(shiroRealm);
            securityManager.setSessionManager(sessionManager);
            return securityManager;
        }

        @Bean
        @ConditionalOnMissingBean
        public Realm shiroRealm(PathMatchPermissionResolver pathMatchPermissionResolver) {
            ShiroDbRealm shiroDbRealm = new ShiroDbRealm();
            shiroDbRealm.setPermissionResolver(pathMatchPermissionResolver);
            shiroDbRealm.setAuthenticationCachingEnabled(false);
            shiroDbRealm.setAuthorizationCachingEnabled(false);
            shiroDbRealm.setAuthorizationCacheName(ShiroCacheManager.KEY_AUTHZ);
            shiroDbRealm.setAuthenticationCacheName(ShiroCacheManager.KEY_AUTHC);
            return shiroDbRealm;
        }

        @Bean
        public PathMatchPermissionResolver pathMatchPermissionResolver() {
            return new PathMatchPermissionResolver();
        }

        @Bean
        @ConditionalOnProperty(
                value = SecurityProperties.PREFIX + ".shiro.auth.enable",
                matchIfMissing = true
        )
        public ShiroFilterFactoryBean shiroFilterFactoryBean(
                @Qualifier("shiroSecurityManager") WebSecurityManager shiroSecurityManager,
                SecurityProperties securityProperties) {
            ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
            shiroFilter.setSecurityManager(shiroSecurityManager);
            shiroFilter.setLoginUrl(securityProperties.getShiro().getLoginUrl());
            //		shiroFilter.setUnauthorizedUrl(shiroProperties.getUnauthorizedUrl());
            shiroFilter.setSuccessUrl(securityProperties.getShiro().getSuccessUrl());
            shiroFilter.setFilters(buildFiltersMap(securityProperties));
            shiroFilter.setFilterChainDefinitions(getFilterChainDefinitions(securityProperties));
            return shiroFilter;
        }

        @Bean
        @DependsOn({"logout", "urlAuthr", "authc", "kickout"})
        @ConditionalOnProperty(
                value = SecurityProperties.PREFIX + ".shiro.auth.enable",
                matchIfMissing = true
        )
        public Filter shiroFilter(ShiroFilterFactoryBean shiroFilterFactoryBean, Realm shiroRealm)
                throws Exception {
            ((DefaultWebSecurityManager) shiroFilterFactoryBean.getSecurityManager())
                    .setRealm(shiroRealm);
            return (Filter) shiroFilterFactoryBean.getObject();
        }

        @Bean
        @ConditionalOnProperty(
                value = SecurityProperties.PREFIX + ".shiro.auth.enable",
                matchIfMissing = true
        )
        public FilterRegistrationBean shiroFilterRegistrationBean(
                @Qualifier("shiroFilter") Filter shiroFilter, SecurityProperties securityProperties) {
            FilterRegistrationBean registration = new FilterRegistrationBean();
            registration.setFilter(shiroFilter);
            registration.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
            registration.addUrlPatterns(
                    Lists.newArrayList("/manage/*").toArray(new String[0]));
            registration.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
            registration.setName("shiroFilter");
            return registration;
        }

        @Bean
        @ConditionalOnMissingBean(LifecycleBeanPostProcessor.class)
        public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
            return new LifecycleBeanPostProcessor();
        }

        @Bean
        public AuthorizationAttributeSourceAdvisor shiroAuthorizationAttributeSourceAdvisor(
                WebSecurityManager shiroSecurityManager) {
            AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
            advisor.setSecurityManager(shiroSecurityManager);
            return advisor;
        }

        @Bean
        public AuthorizationAttributeSourceAdvisor shiroAuthorizationAttributeSourceAdvisor(
                WebSecurityManager shiroSecurityManager, Realm shiroRealm) {
            AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
            advisor.setSecurityManager(shiroSecurityManager);
            return advisor;
        }

        /**
         * 注册spring bean，为了shiro能够找到filter
         *
         * @param shireLoginLogoutSubject
         * @return
         */
        @Bean
        public NotifyLogoutFilter logout(
                ShireLoginLogoutSubject shireLoginLogoutSubject, SecurityProperties securityProperties) {
            NotifyLogoutFilter notifyLogoutFilter = new NotifyLogoutFilter();
            notifyLogoutFilter.setRedirectUrl(securityProperties.getShiro().getLoginUrl());
            notifyLogoutFilter.setShireLoginLogoutSubject(shireLoginLogoutSubject);
            return notifyLogoutFilter;
        }

        /**
         * 禁用logout filter，防止spring设置为web容器filter。因为shiroFilter会代理logout filter的执行。
         *
         * @param filter
         * @return
         */
        @Bean
        public FilterRegistrationBean disableLogoutForSpringMVC(NotifyLogoutFilter filter) {
            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
            filterRegistrationBean.setFilter(filter);
            filterRegistrationBean.setEnabled(false);
            filterRegistrationBean.setName("disableLogoutForSpringMVC");
            return filterRegistrationBean;
        }

        @Bean
        public UrlResourceAuthorizationFilter urlAuthr() {
            UrlResourceAuthorizationFilter filter = new UrlResourceAuthorizationFilter();
            return filter;
        }

        @Bean
        public FilterRegistrationBean disableUrlAuthrForSpringMVC(
                UrlResourceAuthorizationFilter filter) {
            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
            filterRegistrationBean.setFilter(filter);
            filterRegistrationBean.setEnabled(false);
            filterRegistrationBean.setName("disableUrlAuthrForSpringMVC");
            return filterRegistrationBean;
        }

        @Bean
        public CaptchaFormAuthenticationFilter authc(
                ShireLoginLogoutSubject shireLoginLogoutSubject, SecurityProperties securityProperties) {
            CaptchaFormAuthenticationFilter filter = new CaptchaFormAuthenticationFilter();
            filter.setShireLoginLogoutSubject(shireLoginLogoutSubject);
            filter.setFailureUrl(securityProperties.getShiro().getFailedUrl());
            filter.setSuccessUrl(securityProperties.getShiro().getSuccessUrl());
            return filter;
        }

        @Bean
        public KickoutSessionFilter kickout(SessionManager sessionManager,
                                            CacheManager shiroCacheManager, SecurityProperties securityProperties) {
            KickoutSessionFilter filter = new KickoutSessionFilter();
            filter.setSessionManager(sessionManager);
            filter.setKickoutUrl(securityProperties.getShiro().getFailedUrl());
            filter.setCacheManager(shiroCacheManager);
            return filter;
        }


        @Bean
        public FilterRegistrationBean disableAuthcForSpringMVC(CaptchaFormAuthenticationFilter filter) {
            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
            filterRegistrationBean.setFilter(filter);
            filterRegistrationBean.setEnabled(false);
            filterRegistrationBean.setName("disableAuthcForSpringMVC");
            return filterRegistrationBean;
        }

        @Bean
        public ShireLoginLogoutSubject shireLoginLogoutSubject() {
            ShireLoginLogoutSubject logoutSubject = new ShireLoginLogoutSubject();
            return logoutSubject;
        }

        private String getFilterChainDefinitions(SecurityProperties securityProperties) {
            List<Map<String, String>> urls = securityProperties.getShiro().getMergeUrls();
            if (CollectionUtils.isEmpty(urls)) {
                return "";
            }
            Boolean shiroFilterAnon = isShiroFilterAnon();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < urls.size(); i++) {
                Map<String, String> url = urls.get(i);
                if (MapUtils.isNotEmpty(url)) {
                    for (Map.Entry<String, String> entry : url.entrySet()) {
                        if (shiroFilterAnon) {
                            if (i == urls.size() - 1) {
                                sb.append("/**").append("=").append("anon").append("\n");
                            } else {
                                sb.append("/manage/**").append("=").append("anon").append("\n");
                            }
                        } else {
                            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
                        }
                    }
                }
            }
            return sb.toString();
        }

        private Map<String, Filter> buildFiltersMap(SecurityProperties securityProperties) {
            Map<String, String> filters = securityProperties.getShiro().getFilters();
            if (MapUtils.isEmpty(filters)) {
                return Maps.newLinkedHashMap();
            }

            ReflectionBuilder builder = new ReflectionBuilder();
            Map<String, ?> built = builder.buildObjects(filters);
            return extractFilters(built);
        }

        private Map<String, Filter> extractFilters(Map<String, ?> objects) {
            if (CollectionUtils.isEmpty(objects)) {
                return Maps.newLinkedHashMap();
            }
            Map<String, Filter> filterMap = Maps.newLinkedHashMap();
            for (Map.Entry<String, ?> entry : objects.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof Filter) {
                    filterMap.put(key, (Filter) value);
                }
            }
            return filterMap;
        }
    }

    @Configuration
    @ConditionalOnWebApplication
    @ConditionalOnProperty(
            value = SecurityProperties.PREFIX + ".captcha.enable",
            matchIfMissing = true
    )
    public static class CaptchaAutoConfigration {

        @Bean
        public ServletRegistrationBean mycaptcha(SecurityCaptchaManager securityCaptchaManager, SecurityProperties securityProperties) {
            ServletRegistrationBean bean = new ServletRegistrationBean();
            bean.setUrlMappings(Lists.newArrayList(securityProperties.getCaptcha().getUrl()));
            CaptchaServlet captchaServlet = new CaptchaServlet();
            captchaServlet.setSecurityCaptchaManager(securityCaptchaManager);
            bean.setServlet(captchaServlet);
            bean.setLoadOnStartup(1);
            return bean;
        }
    }

    @Configuration
    @ConditionalOnWebApplication
    public static class HealthCheckConfigration {
        @Bean
        public ServletRegistrationBean jcaptchaServlet() {
            ServletRegistrationBean bean = new ServletRegistrationBean();
            bean.setUrlMappings(Lists.newArrayList("/healthCheck"));
            HealthCheckServlet healthCheckServlet = new HealthCheckServlet();
            bean.setServlet(healthCheckServlet);
            return bean;
        }
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    private static class SecurityDatabaseScriptIniter extends StandardDatabaseScriptIniter {

        @Override
        public String getEvaluateTable() {
            return "sys_user";
        }

        @Override
        public String getComponentName() {
            return "security";
        }

        @Override
        public List<String> getInitSqlFile() {
            return Lists.newArrayList("security");
        }

    }

}
