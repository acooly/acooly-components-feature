package com.acooly.module.security.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 扩展：验证签名登录异常
 *
 * @author zhangpu
 */
public class DigestSignatureException extends AuthenticationException {


    public DigestSignatureException() {
        super();
    }

    public DigestSignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public DigestSignatureException(String message) {
        super(message);
    }

    public DigestSignatureException(Throwable cause) {
        super(cause);
    }
}
