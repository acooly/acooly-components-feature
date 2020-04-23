/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-04-22 11:20
 */
package com.acooly.module.security.captche;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 *
 * @author zhangpu
 * @date 2020-04-22 11:20
 */
@Slf4j
public class CaptchaResult {
    private byte[] imageByte;
    private String imageType;
    private String answer;

    public CaptchaResult(byte[] imageByte, String imageType, String answer) {
        this.imageByte = imageByte;
        this.imageType = imageType;
        this.answer = answer;
    }

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Yaptcha{"
                + "imageByte="
                + Arrays.toString(imageByte)
                + ", imageType='"
                + imageType
                + '\''
                + ", answer='"
                + answer
                + '\''
                + '}';
    }
}
