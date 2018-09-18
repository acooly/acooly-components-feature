package com.acooly.module.sso.exceptions;

import com.acooly.core.common.exception.BusinessException;

/**
 * @author shuijing
 */
public class SSOException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public SSOException() {
        super();
    }

    public SSOException(String message) {
        super(message);
    }

    public SSOException(String message, Throwable cause) {
        super(message, cause);
    }

    public SSOException(Throwable cause) {
        super(cause);
    }
}
