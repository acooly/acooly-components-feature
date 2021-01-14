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
    video("video", "视频", "mp4,avi,mpg,mkv,mv,mov"),
    voice("voice", "音频", "mp3,wav"),
    app("app", "程序", "apk,ipa"),
    document("document", "文档", "doc,docx,xls,xlsx,pdf,txt,json"),
    other("other", "其它", "");

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
        if (Strings.isBlank(fileExtention)) {
            return OFileType.other;
        }
        for (OFileType type : values()) {
            if (Strings.contains(type.getExtentions(), fileExtention.toLowerCase())) {
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
