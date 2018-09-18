package com.acooly.module.safety.signature;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * Sha256Hex 签名和验签
 * <p/>
 * Created by zhangpu on 2015/1/23.
 */
@Component("safetySha256Signer")
public class Sha256Signer extends AbstractDigestSigner {

    @Override
    protected String doSign(String waitToSignStr, String key) {
        return DigestUtils.sha256Hex(waitToSignStr + key);
    }

    @Override
    public String getSinType() {
        return SignTypeEnum.Sha256Hex.name();
    }
}
