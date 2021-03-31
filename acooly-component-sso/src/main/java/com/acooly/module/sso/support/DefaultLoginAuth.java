package com.acooly.module.sso.support;

import com.acooly.core.utils.security.JWTUtils;
import com.acooly.module.sso.dic.AuthResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shuijing
 */
@Slf4j
public class DefaultLoginAuth extends AbstractLoginJwtAuthProcessor<AuthResult> {

    @Override
    public AuthResult loginAuthentication(
            String authentication,
            String loginUrl,
            HttpServletRequest request,
            HttpServletResponse response) {
        if (!isAuthenticationExist(authentication)) {
            if (!isLoginUrlExist(loginUrl)) {
                return AuthResult.LOGIN_URL_NULL;
            }
            if (!isDomainMatch(request.getRequestURL().toString(), loginUrl)) {
                return AuthResult.LOGIN_ERROR_DOMAIN;
            }
            return AuthResult.LOGIN_REDIRECT;
        }
        return validateAuthentication(request, authentication);
    }

    /**
     * jwt 验证篡改与过期
     */
    private AuthResult validateAuthentication(HttpServletRequest request, String authentication) {
        try {
            // 验证jwt是否被篡改
            Jwt<Header, Claims> jwt = JWTUtils.parseAuthentication(authentication);
            // 验证 jwt 是否过期
            if (!JWTUtils.validateTimeout(jwt)) {

                //判断jwt是否在黑名单中
                if(JWTUtils.isBlackToken(jwt)){
                    return AuthResult.LOGIN_AUTHENTICATION_TIME_OUT;
                }
                // 将解析后的信息存入 request 属性中
                setRequestAttributes(request, jwt);
                //将subject绑定到线程
                bindSubjectToThread(jwt, request);

                return AuthResult.AUTHENTICATION_ACCESS;
            }
            return AuthResult.LOGIN_AUTHENTICATION_TIME_OUT;
        } catch (Exception e) {
            return AuthResult.AUTHENTICATION_TAMPER;
        }
    }
}
