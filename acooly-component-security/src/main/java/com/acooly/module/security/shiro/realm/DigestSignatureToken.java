/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2023-12-07 13:57
 */
package com.acooly.module.security.shiro.realm;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 摘要签名token
 *
 * @author zhangpu
 * @date 2023-12-07 13:57
 */
@Slf4j
@Getter
@Setter
public class DigestSignatureToken extends UsernamePasswordToken {

    /**
     * 登录的手机号码
     */
    private String mobileNo;
    /**
     * 请求的访问码（由平台分配的密钥对的访问码，也用于标记来源渠道）
     */
    private String accessKey;
    /**
     * 请求时间戳（格式为：yyyyMMddHHmmss）
     */
    private String timestamp;

    /**
     * 报文签名
     * 签名算法：MD5(accessKey+mobileNo+timestamp+secretKey)
     * 请注意待摘要签名的字符串顺序，secretKey为平台分配的密钥对的安全码
     */
    private String sign;


    public DigestSignatureToken(String mobileNo, String accessKey, String timestamp, String sign) {
        this.mobileNo = mobileNo;
        this.accessKey = accessKey;
        this.timestamp = timestamp;
        this.sign = sign;
    }


    @Override
    public Object getCredentials() {
        return this.sign;
    }

    @Override
    public String toString() {
        return "DigestSignatureToken: " + getUsername() + ", " + mobileNo + ", " + accessKey;
    }
}
