package com.acooly.module.scheduler.executor;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author shuijing
 */
public enum TaskStatusEnum {
    NORMAL("NORMAL", "正常"),
    CANCELED("CANCELED", "作废");

    @Setter
    @Getter
    private String code;

    @Setter
    @Getter
    private String message;

    TaskStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static TaskStatusEnum getEnumByCode(String code) {
        for (TaskStatusEnum t : TaskStatusEnum.values()) {
            if (StringUtils.equalsIgnoreCase(t.code, code)) {
                return t;
            }
        }
        return null;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = new LinkedHashMap<>();
        for (TaskStatusEnum type : values()) {
            map.put(type.getCode(), type.getMessage());
        }
        return map;
    }
}
