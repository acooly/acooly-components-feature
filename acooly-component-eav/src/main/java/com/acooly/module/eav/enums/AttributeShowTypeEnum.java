/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2019-03-03 20:34
 */
package com.acooly.module.eav.enums;
/**
 * 属性显示类型
 *
 * @author zhangpu
 * @date 2019-03-03 20:34
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum AttributeShowTypeEnum {


    LIST(1, "列表"),
    CREATE(2, "新增"),
    UPDATE(4, "编辑"),
    SHOW(8, "查看");


    private final int code;
    private final String message;

    AttributeShowTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    /**
     * 所有权限
     *
     * @return
     */
    public static int getAllValue() {
        int all = 0;
        for (AttributeShowTypeEnum status : values()) {
            all = all + status.getCode();
        }
        return all;
    }
    
    public static Map<Integer, String> mapping() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (AttributeShowTypeEnum type : values()) {
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
    public static AttributeShowTypeEnum find(int code) {
        for (AttributeShowTypeEnum status : values()) {
            if (status.getCode() == code) {
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
    public static List<AttributeShowTypeEnum> getAll() {
        List<AttributeShowTypeEnum> list = new ArrayList<AttributeShowTypeEnum>();
        for (AttributeShowTypeEnum status : values()) {
            list.add(status);
        }
        return list;
    }

    /**
     * 获取全部枚举值码。
     *
     * @return 全部枚举值码。
     */
    public static List<Integer> getAllCode() {
        List<Integer> list = new ArrayList<Integer>();
        for (AttributeShowTypeEnum status : values()) {
            list.add(status.getCode());
        }
        return list;
    }

}
