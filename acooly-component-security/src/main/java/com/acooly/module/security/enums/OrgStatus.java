package com.acooly.module.security.enums;

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shuijing
 */
public enum OrgStatus implements Messageable {
    valid("valid", "有效"),
    invalid("invalid", "无效"),;

    private final String code;
    private final String message;

    OrgStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (OrgStatus type : values()) {
            map.put(type.getCode(), type.getMessage());
        }
        return map;
    }

    public static OrgStatus find(String code) {
        for (OrgStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    public static List<OrgStatus> getAll() {
        List<OrgStatus> list = new ArrayList<OrgStatus>();
        for (OrgStatus status : values()) {
            list.add(status);
        }
        return list;
    }

    public static List<String> getAllCode() {
        List<String> list = new ArrayList<String>();
        for (OrgStatus status : values()) {
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
}
