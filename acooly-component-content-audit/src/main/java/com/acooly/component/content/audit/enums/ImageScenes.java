/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-07-24 15:25
 */
package com.acooly.component.content.audit.enums;
/**
 *
 * @author zhangpu
 * @date 2021-07-24 15:25
 */

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum ImageScenes implements Messageable {
    porn("porn","图片智能鉴黄"),
    terrorism("terrorism","图片暴恐涉政"),
    ad("ad","图文违规"),
    qrcode("qrcode","图片二维码"),
    live("live","图片不良场景"),
    logo("logo","图片logo"),
    ;
    private final String code;
    private final String message;

    ImageScenes(String code, String message) {
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
        for (ImageScenes type : values()) {
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
    public static ImageScenes find(String code) {
        for (ImageScenes status : values()) {
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
    public static List<ImageScenes> getAll() {
        List<ImageScenes> list = new ArrayList<ImageScenes>();
        for (ImageScenes status : values()) {
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
        for (ImageScenes status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
