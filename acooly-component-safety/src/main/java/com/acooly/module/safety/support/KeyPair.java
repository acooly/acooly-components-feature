/*
 * acooly.cn Inc.
 * Copyright (c) 2016 All Rights Reserved.
 * create by zhangpu
 * date:2016年4月4日
 *
 */
package com.acooly.module.safety.support;

import com.acooly.core.utils.Strings;
import com.acooly.core.utils.security.RSA;
import lombok.extern.slf4j.Slf4j;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author zhangpu
 */
@Slf4j
public class KeyPair extends KeySupport {


    /**
     * 对方的公钥
     */
    private String publicKeySource;
    /**
     * 自己的私钥
     */
    private String privateKeySource;

    /**
     * 签名结果字符串编码
     */
    private CodecEnum signatureCodec = CodecEnum.BASE64;

    private String signatureAlgo = RSA.SIGN_ALGO_SHA1;

    /**
     * 签名明文字符串编码
     */
    private String plainEncode = "UTF-8";


    private PublicKey publicKey;
    private PrivateKey privateKey;

    public KeyPair() {
        super();
    }

    public KeyPair(String publicKeySource, String privateKeySource) {
        super();
        this.publicKeySource = publicKeySource;
        this.privateKeySource = privateKeySource;
    }

    public KeyPair loadKeys() {
        if (publicKey == null || privateKey == null) {
            synchronized (this) {
                if (publicKey == null || privateKey == null) {
                    initKeys();
                }
            }
        }
        return this;
    }

    protected void initKeys() {
        if (Strings.isNotBlank(publicKeySource)) {
            try {
                this.publicKey = RSA.loadPublicKey(loadKeyContent(this.publicKeySource, "UTF-8"), RSA.DEFAULT_KEY_ALGO, null);
                log.debug("load public key success. publicKey:\n{}", this.publicKey);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (Strings.isNotBlank(privateKeySource)) {
            try {
                this.privateKey = RSA.loadPrivateKey(loadKeyContent(this.privateKeySource, "UTF-8"), RSA.DEFAULT_KEY_ALGO,
                        null);
                log.debug("load private key success. key:\n{}", this.privateKey);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    public String getPublicKeySource() {
        return publicKeySource;
    }

    public void setPublicKeySource(String publicKeySource) {
        this.publicKeySource = publicKeySource;
    }

    public String getPrivateKeySource() {
        return privateKeySource;
    }

    public void setPrivateKeySource(String privateKeySource) {
        this.privateKeySource = privateKeySource;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public CodecEnum getSignatureCodec() {
        return signatureCodec;
    }

    public void setSignatureCodec(CodecEnum signatureCodec) {
        this.signatureCodec = signatureCodec;
    }

    public String getSignatureAlgo() {
        return signatureAlgo;
    }

    public void setSignatureAlgo(String signatureAlgo) {
        this.signatureAlgo = signatureAlgo;
    }

    public String getPlainEncode() {
        return plainEncode;
    }

    public void setPlainEncode(String plainEncode) {
        this.plainEncode = plainEncode;
    }

    @Override
    public String toString() {
        return "{publicKeySource:" + getPublicKeySource() + ", privateKeySource:" + getPrivateKeySource() + "}";
    }

}
