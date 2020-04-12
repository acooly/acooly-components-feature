package com.acooly.module.smsend.enums;

import java.util.HashMap;
import java.util.Map;

public enum SmsStatus {
    UN_SEND(1, "未发送"),

    SEND_FAIL(2, "发送失败"),

    SEND_SUCCESS(3, "已发送");

    private int code;
    private String message;

    SmsStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static SmsStatus getByCode(int code) {
        for (SmsStatus _enum : values()) {
            if (_enum.getCode() == code) {
                return _enum;
            }
        }
        return null;
    }

    public static Map<Integer, String> getMapping() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (SmsStatus _enum : values()) {
            map.put(_enum.getCode(), _enum.getMessage());
        }
        return map;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.code + ":" + this.message;
    }
}
