/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-04-12 18:02
 */
package com.acooly.module.smsend.enums;
/**
 * @author zhangpu
 * @date 2020-04-12 18:02
 */

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum SmsendResultCode implements Messageable {

    NOT_SUPPORT_OPERATE("NOT_SUPPORT_OPERATE", "Provider不支持该操作"),
    NO_PROVIDER_AVAILABLE("NO_PROVIDER_AVAILABLE", "没有可用的发送提供者"),
    NETWORK_CONN_ERROR("NETWORK_CONN_ERROR", "通讯异常"),
    ALL_PROVIDER_QUOTA_FULL("ALL_PROVIDER_QUOTA_FULL", "号码发送配额不足");

    private final String code;
    private final String message;

    SmsendResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (SmsendResultCode type : values()) {
            map.put(type.getCode(), type.getMessage());
        }
        return map;
    }

    /**
     * 通过枚举值码查找枚举值。
     *
     * @param code 查找枚举值的枚举值码。
     * @return 枚举值码对应的枚举值。
     * @throws IllegalArgumentException 如果 code 没有对应的 Status 。
     */
    public static SmsendResultCode find(String code) {
        for (SmsendResultCode status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举值。
     *
     * @return 全部枚举值。
     */
    public static List<SmsendResultCode> getAll() {
        List<SmsendResultCode> list = new ArrayList<SmsendResultCode>();
        for (SmsendResultCode status : values()) {
            list.add(status);
        }
        return list;
    }

    /**
     * 获取全部枚举值码。
     *
     * @return 全部枚举值码。
     */
    public static List<String> getAllCode() {
        List<String> list = new ArrayList<String>();
        for (SmsendResultCode status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
