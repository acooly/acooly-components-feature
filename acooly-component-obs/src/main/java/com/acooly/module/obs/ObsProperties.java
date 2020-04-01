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
         * 接入keyId
         */
        private String accessKeyId;
        /**
         * 接入key秘钥
         */
        private String accessKeySecret;
        /**
         * 华东一：http://oss-cn-hangzhou.aliyuncs.com
         *
         * <p>https://intl.aliyun.com/help/zh/doc-detail/31837.htm
         */
        private String endpoint;
    }
}
