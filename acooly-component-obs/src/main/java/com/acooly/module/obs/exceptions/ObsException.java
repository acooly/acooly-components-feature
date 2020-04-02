package com.acooly.module.obs.exceptions;

import com.acooly.core.common.exception.BusinessException;

/**
 * 当访问对象存储服务失败时抛出该异常类实例。
 */
public class ObsException extends BusinessException {
    public ObsException(String message, Throwable cause) {
        super(message, cause);
    }
}
