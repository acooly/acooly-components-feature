/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-02-20 01:32 创建
 */
package com.acooly.module.cms.enums;

import com.acooly.core.utils.enums.Messageable;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 内容查询方式
 *
 * @author acooly
 * @date 2019-01-01
 */
public enum ContentQueryTypeEnum implements Messageable {


    id("id", "主键查询"),

    key("key", "唯一编码查询"),

    type("type", "类型编码最新查询");

    private final String code;
    private final String message;

    private ContentQueryTypeEnum(String code, String message) {
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
        Map<String, String> map = Maps.newLinkedHashMap();
        for (ContentQueryTypeEnum type : values()) {
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
    public static ContentQueryTypeEnum find(String code) {
        for (ContentQueryTypeEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("ContentSearchType not legal:" + code);
    }

    /**
     * 获取全部枚举值。
     *
     * @return 全部枚举值。
     */
    public static List<ContentQueryTypeEnum> getAll() {
        List<ContentQueryTypeEnum> list = new ArrayList<ContentQueryTypeEnum>();
        for (ContentQueryTypeEnum status : values()) {
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
        for (ContentQueryTypeEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", this.code, this.message);
    }

}
