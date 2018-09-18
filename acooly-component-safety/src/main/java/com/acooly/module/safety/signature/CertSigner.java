/**
 * create by zhangpu
 * date:2015年3月4日
 */
package com.acooly.module.safety.signature;

import com.acooly.module.safety.support.KeyStoreInfo;
import org.springframework.stereotype.Component;

import java.security.Signature;
import java.security.cert.X509Certificate;

/**
 * @author zhangpu
 */
@Component("safetyCertSigner")
public class CertSigner extends AbstractSigner<String, KeyStoreInfo> {


    @Override
    protected String getWaitToSigin(String plain) {
        return plain;
    }

    @Override
    protected void doVerify(String waitForSign, KeyStoreInfo key, String sign) {
        try {
            X509Certificate x509Certificate = key.loadKeys().getCertificate();
            Signature signature = Signature.getInstance(key.getSignatureAlgo());
            signature.initVerify(x509Certificate.getPublicKey());
            signature.update(waitForSign.getBytes(key.getPlainEncode()));
            boolean result = signature.verify(key.getSignatureCodec().decode(sign));
            if (!result) {
                throw new RuntimeException("签名未通过");
            }
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            throw new RuntimeException("证书验签失败:" + e.getMessage());
        }
    }

    @Override
    protected String doSign(String waitToSignStr, KeyStoreInfo key) {
        try {
            Signature signature = Signature.getInstance(key.getSignatureAlgo());
            signature.initSign(key.loadKeys().getPrivateKey());
            signature.update(waitToSignStr.getBytes(key.getPlainEncode()));
            byte[] sign = signature.sign();
            return key.getSignatureCodec().encode(sign);
        } catch (Exception e) {
            throw new RuntimeException("证书签名失败:" + e.getMessage());
        }
    }


    @Override
    public String getSinType() {
        return SignTypeEnum.Cert.name();
    }


}
