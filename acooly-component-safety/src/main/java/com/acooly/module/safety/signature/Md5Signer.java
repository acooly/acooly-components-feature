/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */
package com.acooly.module.safety.signature;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * MD5签名实现
 *
 * @author zhangpu
 * @date 2014年8月3日
 */
@Component("safetyMd5Signer")
public class Md5Signer extends AbstractDigestSigner {

    @Override
    protected String doSign(String waitToSignStr, String key) {
        return DigestUtils.md5Hex(waitToSignStr + key);
    }


    @Override
    public String getSinType() {
        return SignTypeEnum.MD5Hex.name();
    }

}
