/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-10-07 16:14 创建
 */
package com.acooly.module.safety.exception;

import com.acooly.core.utils.enums.Messageable;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpu 2017-10-07 16:14
 */
public enum SafetyResultCode implements Messageable {


    SIGN_TYPE_UNSUPPORTED("SIGN_TYPE_UNSUPPORTED", "签名类型不支持"),

    LOAD_KEY_ERROR("LOAD_KEY_ERROR", "秘钥加载失败"),

    LOAD_KEYSTORE_ERROR("LOAD_KEYSTORE_ERROR", "证书库加载失败"),

    LOAD_CERTIFICATE_ERROR("LOAD_CERTIFICATE_ERROR", "证书加载失败"),

    CERTIFICATE_ENCODING_ERROR("LOAD_CERTIFICATE_ERROR", "证书编码转换失败"),

    LOAD_KEYSTORE_PRIVATE_ERROR("LOAD_KEYSTORE_PRIVATE_ERROR", "证书库私钥加载失败"),

    NOT_EXSIST_KEYLOADMANAGER_PROVIDER_IMPL("NOT_EXSIST_KEYLOADMANAGER_PROVIDER_IMPL", "没有提供商对应的KeyloadManager实现"),

    NOT_EXSIST_KEYLOADER("NOT_EXSIST_KEYLOADER", "没有KeyLoader实现"),

    SIGN_CALC_ERROR("SIGN_CALC_ERROR", "签名失败"),

    SIGN_VERIFY_ERROR("SIGN_VERIFY_ERROR", "验证签名未通过");


    private final String code;
    private final String message;

    private SafetyResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = Maps.newLinkedHashMap();
        for (SafetyResultCode type : values()) {
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
    public static SafetyResultCode find(String code) {
        for (SafetyResultCode status : values()) {
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
    public static List<SafetyResultCode> getAll() {
        List<SafetyResultCode> list = new ArrayList<SafetyResultCode>();
        for (SafetyResultCode status : values()) {
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
        for (SafetyResultCode status : values()) {
            list.add(status.code());
        }
        return list;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

}
