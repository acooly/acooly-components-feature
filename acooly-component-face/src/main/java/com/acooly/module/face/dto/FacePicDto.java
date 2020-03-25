package com.acooly.module.face.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author liangsong
 * @date 2020-03-23 16:26
 */
@Data
@ApiModel(value = "FacePicDto", description = "人脸图片dto")
public class FacePicDto implements Serializable {

    /**
     * 人脸图片的唯一标识
     */
    @ApiModelProperty(value = "人脸图片的唯一标识")
    private String faceId;

    /**
     * 人脸图片的唯一标识
     */
    @ApiModelProperty(value = "人脸图片的唯一标识")
    private String faceToken;

    /**
     * base64编码后的图片信息
     */
    @ApiModelProperty(value = "base64编码后的图片信息")
    private String pic;

    /**
     * 此图片的活体分数，范围[0,1]
     */
    @ApiModelProperty(value = "此图片的活体分数，范围[0,1]")
    private BigDecimal livenessScore;

    /**
     * 判断此图片是合成图的分数，范围[0,1]
     */
    @ApiModelProperty(value = "判断此图片是合成图的分数，范围[0,1]")
    private BigDecimal spoofing;
}
