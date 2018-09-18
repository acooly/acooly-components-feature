package com.acooly.module.sso.support;

import com.google.common.base.Splitter;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shuijing
 */
public class DefaultRequestMatcher implements RequestMatcher {

    private static final String DEFAULT_IGNORE_URI =
            "/security/config/index.html,/index.html,/manage/index.html,/manage/login.html,/manage/logout.html,/mgt/**,/**/*.js,/**/*.css,/**/*.ico,/**/*.jpg,/**/*.gif,/**/*.png,/**/*.map,/**/*.jsp,/**/*.woff2,/**/*.ttf,/**/*.swf";
    private Iterable<String> ignoreUrls;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public DefaultRequestMatcher(String ignoreUrls) {
        ignoreUrls += "," + DEFAULT_IGNORE_URI;
        this.ignoreUrls = Splitter.on(',').trimResults().omitEmptyStrings().split(ignoreUrls);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        //1. 验证是否为忽略url
        String url = request.getRequestURI();
        for (String ignoreUrl : ignoreUrls) {
            if (antPathMatcher.match(ignoreUrl, url)) {
                return false;
            }
        }
        return true;
    }
}
