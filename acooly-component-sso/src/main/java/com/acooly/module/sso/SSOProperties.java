package com.acooly.module.sso;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author shuijing
 */
@Data
@ConfigurationProperties(SSOProperties.PREFIX)
public class SSOProperties {

    public static final String PREFIX = "acooly.sso";

    private boolean enable = true;

    /**
     * 主boss登录地址
     */
    @NotBlank
    private String ssoServerUrl;

    /**
     * 不需要拦截处理的静态资源(ant匹配规则 @link
     * https://github.com/spring-projects/spring-framework/blob/master/spring-core/src/test/java/org/springframework/util/AntPathMatcherTests.java)
     */
    private String ssoExcludeUrl;

    /**
     * 客户端资源权限缓存时间，在主boss验证资源用户的资源权限后缓存的时间,单位分钟,默认10分钟
     */
    private int AuthorizationCacheTime = 10;

    /**
     * 启用dubbo方式去主boss校验资源权限，默认false采用http请求主boss校验资源权限
     */
    private boolean enableDubboAuthz = false;

}
