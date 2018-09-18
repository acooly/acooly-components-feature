package com.acooly.module.certification.cert;

import com.acooly.core.common.exception.BusinessException;

/**
 * 短信发送异常
 *
 * @author zhangpu
 */
public class RealNameAuthenticationException extends BusinessException {

    /**
     * UID
     */
    private static final long serialVersionUID = -1986339830876922364L;

    private String resultCode;
    private String resultMessage;

    public RealNameAuthenticationException() {
        super();
    }

    public RealNameAuthenticationException(String resultCode, String resultMessage) {
        super();
        this.resultCode = resultCode;
        this.setCode(resultCode);
        this.resultMessage = resultMessage;
    }

    public RealNameAuthenticationException(String resultCode, String resultMessage, String message) {
        super(message);
        this.resultCode = resultCode;
        this.setCode(resultCode);
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
