/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-04-12 15:31
 */
package com.acooly.module.smsend.enums;
/**
 * @author zhangpu
 * @date 2020-04-12 15:31
 */

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum SmsProvider implements Messageable {

    /**
     * 漫道
     */
    MaiDao("MaiDao", "漫道"),

    /**
     * 亿美
     */
    EMay("EMay", "亿美"),
    /**
     * 阿里云
     */
    Aliyun("Aliyun", "阿里云"),


    AnyCmp("AnyCmp","多云"),

    /**
     * 容联.云通讯
     */
    Cloopen("Cloopen", "容联云");

    private final String code;
    private final String message;

    SmsProvider(String code, String message) {
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
        for (SmsProvider type : values()) {
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
    public static SmsProvider find(String code) {
        for (SmsProvider status : values()) {
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
    public static List<SmsProvider> getAll() {
        List<SmsProvider> list = new ArrayList<SmsProvider>();
        for (SmsProvider status : values()) {
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
        for (SmsProvider status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
