package com.acooly.module.ocr.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liangsong
 * @date 2020-03-25 16:21
 */
@Data
public class IdCardOcrFrontDto implements Serializable {

    /**
     * 姓名
     */
    private String name;

    /**
     * 民族（例如：汉）
     */
    private String nation;

    /**
     * 住址
     */
    private String address;

    /**
     * 身份证号
     */
    private String idCardNo;
    /**
     * 生日，例如：19890701
     */
    private String brithday;

    /**
     * 性别，例如：男
     */
    private String sex;
}
