package com.acooly.module.captcha.exception;

import com.acooly.core.common.exception.BusinessException;

/**
 * @author shuijing
 */
public class CaptchaGenerateException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public CaptchaGenerateException() {
        super();
    }

    public CaptchaGenerateException(String message) {
        super(message);
    }

    public CaptchaGenerateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaGenerateException(Throwable cause) {
        super(cause);
    }
}
