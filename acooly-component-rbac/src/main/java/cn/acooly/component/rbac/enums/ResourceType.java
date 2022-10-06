/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-10-05
 *
 */
package cn.acooly.component.rbac.enums;

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限资源表 TypeEnum 枚举定义
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
public enum ResourceType implements Messageable {

    MENU("MENU", "‘菜单’"),
    FUNC("FUNC", "‘功能’"),
    URL(" URL", "‘链接’");

    private final String code;
    private final String message;

    private ResourceType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (ResourceType type : values()) {
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
    public static ResourceType find(String code) {
        for (ResourceType status : values()) {
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
    public static List<ResourceType> getAll() {
        List<ResourceType> list = new ArrayList<ResourceType>();
        for (ResourceType status : values()) {
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
        for (ResourceType status : values()) {
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

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

}
