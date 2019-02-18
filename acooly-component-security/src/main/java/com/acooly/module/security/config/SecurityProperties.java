/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-10-27 23:31 创建
 */
package com.acooly.module.security.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiubo
 * @author zhangpu
 */
// OK: 1.后台管理的资源管理功能待完善
// OK: 2.新老版本切换的cookies保存功能
// OK: 3.新版登录后的首个菜单自动展开;菜单的点击选中效果
// todo: 4.开发新版的登录界面
// todo: 5.后台的notication功能（集成notice组件并扩展支持web后台）
// OK: 6.新版：左边菜单缩放后，界面resize自适应
@ConfigurationProperties(SecurityProperties.PREFIX)
@Data
public class SecurityProperties {

    public static final String PREFIX = "acooly.security";
    public static final boolean DEFAULT_SHIRO_FILTER_ANON = false;
    public static final boolean DEFAULT_LOGIN_SMS = false;

    /**
     * 是否启用shiro
     */
    private boolean enable = true;

    private Shiro shiro = new Shiro();

    private Captcha captcha = new Captcha();

    /**
     * 开启后shiro filter链都会设为不拦截，可在系统不需要任何授权、认证时开启
     */
    private boolean shiroFilterAnon = DEFAULT_SHIRO_FILTER_ANON;

    /**
     * 开启短信验证
     */
    private boolean loginSmsEnable = DEFAULT_LOGIN_SMS;

    /**
     * 开启单点登录权限校验dubbo服务(主boss才开启，为防止非主boss应用提供此服务，同一个zk环境中仅仅允许一个应用为服务提供者)
     */
    private boolean enableSSOAuthzService = false;

    @PostConstruct
    public void initXss() {
    }

    @Getter
    @Setter
    public static class Shiro {

        /**
         * 是否启用shiro
         */
        private boolean enable = true;
        /**
         * 当盐为空时，使用md5来校验(password存储的是密码的md5)
         */
        private boolean enableBlankSaltUseMd5Matcher = false;

        /**
         * 登录页面链接
         */
        private String loginUrl = "/manage/login.html";

        /**
         * 没有权限时跳转的链接
         */
        private String unauthorizedUrl = "/unauthorized.html";

        /**
         * 登录成功后的链接
         */
        private String successUrl = "/manage/onLoginSuccess.html";
        /**
         * 登录失败后的链接
         */
        private String failedUrl = "/manage/onLoginFailure.html";
        /**
         * 对应shiro.ini中的[urls]标签，注意顺序，格式如：
         *
         *
         *
         * <pre>
         * acooly.shiro.urls[0]./shiro/** = authc
         * acooly.shiro.urls[1]./** = anon
         * </pre>
         */
        private List<Map<String, String>> urls = Lists.newLinkedList();

        /**
         * 自定义Filter列表，对应shiro.ini中的[filters]标签，格式如：
         *
         *
         *
         * <pre>
         *  yiji.shiro.filters.authc=com.yiji.neverstopfront.web.shiro.CaptchaFormAuthenticationFilter
         *  yiji.shiro.filters.admin=com.yiji.neverstopfront.web.shiro.AdminAuthorizationFilter
         *  houseProperty=com.yiji.neverstopfront.web.shiro.ServiceTypeAuthorizationFilter
         *  houseProperty.serviceType=HOUSE_PROPERTY
         *  installment=com.yiji.neverstopfront.web.shiro.ServiceTypeAuthorizationFilter
         *  installment.serviceType=INSTALLMENT
         *  </pre>
         *
         *
         * </ul>
         */
        private LinkedHashMap<String, String> filters = Maps.newLinkedHashMap();

        public Shiro() {
            //添加默认url过滤器
            addUrlFilter("/manage/index.html", "authc");
            addUrlFilter("/manage/login.html", "authc");
            addUrlFilter("/manage/logout.html", "logout");
            addUrlFilter("/manage/error/**", "anon");
            addUrlFilter("/manage/assert/**", "anon");
            addUrlFilter("/manage/asset/**", "anon");

            addUrlFilter("/manage/*.html", "anon");
            addUrlFilter("/manage/*.jsp", "user");
            addUrlFilter("/manage/*.css", "anon");
            addUrlFilter("/manage/*.js", "anon");
            addUrlFilter("/manage/layout/*", "user");
            addUrlFilter("/manage/system/*", "user");
            addUrlFilter("/manage/druid/**", "user");
            addUrlFilter("/manage/**", "urlAuthr");
            addUrlFilter("/**", "anon");
        }

        public void addUrlFilter(String key, String value) {
            Map<String, String> url = Maps.newHashMap();
            url.put(key, value);
            urls.add(url);
        }
    }

    @Data
    public static class Captcha {
        private boolean enable = true;
        private String url = "/jcaptcha.jpg";
        private Kaptcha kaptcha = new Kaptcha();

        @Data
        public static class Kaptcha {
            /**
             * 图片高
             */
            private int height = 30;
            /**
             * 图片宽
             */
            private int width = 80;
            /**
             * 验证码文字颜色
             */
            private String fontColor = "34,114,200";
            /**
             * 验证码文字大小
             */
            private int fontSize = 24;
            /**
             * 验证码长度
             */
            private int charCount = 4;
        }
    }
}
