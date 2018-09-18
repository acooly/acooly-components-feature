package com.acooly.module.safety.exception;

import com.acooly.core.common.exception.BusinessException;

/**
 * 签名/验签异常
 *
 * @author zhangpu
 * @date 2014年6月11日
 */
public class SignatureException extends BusinessException {

    public SignatureException() {
        super(SafetyResultCode.SIGN_CALC_ERROR);
    }

    public SignatureException(String detail) {
        super(SafetyResultCode.SIGN_CALC_ERROR, detail);
    }
}
