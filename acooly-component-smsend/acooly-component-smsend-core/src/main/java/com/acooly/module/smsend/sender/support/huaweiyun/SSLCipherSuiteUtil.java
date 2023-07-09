/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2022-2022. All rights reserved.
 */

package com.acooly.module.smsend.sender.support.huaweiyun;

import com.acooly.core.common.exception.BusinessException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.prng.SP800SecureRandomBuilder;
import org.openeuler.BGMProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Locale;

/**
 * 华为云http工具
 * copy from 华为云SDK
 */
public class SSLCipherSuiteUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SSLCipherSuiteUtil.class);
    private static CloseableHttpClient httpClient;

    private static final int CIPHER_LEN = 256;

    private static final int ENTROPY_BITS_REQUIRED = 384;

    public static HttpClient createHttpClient(String protocol) throws Exception {
        SSLContext sslContext = getSslContext(protocol);
        // create factory
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                new String[]{protocol}, Constant.SUPPORTED_CIPHER_SUITES, new TrustAllHostnameVerifier());

        httpClient = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
        return httpClient;
    }

    public static HttpURLConnection createHttpsOrHttpURLConnection(URL uUrl, String protocol) throws Exception {
        // initial connection
        if (uUrl.getProtocol().toUpperCase(Locale.getDefault()).equals(Constant.HTTPS)) {
            SSLContext sslContext = getSslContext(protocol);
            HttpsURLConnection.setDefaultHostnameVerifier(new TrustAllHostnameVerifier());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            return (HttpsURLConnection) uUrl.openConnection();
        }
        return (HttpURLConnection) uUrl.openConnection();
    }

    private static SSLContext getSslContext(String protocol) throws
            NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        if (!Constant.GM_PROTOCOL.equals(protocol) && !Constant.INTERNATIONAL_PROTOCOL.equals(protocol)) {
            LOGGER.info("Unsupport protocol: {}, Only support GMTLS TLSv1.2", protocol);
            throw new BusinessException("Unsupport_protocol", "Unsupport protocol, Only support GMTLS TLSv1.2", "");
        }
        // Create a trust manager that does not validate certificate chains
        TrustAllManager[] trust = {new TrustAllManager()};
        KeyManager[] kms = null;
        SSLContext sslContext;

        sslContext = SSLContext.getInstance(Constant.INTERNATIONAL_PROTOCOL, "SunJSSE");

        if (Constant.GM_PROTOCOL.equals(protocol)) {
            Security.insertProviderAt(new BGMProvider(), 1);
            sslContext = SSLContext.getInstance(Constant.GM_PROTOCOL, "BGMProvider");
        }
        SecureRandom secureRandom = getSecureRandom();
        sslContext.init(kms, trust, secureRandom);
        sslContext.getServerSessionContext().setSessionCacheSize(8192);
        sslContext.getServerSessionContext().setSessionTimeout(3600);
        return sslContext;
    }

    // 不校验域名
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    // 不校验服务端证书
    private static class TrustAllManager implements X509TrustManager {
        private X509Certificate[] issuers;

        public TrustAllManager() {
            this.issuers = new X509Certificate[0];
        }

        public X509Certificate[] getAcceptedIssuers() {
            return issuers;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
    }

    private static SecureRandom getSecureRandom() {
        SecureRandom source;
        try {
            source = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("get SecureRandom failed", e);
            throw new RuntimeException("get SecureRandom failed");
        }
        boolean predictionResistant = true;
        BlockCipher cipher = new AESEngine();
        boolean reSeed = false;
        return new SP800SecureRandomBuilder(source, predictionResistant).setEntropyBitsRequired(
                ENTROPY_BITS_REQUIRED).buildCTR(cipher, CIPHER_LEN, null, reSeed);
    }
}
