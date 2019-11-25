package com.acooly.module.security.shiro.filter;

import com.acooly.core.common.boot.EnvironmentHolder;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Encodes;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.security.JWTUtils;
import com.acooly.module.defence.password.PasswordStrength;
import com.acooly.module.security.captche.Captchas;
import com.acooly.module.security.config.FrameworkProperties;
import com.acooly.module.security.config.FrameworkPropertiesHolder;
import com.acooly.module.security.config.SecurityProperties;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.service.UserService;
import com.acooly.module.security.shiro.exception.InvaildCaptchaException;
import com.acooly.module.security.shiro.listener.ShireLoginLogoutSubject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 为Form-POST认证扩展Captcha
 *
 * @author zhangpu
 */
@Slf4j
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {

    public static final String SMS_VERIFY_CODE_KEY = "SMS_VERIFY_CODE_KEY";
    /**
     * 短信验证码发送开始时间
     */
    public static final String SMS_VERIFY_CODE_KEY_ST = "SMS_VERIFY_CODE_KEY_ST";

    public static final String CAPTCHA_FIRST_VERFIY = "CaptchaFirstVerfiy";
    private static final Logger logger =
            LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);
    /**
     * 界面请求的Input-form表单名称
     */
    public String captchaInputName = "captcha";

    @Autowired
    protected UserService userService;

    @Autowired
    protected FrameworkProperties frameworkProperties;
    /**
     * 登录失败Redirect URL
     */
    private String failureUrl = "/manage/onLoginFailure.html";
    /**
     * 监听处理
     */
    private ShireLoginLogoutSubject shireLoginLogoutSubject;

    public static boolean isLoginSmsEnable() {
        return EnvironmentHolder.get()
                .getProperty("acooly.security.enableSmsAuth", Boolean.class, SecurityProperties.DEFAULT_LOGIN_SMS);
    }

    /**
     * 扩展：在调用认证前，先验证验证码,同时，认证成功和失败都通过onLogin...方法直接Redirect到自定义的URL，进行后续日志，如日志拦截 ，實現与安全控件解耦
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response)
            throws Exception {

        setTargetUrlToSession();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        AuthenticationToken token = createToken(httpServletRequest, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken "
                    + "must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        }

        try {
            // 密码强度验证
            try {
                PasswordStrength passwordStrength = frameworkProperties.getPasswordStrength();
                passwordStrength.verify(new String((char[]) token.getCredentials()));
            } catch (BusinessException be) {
                throw new AuthenticationException(be.getMessage());
            }

            // 用户状态验证
            User user = checkUserStatus(token, httpServletRequest);
            //开启短信验证码
            if (isLoginSmsEnable()) {
                if (!checkSmsCaptcha()) {
                    throw new InvaildCaptchaException("验证码错误.");
                }
            } else {
                if (user.getLoginFailTimes() > 0) {
                    checkCaptcha(httpServletRequest);
                }
            }

            // Web安全-会话标识未更新问题（shiro）：让旧session失效，这一句代码一定要放在登录验证的最前面
            SecurityUtils.getSubject().logout();
            Subject subject = getSubject(httpServletRequest, response);
            subject.login(token);
            shireLoginLogoutSubject.afterLogin(token, null, httpServletRequest, (HttpServletResponse) response);
            return onLoginSuccess(token, subject, httpServletRequest, response);
        } catch (AuthenticationException e) {
            logger.debug("login failure. token:[" + token + "], exception:[" + e.getClass().getName() + "]");
            shireLoginLogoutSubject.afterLogin(token, e, httpServletRequest, (HttpServletResponse) response);
            return onLoginFailure(token, e, httpServletRequest, response);
        }
    }

    protected User checkUserStatus(AuthenticationToken token, ServletRequest request) {
        String username = (String) token.getPrincipal();
        User user = userService.findUserByUsername(username);
        if (user == null) {
            logger.debug("login checkUserStatus：用户不存在");
            throw new UnknownAccountException("用户名或密码错误");
        }
        Date now = new Date();
        if (user.getStatus() == User.STATUS_LOCK) {
            if (now.getTime() >= user.getUnlockTime().getTime()) {
                logger.debug("用户已到解锁时间 {}，登录时自动解锁定", Dates.format(user.getUnlockTime()));
                user.setStatus(User.STATUS_ENABLE);
                user.setLastModifyTime(now);
                userService.save(user);
            } else {
                logger.debug("login checkUserStatus：用户已锁定:{}", user.getStatus());
                throw new AuthenticationException(
                        "用户已锁定，解锁时间：" + Dates.format(user.getUnlockTime(), "yyyy-MM-dd HH:mm"));
            }
        }

        // 密码过期
        if (user.getStatus() == User.STATUS_EXPIRES
                || (FrameworkPropertiesHolder.get().isExpire()
                && user.getExpirationTime() != null
                && now.getTime() >= user.getExpirationTime().getTime())) {
            user.setStatus(User.STATUS_EXPIRES);
            userService.save(user);
            logger.debug("密码已经过期, expireTime:{}", Dates.format(user.getExpirationTime()));
            throw new AuthenticationException("密码已过期，请联系管理员修改密码");
        }

        if (user.getStatus() != User.STATUS_ENABLE) {
            logger.debug("login checkUserStatus：用户状态非法:{}", user.getStatus());
            throw new AuthenticationException(
                    "用户已" + FrameworkPropertiesHolder.get().getUserStatus().get(user.getStatus()));
        }
        return user;
    }

    /**
     * 登录认证,获取重定向地址,并存入 session
     *
     * @return
     */
    private String setTargetUrlToSession() {
        String targetUrl = Servlets.getRequestParameter(JWTUtils.KEY_TARGETURL);
        targetUrl = Encodes.urlDecode(targetUrl);
        if (StringUtils.isNotBlank(targetUrl)) {
            Servlets.setSessionAttribute(JWTUtils.KEY_TARGETURL, targetUrl);
        }
        return targetUrl;
    }

    /**
     * 验证图片验证码
     *
     * @param request
     */
    protected void checkCaptcha(HttpServletRequest request) {
        String requestCaptcha = request.getParameter(captchaInputName);
        //判断是否为第一次验证码检查
        Object firstVerfiy = SecurityUtils.getSubject().getSession().getAttribute(CAPTCHA_FIRST_VERFIY);
        if (firstVerfiy == null) {
            SecurityUtils.getSubject().getSession().setAttribute(CAPTCHA_FIRST_VERFIY, "");
            return;
        } else {
            if (!Captchas.verify(request, requestCaptcha)) {
                throw new InvaildCaptchaException("验证码错误.");
            }
        }
    }

    /**
     * 验证短信验证码
     */
    protected boolean checkSmsCaptcha() {
        String captchaInputName = Servlets.getRequestParameter(this.captchaInputName);
        String code = (String) Servlets.getSessionAttribute(SMS_VERIFY_CODE_KEY);
        if (StringUtils.isEmpty(code)) {
            return false;
        }
        if (code.equals(captchaInputName)) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onLoginSuccess(
            AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
            throws Exception {
        String username = (String) token.getPrincipal();
        userService.clearLoginFailureCount(username);
        SecurityUtils.getSubject().getSession().removeAttribute(CAPTCHA_FIRST_VERFIY);
        return super.onLoginSuccess(token, subject, request, response);
    }

    /**
     * 扩展登录失败回调
     *
     * <p>实现：失败后直接Redirect到指定的失败处理界面，如果Redirect失败，则使用框架的实现，直接返回请求界面直接处理。
     *
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {
        try {
            log.error("登录失败：{}", e.getMessage());
            String username = (String) token.getPrincipal();
            User user = null;
            if (!UnknownAccountException.class.isAssignableFrom(e.getClass())
                    && Strings.isNotBlank(username)) {
                user = userService.addLoginFailureCount(username);
            }
            Map<String, String> queryParams = Maps.newHashMap();
            queryParams.put(getFailureKeyAttribute(), e.getClass().getName());
            queryParams.put("message", e.getMessage());
            int lastTimes = FrameworkPropertiesHolder.get().getLoginLockErrorTimes() - user.getLoginFailTimes();
            if (lastTimes > 0) {
                queryParams.put("lastTimes", String.valueOf(lastTimes));
            }
            WebUtils.issueRedirect(request, response, getFailureUrl(), queryParams, true);
            return true;
        } catch (Exception e2) {
            return super.onLoginFailure(token, e, request, response);
        }
    }

    /**
     * 复写认证成功后，直接redirect到successUrl,不返回记录的上个请求地址。适应界面frame框架
     */
    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response)
            throws Exception {
        WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
    }

    public String getFailureUrl() {
        return failureUrl;
    }

    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

    public void setShireLoginLogoutSubject(ShireLoginLogoutSubject shireLoginLogoutSubject) {
        this.shireLoginLogoutSubject = shireLoginLogoutSubject;
    }
}
