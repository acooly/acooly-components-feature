/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-03-01
 *
 */
package com.acooly.module.portlet.enums;

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 常见 site config的key
 *
 * @author acooly Date: 2017-03-01 00:53:18
 */
public enum SiteConfigKeyEnum implements Messageable {
    serviceTel("serviceTel", "客服热线"),

    serviceWorkHours("serviceWorkHours", "服务时间"),

    serviceEmail("serviceEmail", "服务邮箱"),

    serviceWeibo("serviceWeibo", "服务微博"),

    serviceQQ("serviceQQ", "服务QQ"),

    serviceQQGroup("serviceQQGroup", "服务QQ群"),

    serviceWeChat("serviceWeChat", "微信公众号");

    private final String code;
    private final String message;

    private SiteConfigKeyEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (SiteConfigKeyEnum type : values()) {
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
    public static SiteConfigKeyEnum find(String code) {
        for (SiteConfigKeyEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Type not legal:" + code);
    }

    /**
     * 获取全部枚举值。
     *
     * @return 全部枚举值。
     */
    public static List<SiteConfigKeyEnum> getAll() {
        List<SiteConfigKeyEnum> list = new ArrayList<SiteConfigKeyEnum>();
        for (SiteConfigKeyEnum status : values()) {
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
        for (SiteConfigKeyEnum status : values()) {
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

    @Override
    public String toString() {
        return String.format("%s:%s", this.code, this.message);
    }
}
