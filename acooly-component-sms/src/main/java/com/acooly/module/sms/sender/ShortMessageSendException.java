package com.acooly.module.sms.sender;

/**
 * 短信发送异常
 *
 * @author zhangpu
 */
public class ShortMessageSendException extends RuntimeException {

    /**
     * UID
     */
    private static final long serialVersionUID = -1986339830876922364L;

    private String resultCode;
    private String resultMessage;

    public ShortMessageSendException() {
        super();
    }

    public ShortMessageSendException(String resultCode, String resultMessage) {
        super();
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public ShortMessageSendException(String resultCode, String resultMessage, String message) {
        super(message, null);
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
        return (resultCode != null ? resultCode : "")
                + (super.getMessage() != null ? ":" + super.getMessage() : "");
    }
}
