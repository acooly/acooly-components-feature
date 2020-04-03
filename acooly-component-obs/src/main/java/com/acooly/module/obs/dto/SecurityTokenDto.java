package com.acooly.module.obs.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liangsong
 * @date 2020-04-02 16:12
 */
@Data
public class SecurityTokenDto implements Serializable {

    private String securityToken;

    private String accessKeyId;

    private String accessKeySecret;

    /**
     * 授权的过期时间
     */
    private String expiration;
}
