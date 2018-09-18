/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-10-07 21:32 创建
 */
package com.acooly.module.safety.support;

import com.acooly.core.utils.Encodes;
import com.acooly.core.utils.enums.Messageable;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpu 2017-10-07 21:32
 */
public enum CodecEnum implements Messageable, StringCodec {

    HEX("HEX", "十六进制") {
        @Override
        public String encode(byte[] data) {
            return Encodes.encodeHex(data);
        }

        @Override
        public byte[] decode(String data) {
            return Encodes.decodeHex(data);
        }
    },

    BASE64("BASE64", "BASE64") {
        @Override
        public String encode(byte[] data) {
            return Encodes.encodeBase64(data);
        }

        @Override
        public byte[] decode(String data) {
            return Encodes.decodeBase64(data);
        }
    };


    private final String code;
    private final String message;

    private CodecEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = Maps.newLinkedHashMap();
        for (CodecEnum type : values()) {
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
    public static CodecEnum find(String code) {
        for (CodecEnum status : values()) {
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
    public static List<CodecEnum> getAll() {
        List<CodecEnum> list = new ArrayList<CodecEnum>();
        for (CodecEnum status : values()) {
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
        for (CodecEnum status : values()) {
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
