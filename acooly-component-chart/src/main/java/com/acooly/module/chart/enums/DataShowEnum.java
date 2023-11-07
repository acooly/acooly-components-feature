/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 *
 */
package com.acooly.module.chart.enums;

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 图表-图表选项 StatusEnum 枚举定义
 *
 * @author acooly
 * Date: 2018-10-10 11:15:20
 */
public enum DataShowEnum implements Messageable {

    YES("YES", "是", true),

    NO("NO", "否", false),

    ;

    private final String code;
    private final String message;

    private final boolean flag;


    private DataShowEnum(String code, String message, boolean flag) {
        this.code = code;
        this.message = message;
        this.flag = flag;
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

    public boolean isFlag() {
        return flag;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (DataShowEnum type : values()) {
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
    public static DataShowEnum find(String code) {
        for (DataShowEnum status : values()) {
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
    public static List<DataShowEnum> getAll() {
        List<DataShowEnum> list = new ArrayList<DataShowEnum>();
        for (DataShowEnum status : values()) {
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
        for (DataShowEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
