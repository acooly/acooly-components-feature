package com.acooly.module.face.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liangsong
 * @date 2020-03-20 15:18
 */
@Data
public class VideoSessionCodeOrder implements Serializable {

    /**
     * 相关联的业务订单号，原样返回
     */
    private String bizOrderNo;

    /**
     * 生成的验证码最小长度 最大6 最小3 默认3
     */
    private String minCodeLength;

    /**
     * 生成的验证码最大长度 最大6 最小3 默认3
     */
    private String maxCodeLength;
}
