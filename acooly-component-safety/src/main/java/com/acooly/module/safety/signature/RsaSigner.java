/*
 * acooly.cn Inc.
 * Copyright (c) 2016 All Rights Reserved.
 * create by zhangpu
 * date:2016年4月27日
 *
 */
package com.acooly.module.safety.signature;

import com.acooly.core.utils.security.RSA;
import com.acooly.module.safety.exception.SignatureVerifyException;
import com.acooly.module.safety.support.KeyPair;
import org.springframework.stereotype.Component;

/**
 * @author zhangpu
 */
@Component("safetyRsaSinger")
public class RsaSigner extends AbstractSigner<String, KeyPair> {


    @Override
    protected String getWaitToSigin(String plain) {
        return plain;
    }

    @Override
    protected String doSign(String waitToSignStr, KeyPair key) {
        key.loadKeys();
        byte[] signByte = RSA.sign(getBytes(waitToSignStr, key.getPlainEncode()), key.getPrivateKey(), key.getSignatureAlgo());
        return key.getSignatureCodec().encode(signByte);
    }

    @Override
    protected void doVerify(String waitForSign, KeyPair key, String sign) {
        key.loadKeys();
        boolean result = RSA.verify(getBytes(waitForSign, key.getPlainEncode()),
                key.getSignatureCodec().decode(sign), key.getPublicKey(), key.getSignatureAlgo());
        if (!result) {
            throw new SignatureVerifyException();
        }
    }


    @Override
    public String getSinType() {
        return SignTypeEnum.Rsa.name();
    }

}
