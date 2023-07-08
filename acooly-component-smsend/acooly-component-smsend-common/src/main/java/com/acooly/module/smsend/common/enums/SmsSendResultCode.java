/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-04-12 18:02
 */
package com.acooly.module.smsend.common.enums;
/**
 * @author zhangpu
 * @date 2020-04-12 18:02
 */

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum SmsSendResultCode implements Messageable {

    SUCCESS("SUCCESS", "发送成功"),
    TEMPLATE_PROVIDER_NOT_FOUND("TEMPLATE_PROVIDER_NOT_FOUND", "渠道模板未配置"),
    APP_AUTH_ERROR("APP_AUTH_ERROR", "AppId认证失败"),
    DATA_PARSE_ERORR("DATA_PARSE_ERORR", "数据解析错误"),
    RATE_LIMIT_MOBILE("RATE_LIMIT_MOBILE", "手机号码限流"),
    RATE_LIMIT_IP("RATE_LIMIT_IP", "发送IP限流"),
    BLACK_LIST_HIT("BLACK_LIST_HIT", "黑名单命中"),
    NOT_SUPPORT_OPERATE("NOT_SUPPORT_OPERATE", "Provider不支持该操作"),
    NO_PROVIDER_AVAILABLE("NO_PROVIDER_AVAILABLE", "没有可用的发送提供者"),
    NETWORK_CONN_ERROR("NETWORK_CONN_ERROR", "通讯异常"),
    ALL_PROVIDER_QUOTA_FULL("ALL_PROVIDER_QUOTA_FULL", "号码发送配额不足"),

    RECEIVER_MOBILE_EMPTY("RECEIVER_MOBILE_EMPTY", "接收手机号码不能为空"),

    ;

    private final String code;
    private final String message;

    SmsSendResultCode(String code, String message) {
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
        for (SmsSendResultCode type : values()) {
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
    public static SmsSendResultCode find(String code) {
        for (SmsSendResultCode status : values()) {
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
    public static List<SmsSendResultCode> getAll() {
        List<SmsSendResultCode> list = new ArrayList<SmsSendResultCode>();
        for (SmsSendResultCode status : values()) {
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
        for (SmsSendResultCode status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
