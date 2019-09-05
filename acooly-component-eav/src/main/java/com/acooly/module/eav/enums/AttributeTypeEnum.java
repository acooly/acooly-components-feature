/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-26
 *
 */
package com.acooly.module.eav.enums;

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * eav_attribute AttributeTypeEnum 枚举定义
 *
 * @author qiubo
 * Date: 2018-06-26 21:51:37
 */
public enum AttributeTypeEnum implements Messageable {

    STRING("STRING", "字符串"),

    ENUM("ENUM", "枚举"),

    NUMBER_INTEGER("NUMBER_INTEGER", "整数"),

    NUMBER_DECIMAL("NUMBER_DECIMAL", "小数"),

    NUMBER_MONEY("NUMBER_MONEY", "金额"),

    DATE("DATE", "日期时间");

    private final String code;
    private final String message;

    private AttributeTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
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

    public static Map<String, String> mapping() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (AttributeTypeEnum type : values()) {
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
    public static AttributeTypeEnum find(String code) {
        for (AttributeTypeEnum status : values()) {
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
    public static List<AttributeTypeEnum> getAll() {
        List<AttributeTypeEnum> list = new ArrayList<AttributeTypeEnum>();
        for (AttributeTypeEnum status : values()) {
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
        for (AttributeTypeEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
