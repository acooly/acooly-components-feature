/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-10-08 00:46 创建
 */
package com.acooly.module.safety.test;

import com.acooly.module.safety.signature.Md5Signer;
import com.acooly.module.safety.signature.Sha1Signer;
import com.acooly.module.safety.signature.Sha256Signer;
import com.acooly.module.safety.signature.Signer;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;

/**
 * @author zhangpu 2017-10-08 00:46
 */
@Slf4j
public class DigestSignerTest {

    String key = "12345678901234567890123456789012";

    @Test
    public void testMD5() {
        doTest(new Md5Signer());
    }

    @Test
    public void testSha1() {
        doTest(new Sha1Signer());
    }

    @Test
    public void testSha2() {
        doTest(new Sha256Signer());
    }

    protected void doTest(Signer signer) {
        Map<String, String> plain = getPlainMap();
        log.info("signerType:{}, plain: {}", signer.getSinType(), plain);
        String signature = signer.sign(plain, key);
        log.info("signerType:{}, signature: {}", signer.getSinType(), signature);
        boolean verifyResult = false;
        try {
            signer.verify(plain, key, signature);
            verifyResult = true;
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        log.info("signerType:{}, verify: {} ", signer.getSinType(), verifyResult);
    }

    private Map<String, String> getPlainMap() {
        Map<String, String> plain = Maps.newHashMap();
        plain.put("1", "12312312");
        plain.put("a", "12asdf");
        plain.put("asdf", "2323");
        plain.put("ccc", "中国人");
        return plain;
    }

}
