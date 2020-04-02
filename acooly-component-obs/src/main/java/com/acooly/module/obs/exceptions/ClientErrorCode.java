package com.acooly.module.obs.exceptions;

public interface ClientErrorCode {

    /**
     * 未知错误。
     */
    static final String UNKNOWN = "Unknown";

    /**
     * 未知域名。
     */
    static final String UNKNOWN_HOST = "UnknownHost";

    /**
     * 远程服务连接超时。
     */
    static final String CONNECTION_TIMEOUT = "ConnectionTimeout";

    /**
     * 远程服务socket读写超时。
     */
    static final String SOCKET_TIMEOUT = "SocketTimeout";

    /**
     * 远程服务socket异常。
     */
    static final String SOCKET_EXCEPTION = "SocketException";

    /**
     * 远程服务拒绝连接。
     */
    static final String CONNECTION_REFUSED = "ConnectionRefused";

    /**
     * 请求输入流不可重复读取。
     */
    static final String NONREPEATABLE_REQUEST = "NonRepeatableRequest";
}
