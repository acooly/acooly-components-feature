package com.acooly.module.safety.exception;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.enums.Messageable;

/**
 * 签名/验签异常
 *
 * @author zhangpu
 * @date 2014年6月11日
 */
public class SafetyException extends BusinessException {

    public SafetyException(Messageable messageable) {
        super(messageable);
    }

    public SafetyException(Messageable messageable, String detail) {
        super(messageable, detail);
    }
}
