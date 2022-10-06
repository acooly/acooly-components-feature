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
 * 用户表 StatusEnum 枚举定义
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
public enum UserStatus implements Messageable {

    enable("enable", "’有效’"),
    expired("expired", "’过期’"),
    locked("locked", "’锁定’"),
    disable("disable", "’禁用’");

    private final String code;
    private final String message;

    private UserStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (UserStatus type : values()) {
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
    public static UserStatus find(String code) {
        for (UserStatus status : values()) {
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
    public static List<UserStatus> getAll() {
        List<UserStatus> list = new ArrayList<UserStatus>();
        for (UserStatus status : values()) {
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
        for (UserStatus status : values()) {
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
