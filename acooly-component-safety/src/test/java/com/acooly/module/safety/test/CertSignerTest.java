/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-10-08 00:46 创建
 */
package com.acooly.module.safety.test;

import com.acooly.module.safety.signature.CertSigner;
import com.acooly.module.safety.support.CodecEnum;
import com.acooly.module.safety.support.KeyStoreInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author zhangpu 2017-10-08 00:46
 */
@Slf4j
public class CertSignerTest {


    String plain = "为是人民服务";


    @Test
    public void testPfx() {
        KeyStoreInfo keyStoreInfo = new KeyStoreInfo();
        keyStoreInfo.setKeyStoreType(KeyStoreInfo.KEY_STORE_PKCS12);
        keyStoreInfo.setCertificateUri("classpath:keystore/pfx/acooly.crt");
        keyStoreInfo.setKeyStoreUri("classpath:keystore/pfx/acooly.pfx");
        keyStoreInfo.setKeyStorePassword("123456");
        keyStoreInfo.setSignatureCodec(CodecEnum.HEX);

        CertSigner signer = new CertSigner();
        log.info("plain: {}", plain);
        String signature = signer.sign(plain, keyStoreInfo);
        log.info("signature: {}", signature);
        boolean verifyResult = false;
        try {
            signer.verify(plain, keyStoreInfo, signature);
            verifyResult = true;
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        log.info("verify: {} ", verifyResult);
    }


    @Test
    public void testJKS() {
        KeyStoreInfo keyStoreInfo = new KeyStoreInfo();
        keyStoreInfo.setKeyStoreType(KeyStoreInfo.KEY_STORE_JKS);
        keyStoreInfo.setCertificateUri("classpath:keystore/jks/acooly.cer");
        keyStoreInfo.setKeyStoreUri("classpath:keystore/jks/acooly.jks");
        keyStoreInfo.setKeyStorePassword("123456");
        keyStoreInfo.setSignatureAlgo("SHA256withRSA");
        keyStoreInfo.setSignatureCodec(CodecEnum.HEX);

        CertSigner signer = new CertSigner();
        log.info("plain: {}", plain);
        String signature = signer.sign(plain, keyStoreInfo);
        log.info("signature: {}", signature);
        boolean verifyResult = false;
        try {
            signer.verify(plain, keyStoreInfo, signature);
            verifyResult = true;
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        log.info("verify: {} ", verifyResult);
    }


}
