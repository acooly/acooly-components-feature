/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-24 21:59 创建
 */
package com.acooly.module.certification;

import com.acooly.core.utils.enums.Messageable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;

import static com.acooly.module.certification.CertificationProperties.BankCertProvider.ALI;
import static com.acooly.module.certification.CertificationProperties.PREFIX;

/**
 * @author zhike@yiji.com
 * @author shuijing
 */
@ConfigurationProperties(prefix = PREFIX)
@Data
@Slf4j
@Validated
public class CertificationProperties {
    public static final String PREFIX = "acooly.certification";

    private Boolean enable = true;
    /**
     * 实名认证
     */
    private Provider provider = Provider.ALI;

    /**
     * 银行卡认证，默认为阿里云
     */
    private BankCertProvider bankCertProvider = ALI;

    /**
     * 工商信息查询，默认为阿里云
     */
    private EnterpriseBsuiInfoProvider enterpriseBsuiInfoProvider;

    /**
     * 实名认证url 当使用非阿里云时配置
     */
    private String realnameurl;
    /**
     * 用阿里云实名认证配置
     */
    private RealName realname;
    /**
     * 用阿里云银行卡要素认证配置
     */
    private BankCert bankcert;

    /**
     * 企业工商信息查询配置
     */
    private EnterpriseBsuiInfo enterpriseBsuiInfo;


    @PostConstruct
    public void init() {
        if (this.provider == Provider.ALI) {
            Assert.notNull(realname, "实名认证渠道配置不能为空");
            Assert.hasText(realname.getAppCode(), "实名认证渠道配置不能为空");
        }
        if (this.bankCertProvider == ALI) {
            Assert.notNull(bankcert, "银行卡认证渠道配置不能为空");
            Assert.hasText(bankcert.getAppCode(), "银行卡认证渠道配置不能为空");
        }

        if (this.enterpriseBsuiInfoProvider == EnterpriseBsuiInfoProvider.ALI) {
            Assert.notNull(enterpriseBsuiInfo, "企业信息查询渠道配置不能为空");
            Assert.hasText(enterpriseBsuiInfo.getAppCode(), "企业信息查询渠道配置不能为空");
        }
    }

    public enum Provider implements Messageable {
        /**
         * 阿里实名认证
         */
        ALI("aliRealNameAuthentication", "阿里实名认证");

        private final String code;
        private final String message;

        Provider(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String code() {
            return this.code;
        }

        @Override
        public String message() {
            return this.message;
        }
    }

    public enum BankCertProvider implements Messageable {
        /**
         * 阿里银行卡二三四要素认证
         */
        ALI("aliBankCardCertService", "阿里银行卡二三四要素认证");
        private final String code;
        private final String message;

        BankCertProvider(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String code() {
            return this.code;
        }

        @Override
        public String message() {
            return this.message;
        }
    }

    public enum EnterpriseBsuiInfoProvider implements Messageable {
        /**
         * 阿里工商信息查询
         */
        ALI("aliEnterpriseBusinessInfoService", "阿里工商信息查询");
        private final String code;
        private final String message;

        EnterpriseBsuiInfoProvider(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String code() {
            return this.code;
        }

        @Override
        public String message() {
            return this.message;
        }
    }

    @Data
    public static class RealName {
        /**
         * 实名appCode
         */
        private String appCode;

        private int timeout = 20000;
    }

    @Data
    public static class BankCert {
        /**
         * 银行卡要素二三四要素appCode
         */
        private String appCode;

        private int timeout = 20000;
    }

    @Data
    public static class EnterpriseBsuiInfo {
        /**
         * 企业工商信息查询appCode
         */
        private String appCode;

        private int timeout = 20000;
    }
}
