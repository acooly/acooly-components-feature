package com.acooly.module.ocr.enums;
/**
 * @author liangsong
 * @date 2020, 03, 25 16:55
 */

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum ImageStatusEnum implements Messageable {
    normal("normal", "识别正常"),
    reversed_side("reversed_side", "身份证正反面颠倒"),
    non_idcard("non_idcard", "上传的图片中不包含身份证"),
    blurred("blurred", "身份证模糊"),
    other_type_card("other_type_card", "其他类型证照"),
    over_exposure("over_exposure", "身份证关键字段反光或过曝"),
    over_dark("over_dark", "身份证欠曝（亮度过低）"),
    unknown("unknown", "未知状态"),
    ;
    private final String code;
    private final String message;

    ImageStatusEnum(String code, String message) {
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
        for (ImageStatusEnum type : values()) {
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
    public static ImageStatusEnum find(String code) {
        for (ImageStatusEnum status : values()) {
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
    public static List<ImageStatusEnum> getAll() {
        List<ImageStatusEnum> list = new ArrayList<ImageStatusEnum>();
        for (ImageStatusEnum status : values()) {
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
        for (ImageStatusEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
