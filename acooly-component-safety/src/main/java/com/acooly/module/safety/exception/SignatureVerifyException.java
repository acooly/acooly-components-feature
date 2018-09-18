package com.acooly.module.safety.exception;

import com.acooly.core.common.exception.BusinessException;

/**
 * 签名/验签异常
 *
 * @author zhangpu
 * @date 2014年6月11日
 */
public class SignatureVerifyException extends BusinessException {


    public SignatureVerifyException() {
        super(SafetyResultCode.SIGN_VERIFY_ERROR);
    }

    public SignatureVerifyException(String detail) {
        super(SafetyResultCode.SIGN_VERIFY_ERROR, detail);
    }
}
