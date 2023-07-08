/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2022-2022. All rights reserved.
 */

package com.acooly.module.smsend.sender.support.huaweiyun;

/**
 * 华为云http工具
 * copy from 华为云SDK
 */
public final class Constant {
    public static final String HTTPS = "HTTPS";

    public static final String GM_PROTOCOL = "GMTLS";
    public static final String INTERNATIONAL_PROTOCOL = "TLSv1.2";
    public static final String SIGNATURE_ALGORITHM_SDK_HMAC_SHA256 = "SDK-HMAC-SHA256";
    public static final String SIGNATURE_ALGORITHM_SDK_HMAC_SM3 = "SDK-HMAC-SM3";
    public static final String[] SUPPORTED_CIPHER_SUITES = {"TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384"};

    private Constant() {
    }
}
