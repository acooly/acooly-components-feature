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
 * 方案功能权限
 *
 * @author zhangpu
 * @date 2019-03-03 20:34
 */

import com.acooly.core.utils.arithmetic.perm.BitPermissions;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum SchemePermissionEnum {

    LIST(1, "列表"),
    CREATE(2, "新增"),
    UPDATE(4, "编辑"),
    DELETE(8, "删除"),
    SHOW(16, "查看");


    private final int code;
    private final String message;

    SchemePermissionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static List<String> parse(int perm) {
        List<String> permNames = Lists.newArrayList();
        for (SchemePermissionEnum status : values()) {
            if (BitPermissions.hasPerm(perm, status.code)) {
                permNames.add(status.getMessage());
            }
        }
        return permNames;
    }

    /**
     * 所有权限
     *
     * @return
     */
    public static int getAllValue() {
        int all = 0;
        for (SchemePermissionEnum status : values()) {
            all = all + status.getCode();
        }
        return all;
    }

    public static Map<Integer, String> mapping() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (SchemePermissionEnum type : values()) {
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
    public static SchemePermissionEnum find(int code) {
        for (SchemePermissionEnum status : values()) {
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
    public static List<SchemePermissionEnum> getAll() {
        List<SchemePermissionEnum> list = new ArrayList<SchemePermissionEnum>();
        for (SchemePermissionEnum status : values()) {
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
        for (SchemePermissionEnum status : values()) {
            list.add(status.getCode());
        }
        return list;
    }

}
