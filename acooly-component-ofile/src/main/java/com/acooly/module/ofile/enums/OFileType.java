/**
 * create by zhangpu date:2015年7月12日
 */
package com.acooly.module.ofile.enums;

import com.acooly.core.utils.Strings;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author zhangpu
 */
public enum OFileType {

    picture("picture", "图片", "jpg,jpeg,png,gif,bmp"),
    app("app", "程序", "apk,ipa"),
    other("other", "未知", "");

    /**
     * code
     */
    private String code;
    /**
     * message
     */
    private String message;
    /**
     * 扩展名
     */
    private String extentions;

    OFileType(String code, String message, String extentions) {
        this.code = code;
        this.message = message;
        this.extentions = extentions;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = Maps.newLinkedHashMap();
        for (OFileType type : values()) {
            map.put(type.getCode(), type.getMessage());
        }
        return map;
    }

    public static OFileType with(String fileExtention) {
        for (OFileType type : values()) {
            if (Strings.contains(type.getExtentions(), fileExtention)) {
                return type;
            }
        }
        return OFileType.other;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getExtentions() {
        return extentions;
    }

}
