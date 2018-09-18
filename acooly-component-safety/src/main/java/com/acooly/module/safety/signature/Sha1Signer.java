package com.acooly.module.safety.signature;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * Sha1Hex 签名和验签
 * <p/>
 * Created by zhangpu on 2015/1/23.
 */
@Component("safetySha1Signer")
public class Sha1Signer extends AbstractDigestSigner {

    @Override
    protected String doSign(String waitToSignStr, String key) {
        return DigestUtils.sha1Hex(waitToSignStr + key);
    }

    @Override
    public String getSinType() {
        return SignTypeEnum.Sha1Hex.name();
    }
}
