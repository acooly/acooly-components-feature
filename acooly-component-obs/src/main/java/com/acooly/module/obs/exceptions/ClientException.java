package com.acooly.module.obs.exceptions;

/**
 * 客户端访问服务遇到的异常。
 */
public class ClientException extends RuntimeException {

    private static final long serialVersionUID = 1870835486798448798L;

    private String errorMessage;
    private String requestId;
    private String errorCode;

    /**
     * 构造新实例。
     */
    public ClientException() {
        super();
    }

    /**
     * 用给定的异常信息构造新实例。
     *
     * @param errorMessage 异常信息。
     */
    public ClientException(String errorMessage) {
        this(errorMessage, null);
    }

    /**
     * 用表示异常原因的对象构造新实例。
     *
     * @param cause 异常原因。
     */
    public ClientException(Throwable cause) {
        this(null, cause);
    }

    /**
     * 用异常消息和表示异常原因的对象构造新实例。
     *
     * @param errorMessage 异常信息。
     * @param cause        异常原因。
     */
    public ClientException(String errorMessage, Throwable cause) {
        super(null, cause);
        this.errorMessage = errorMessage;
        this.errorCode = ClientErrorCode.UNKNOWN;
        this.requestId = "Unknown";
    }

    /**
     * 用异常消息构造新实例。
     *
     * @param errorMessage 异常信息。
     * @param errorCode    错误码。
     * @param requestId    请求编号。
     */
    public ClientException(String errorMessage, String errorCode, String requestId) {
        this(errorMessage, errorCode, requestId, null);
    }

    /**
     * 用异常消息和表示异常原因的对象构造新实例。
     *
     * @param errorMessage 异常信息。
     * @param errorCode    错误码。
     * @param requestId    请求编号。
     * @param cause        异常原因。
     */
    public ClientException(String errorMessage, String errorCode, String requestId, Throwable cause) {
        this(errorMessage, cause);
        this.errorCode = errorCode;
        this.requestId = requestId;
    }

    /**
     * 获取异常信息。
     *
     * @return 异常信息。
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 获取异常的错误码
     *
     * @return 异常错误码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 获取本次异常的 RequestId
     *
     * @return 本次异常的 RequestId
     */
    public String getRequestId() {
        return requestId;
    }

    @Override
    public String getMessage() {
        return getErrorMessage() + "\n[ErrorCode]: " + errorCode != null
                ? errorCode
                : "" + "\n[RequestId]: " + requestId != null ? requestId : "";
    }
}
