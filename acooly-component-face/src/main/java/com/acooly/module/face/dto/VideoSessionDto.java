package com.acooly.module.face.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liangsong
 * @date 2020-03-23 09:31
 */
@Data
@ApiModel(value = "VideoSessionDto", description = "视频验证码dto")
public class VideoSessionDto implements Serializable {

    @ApiModelProperty(value = "业务订单号")
    private String bizOrderNo;

    /**
     * 会话id，校验时需使用传入
     */
    @ApiModelProperty(value = "会话id，校验时需使用传入")
    private String sessionId;

    /**
     * 语音校验码
     */
    @ApiModelProperty(value = "语音校验码")
    private String code;
}
