package com.acooly.module.sso.filter;

import com.acooly.module.sso.dic.AuthResult;
import com.acooly.module.sso.exceptions.SSOException;
import com.acooly.module.sso.support.*;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shuijing
 */
@Slf4j
public class AuthenticationFilter implements Filter {

    private static final LoginAuthProcessor defaultLoginAuthentication = new DefaultLoginAuth();

    private ApplicationContext applicationContext;

    private ServletContext servletContext;

    /**
     * 系统登录地址
     */
    @Setter
    private String loginUrl;
    /**
     * SSO不需要拦截的url
     */
    @Setter
    private String SSOExcludeUrl;

    private RequestMatcher requestMatcher;

    private Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        requestMatcher = new DefaultRequestMatcher(this.SSOExcludeUrl);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        /** 1、去掉排除的路径 2、判断是否登录 3、未登录则重定向到server登录 4、主boss认证成功后 重定向回业务系统，在cookie设置jwt并在url中带回jwt */
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestURL = httpServletRequest.getRequestURL().toString();
        String queryString = httpServletRequest.getQueryString();
        if (!requestMatcher.matches(httpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }
        String compactJws = AuthFilterUtil.getCompactJwt(httpServletRequest);

        AuthResult result =
                (AuthResult)
                        defaultLoginAuthentication.loginAuthentication(
                                compactJws, loginUrl, httpServletRequest, httpServletResponse);
        // 根据认证结果处理逻辑
        switch (result) {
            case LOGIN_URL_NULL:
                logger.error(AuthResult.LOGIN_URL_NULL.getDescription());
                throw new SSOException(AuthResult.LOGIN_URL_NULL.getDescription());
            case LOGIN_ERROR_DOMAIN:
                logger.error(AuthResult.LOGIN_ERROR_DOMAIN.getDescription());
                throw new SSOException(AuthResult.LOGIN_ERROR_DOMAIN.getDescription());
            case LOGIN_REDIRECT:
                AuthFilterUtil.doRedirectLogin(httpServletResponse, loginUrl, requestURL, queryString);
                break;
            case LOGIN_AUTHENTICATION_TIME_OUT:
                AuthFilterUtil.doRedirectLogin(httpServletResponse, loginUrl, requestURL, queryString);
                break;
            case AUTHENTICATION_TAMPER:
                logger.error(AuthResult.AUTHENTICATION_TAMPER.getDescription());
                throw new SSOException(AuthResult.AUTHENTICATION_TAMPER.getDescription());
            case AUTHENTICATION_ACCESS:
                chain.doFilter(request, response);
                break;
            default:
                chain.doFilter(request, response);
                break;
        }
    }

    @Override
    public void destroy() {
    }
}
