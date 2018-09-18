/**
 * create by zhangpu date:2015年5月19日
 */
package com.acooly.module.portlet.enums;

import com.acooly.core.utils.enums.Messageable;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 客户反馈信息处理状态
 *
 * @author zhangpu
 */
public enum FeedbackStatusEnum implements Messageable {
    submit("submit", "未处理"),

    processing("processing", "处理中"),

    complete("complete", "已处理");

    private final String code;
    private final String message;

    FeedbackStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = Maps.newLinkedHashMap();
        for (FeedbackStatusEnum type : values()) {
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
    public static FeedbackStatusEnum find(String code) {
        for (FeedbackStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("FeedbackType not legal:" + code);
    }

    /**
     * 获取全部枚举值。
     *
     * @return 全部枚举值。
     */
    public static List<FeedbackStatusEnum> getAll() {
        List<FeedbackStatusEnum> list = new ArrayList<FeedbackStatusEnum>();
        for (FeedbackStatusEnum status : values()) {
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
        for (FeedbackStatusEnum status : values()) {
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
