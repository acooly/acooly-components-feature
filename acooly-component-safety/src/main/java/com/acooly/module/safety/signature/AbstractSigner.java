package com.acooly.module.safety.signature;

import com.acooly.module.safety.exception.SignatureException;
import com.acooly.module.safety.exception.SignatureVerifyException;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by zhangpu on 2015/1/23.
 */
@Slf4j
public abstract class AbstractSigner<T, K> implements Signer<T, K> {


    @Override
    public String sign(T plain, K key) {
        String waitForSign = getWaitToSigin(plain);
        try {
            log.debug("待签字符串: {}", waitForSign);
            return doSign(waitForSign, key);
        } catch (Exception e) {
            log.warn("签名计算错误, plain:{}, 待签字符串:{}, 原因：{}", plain, waitForSign, e.getMessage());
            throw new SignatureException("待签字符串:" + waitForSign);
        }

    }

    @Override
    public void verify(T plain, K key, String sign) {
        String waitForSign = getWaitToSigin(plain);
        try {
            doVerify(waitForSign, key, sign);
        } catch (Exception e) {
            log.warn("验签未通过. requestSign:{},calcPlain:{}", getSinType(), sign, plain);
            throw new SignatureVerifyException("待签字符串:" + waitForSign);
        }
    }

    protected byte[] getBytes(String plain, String encode) {
        try {
            return plain.getBytes(encode);
        } catch (Exception e) {
            throw new RuntimeException("不支持对待签名字符串的编码转换(" + encode + ")");
        }
    }


    protected abstract String getWaitToSigin(T plain);

    protected abstract void doVerify(String waitForSign, K key, String sign);

    protected abstract String doSign(String waitForSign, K key);

}
