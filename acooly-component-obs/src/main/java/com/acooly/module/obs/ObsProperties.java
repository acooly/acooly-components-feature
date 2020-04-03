package com.acooly.module.obs;

import com.acooly.core.utils.enums.Messageable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import static com.acooly.module.obs.ObsProperties.PREFIX;

/**
 * @author shuijing
 */
@ConfigurationProperties(prefix = PREFIX)
@Data
@Slf4j
@Validated
public class ObsProperties {
    public static final String PREFIX = "acooly.obs";

    public boolean enable;
    /**
     * 文件服务提供方式
     */
    @NotNull
    private Provider provider;

    /**
     * 仅当使用阿里云OSS配置
     */
    private Aliyun aliyun;

    private int timeout = 20000;

    @PostConstruct
    public void init() {
        if (this.provider == Provider.Aliyun) {
            Assert.notNull(this.aliyun);
            Assert.hasText(this.aliyun.getAccessKeyId());
            Assert.hasText(this.aliyun.getAccessKeySecret());
            Assert.hasText(this.aliyun.getEndpoint());
        }
    }

    public enum Provider implements Messageable {

        /**
         * 阿里云
         */
        Aliyun("aliyunObsClient", "阿里云");

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

    @Data
    public static class Aliyun {

        /**
         * 地域id
         * https://help.aliyun.com/document_detail/40654.html?spm=5176.10695662.1996646101.1.7e8f10132OTSUc
         */
        private String regionId;

        /**
         * 协议头,可选值 http:// 或者https://
         */
        private String protocol;
        /**
         * 接入keyId
         */
        private String accessKeyId;
        /**
         * 接入key秘钥
         */
        private String accessKeySecret;
        /**
         * 内网访问域名（主要用于ecs或者vpc环境内网做上传，加快上传速度）
         * 例如:oss-cn-hangzhou-internal.aliyuncs.com
         * <p>https://intl.aliyun.com/help/zh/doc-detail/31837.htm</p>
         */
        private String endpoint;

        /**
         * 外网访问域名（主要用于图片或者文件展示，及下载）
         * 例如:oss-cn-hangzhou.aliyuncs.com
         * <p>https://intl.aliyun.com/help/zh/doc-detail/31837.htm</p>
         */
        private String endpointExternal;

        /**
         * 默认桶名称
         */
        public String bucketName;


        //sts需要使用的配置
        /**
         * 通过RAM控制台创建一个RAM用户，拿到的accessKey，不能用主账号的accessKey
         */
        private String stsAccessKeyId;

        /**
         * 通过RAM控制台创建一个RAM用户，拿到的accessKeySecret，不能用主账号的accessKeySecret
         */
        private String stsAccessKeySecret;

        /**
         * 指定角色的ARN。格式：acs:ram::$accountID:role/$roleName
         */
        private String stsRoleArn;

        /**
         * 用户自定义参数。此参数用来区分不同的令牌，可用于用户级别的访问审计。格式：^[a-zA-Z0-9\.@\-_]+$。
         */
        private String stsRoleSessionName;

        /**
         * sts公共参数，默认版本传入2015-04-01
         */
        private String stsApiVersion = "2015-04-01";
    }
}
