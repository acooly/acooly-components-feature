package com.acooly.module.scheduler.executor;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author shuijing
 */
public enum TaskTypeEnum {
    HTTP_TASK("HTTP", "HTTP任务"),
    DUBBO_TASK("DUBBO", "DUBBO任务"),
    LOCAL_TASK("LOCAL", "本地任务");

    @Setter
    @Getter
    private String code;

    @Setter
    @Getter
    private String message;

    TaskTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static TaskTypeEnum getEnumByCode(String code) {
        for (TaskTypeEnum t : TaskTypeEnum.values()) {
            if (StringUtils.equalsIgnoreCase(t.code, code)) {
                return t;
            }
        }
        return null;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = new LinkedHashMap<>();
        for (TaskTypeEnum type : values()) {
            map.put(type.getCode(), type.getMessage());
        }
        return map;
    }
}
