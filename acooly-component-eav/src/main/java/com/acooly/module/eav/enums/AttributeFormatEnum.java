/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2019-03-03 21:00
 */
package com.acooly.module.eav.enums;
/**
 * 属性显示格式
 *
 * @author zhangpu
 * @date 2019-03-03 21:00
 */

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum AttributeFormatEnum implements Messageable {

    NORMAL("NORMAL", "原始格式", null),
    FORMAT_DATA_TIME("FORMAT_DATA_TIME", "日期时间", "yyyy-MM-dd HH:mm:ss"),
    FORMAT_DATA("FORMAT_DATA", "日期", "yyyy-MM-dd"),
    FORMAT_TIME("FORMAT_TIME", "时间", "HH:mm:ss");

    private final String code;
    private final String message;
    private final String pattern;

    AttributeFormatEnum(String code, String message, String pattern) {
        this.code = code;
        this.message = message;
        this.pattern = pattern;
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

    public String getPattern() {
        return pattern;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (AttributeFormatEnum type : values()) {
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
    public static AttributeFormatEnum find(String code) {
        for (AttributeFormatEnum status : values()) {
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
    public static List<AttributeFormatEnum> getAll() {
        List<AttributeFormatEnum> list = new ArrayList<AttributeFormatEnum>();
        for (AttributeFormatEnum status : values()) {
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
        for (AttributeFormatEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
