/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-07-24 14:01
 */
package com.acooly.component.content.audit.enums;
/**
 * @author zhangpu
 * @date 2021-07-24 14:01
 * * pass：文本正常，可以直接放行。
 * * review：文本需要进一步人工审核。
 * * block：文本违规，可以直接删除或者限制公开。
 */

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum Suggestions implements Messageable {
    pass("pass", "文本正常"),
    review("review", "人工审核"),
    block("block", "文本违规");
    private final String code;
    private final String message;

    Suggestions(String code, String message) {
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
        for (Suggestions type : values()) {
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
    public static Suggestions find(String code) {
        for (Suggestions status : values()) {
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
    public static List<Suggestions> getAll() {
        List<Suggestions> list = new ArrayList<Suggestions>();
        for (Suggestions status : values()) {
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
        for (Suggestions status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
