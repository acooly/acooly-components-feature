package com.acooly.module.certification.cert;

import com.acooly.core.common.exception.BusinessException;

/**
 * @author shuijing
 */
public class CertficationException extends BusinessException {

    /**
     * UID
     */
    private static final long serialVersionUID = -1986339230876922364L;

    private String resultCode;
    private String resultMessage;
    private String detail;

    public CertficationException() {
        super();
    }

    @Deprecated
    public CertficationException(String resultCode, String resultMessage) {
        super();
        this.resultCode = resultCode;
        this.setCode(resultCode);
        this.resultMessage = resultMessage;
    }

    public CertficationException(String resultCode, String resultMessage, String detail) {
        super(detail);
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.detail = detail;
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
