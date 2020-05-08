package com.acooly.module.smsend.exception;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.enums.Messageable;
import com.acooly.module.smsend.sender.dto.SmsResult;

/**
 * 短信发送异常
 *
 * @author zhangpu
 */
public class ShortMessageSendException extends BusinessException {

    public ShortMessageSendException() {
        super();
    }

    public ShortMessageSendException(Messageable messageable, String msg) {
        super(messageable, msg);
    }

    public ShortMessageSendException(String resultCode, String resultMessage) {
        super(resultMessage, resultCode);
    }

    public ShortMessageSendException(Messageable messageable) {
        super(messageable);
    }

    public ShortMessageSendException(String resultCode, String resultMessage, String detail) {
        super(resultMessage, resultCode);
    }

}
