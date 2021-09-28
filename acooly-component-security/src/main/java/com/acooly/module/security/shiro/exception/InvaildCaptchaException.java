package com.acooly.module.security.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 验证码错误异常，扩展Shiro认证失败的异常
 *
 * @author zhangpu
 */
public class InvaildCaptchaException extends AuthenticationException {

    /**
     * UID
     */
    private static final long serialVersionUID = 2921483564393257577L;

    public InvaildCaptchaException() {
        super();
    }

    public InvaildCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvaildCaptchaException(String message) {
        super(message);
    }

    public InvaildCaptchaException(Throwable cause) {
        super(cause);
    }
}
