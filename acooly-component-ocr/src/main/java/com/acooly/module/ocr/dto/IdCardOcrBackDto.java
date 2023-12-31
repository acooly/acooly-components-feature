package com.acooly.module.ocr.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liangsong
 * @date 2020-03-25 16:22
 */
@Data
public class IdCardOcrBackDto implements Serializable {

    /**
     * 签发日期，格式：2017-08-04
     */
    private String issuanceDate;

    /**
     * 失效日期，格式：2017-08-04或者长期
     */
    private String expiryDate;

    /**
     * 签发机关
     */
    private String authority;
}
