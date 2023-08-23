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

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author qiubo
 * @author zhangpu
 */
// OK: 1.后台管理的资源管理功能待完善
// OK: 2.新老版本切换的cookies保存功能
// OK: 3.新版登录后的首个菜单自动展开;菜单的点击选中效果
// todo: 5.后台的notication功能（集成notice组件并扩展支持web后台）
// OK: 6.新版：左边菜单缩放后，界面resize自适应
// OK: 7.优化表单验证的错误提示样式，输入框应该有颜色提示。
// OK: 8.文件自动浏览脚本acooly.file.在打开图片时，如果图片太高，会操作窗口界面，要根据当前窗口大小进行有效计算。
// todo: 9.找回密码（主要考虑短信还是邮件文件）
// ok: 10.支持用户选择对角色
@ConfigurationProperties(SecurityProperties.PREFIX)
@Getter
@Setter
public class SecurityProperties {

    public static final String PREFIX = "acooly.security";
    public static final boolean DEFAULT_SHIRO_FILTER_ANON = false;
    public static final boolean DEFAULT_LOGIN_SMS = false;
    /**
     * 登录短信验证码重新发送时间间隔 s
     */
    public int smsSendInterval = 60;
    /**
     * 是否启用shiro
     */
    private boolean enable = true;
    private Shiro shiro = new Shiro();
    private Captcha captcha = new Captcha();
    /**
     * Shiro会话相关配置
     */
    private Session session = new Session();
    /**
     * 开启后shiro filter链都会设为不拦截，可在系统不需要任何授权、认证时开启
     */
    private boolean shiroFilterAnon = DEFAULT_SHIRO_FILTER_ANON;
    /**
     * 开启登录短信验证
     */
    private boolean enableSmsAuth = DEFAULT_LOGIN_SMS;
    /**
     * 开启单点登录权限校验dubbo服务(主boss才开启，为防止非主boss应用提供此服务，同一个zk环境中仅仅允许一个应用为服务提供者)
     */
    private boolean enableSsoAuth = false;

    /**
     * 是否开启druidStatView,默认开启。在部分安全级别要求较高的场景，可通过该参数关闭
     * 存在主机头攻击的风险，在不愿开启主机头攻击防御的情况下，可关闭。
     */
    private boolean druidStatViewEnable = true;


    public static enum SerializeType {
        Jdk,
        Kryo
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
         * <pre>
         * acooly.shiro.urls[0]./shiro/** = authc
         * acooly.shiro.urls[1]./** = anon
         * </pre>
         */
        private List<Map<String, String>> urls = Lists.newLinkedList();

        /**
         * 扩展配置的URLs
         * 格式：acooly.security.shiro.config-urls[0]=/manage/xxx/**,authc
         */
        private List<String> configUrls = Lists.newLinkedList();

        /**
         * 自定义Filter列表，对应shiro.ini中的[filters]标签，格式如：
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
            addUrlFilter("/manage/*.css", "anon");
            addUrlFilter("/manage/*.js", "anon");

            addUrlFilter("/manage/*.jsp", "user");
            addUrlFilter("/manage/layout/*", "user");
            addUrlFilter("/manage/system/*", "user");
            addUrlFilter("/manage/system/shiro/*", "user");
            addUrlFilter("/manage/druid/**", "user");

            addUrlFilter("/manage/**", "urlAuthr,kickout");
            addUrlFilter("/**", "anon");
        }

        public void addUrlFilter(String key, String value) {
            Map<String, String> url = Maps.newHashMap();
            url.put(key, value);
            urls.add(url);
        }


        public List<Map<String, String>> getMergeUrls() {
            for (String s : this.configUrls) {
                String[] sa = s.split(",");
                if (sa != null && sa.length == 2) {
                    Map<String, String> url = Maps.newHashMap();
                    url.put(sa[0], sa[1]);
                    ((LinkedList) urls).addFirst(url);
                }
            }
            return this.urls;
        }


    }

    @Getter
    @Setter
    public static class Captcha {
        private boolean enable = true;
        private int maxTry = 10;
        private String url = "/jcaptcha.jpg";
        private Kaptcha kaptcha = new Kaptcha();

        @Getter
        @Setter
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

    /**
     * ShiroSession配置
     */
    @Data
    public static class Session {

        /**
         * redis-session的值的序列化模式，默认JDK。
         */
        private SerializeType redisSerializeType = SerializeType.Jdk;

        /**
         * 会话存储到redis的过期时间（单位：分钟），默认8小时
         */
        private int redisTimeout = 8 * 60;

        /**
         * 会话超时时间（单位：秒），默认30分钟
         */
        private int timeout = 30 * 60;

        /**
         * 会话过期检查时间间隔（单位：秒），默认30分钟
         */
        private int checkInterval = 30 * 60;

        /**
         * 是否开启同用户登录踢人模式
         */
        private boolean enableKickOut = true;

        /**
         * 每个用户允许的同时登录个人，默认为1，表示只支持单用户登录
         */
        private int maxSessionPerUser = 1;
    }
}
