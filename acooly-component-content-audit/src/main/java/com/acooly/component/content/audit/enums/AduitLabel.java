/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-07-24 14:04
 */
package com.acooly.component.content.audit.enums;
/**
 * @author zhangpu
 * @date 2021-07-24 14:04
 */

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum AduitLabel implements Messageable {

    normal("normal", "正常文本"),
    spam("spam", "含垃圾信息"),
    ad("ad", "广告"),
    politics("politics", "涉政"),
    terrorism("terrorism", "暴恐"),
    abuse("abuse", "辱骂"),
    porn("porn", "色情"),
    flood("flood", "灌水"),
    contraband("contraband", "违禁"),
    meaningless("meaningless", "无意义"),
    customized("customized", "自定义"),


    /**
     * 图片涉恐
     */
    bloody("bloody", "血腥"),
    explosion("explosion", "爆炸烟光"),
    outfit("outfit", "特殊装束"),
    logo("logo", "特殊标识"),
    weapon("weapon", "武器"),
    violence("violence", "打斗"),
    crowd("crowd", "聚众"),
    parade("parade", "游行"),
    flag("flag", "旗帜"),
    location("location", "地标"),
    others("others", "其他"),

    npx("npx", "牛皮癣广告"),
    qrcode("qrcode", "含二维码"),
    programCode("programCode", "含小程序码"),

    /**
     * 图片不良场景（live）结果分类
     */
    PIP("PIP", "画中画"),
    smoking("smoking", "吸烟"),
    drivelive("drivelive", "车内直播");
    private final String code;
    private final String message;

    AduitLabel(String code, String message) {
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
        for (AduitLabel type : values()) {
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
    public static AduitLabel find(String code) {
        for (AduitLabel status : values()) {
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
    public static List<AduitLabel> getAll() {
        List<AduitLabel> list = new ArrayList<AduitLabel>();
        for (AduitLabel status : values()) {
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
        for (AduitLabel status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
