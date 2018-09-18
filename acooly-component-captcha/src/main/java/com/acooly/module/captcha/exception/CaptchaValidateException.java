package com.acooly.module.captcha.exception;

import com.acooly.core.common.exception.BusinessException;

/**
 * @author shuijing
 */
public class CaptchaValidateException extends BusinessException {

    /**
     * UID
     */
    private static final long serialVersionUID = -1986339830876922364L;

    private String resultCode;
    private String resultMessage;

    public CaptchaValidateException() {
        super();
    }

    public CaptchaValidateException(String resultCode, String resultMessage) {
        super();
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public CaptchaValidateException(String resultCode, String resultMessage, String message) {
        super(message);
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    @Override
    public String getMessage() {
        return (resultMessage != null ? resultMessage : "")
                + (super.getMessage() != null ? ":" + super.getMessage() : "");
    }
}
