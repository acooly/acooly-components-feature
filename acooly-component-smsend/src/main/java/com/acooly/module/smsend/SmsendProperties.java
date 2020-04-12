/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-24 21:59 创建
 */
package com.acooly.module.smsend;

import com.acooly.module.smsend.enums.SmsProvider;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;


/**
 * 短信发送组件 配置参数
 *
 * @author zhangpu@acooly.cn
 * @date 2020-04-12
 */
@Slf4j
@Data
@Validated
@ConfigurationProperties(prefix = SmsendProperties.PREFIX)
public class SmsendProperties {
    public static final String PREFIX = "acooly.smsend";

    public boolean enable;

    /**
     * 服务商列表
     * 配置方式：acooly.smsend.providers.maidao.
     */
    private Map<SmsProvider, SmsProviderInfo> providers = Maps.newLinkedHashMap();

    private int connTimeout = 20 * 1000;
    private int readTimeout = 10 * 1000;
    /**
     * IP最大频率(分钟)
     */
    private int ipFreq = 200;

    /**
     * 单号码发送间隔（秒）
     */
    private int sendInterval = 10;

    /**
     * 黑名单
     */
    private List<String> blacklist = Lists.newArrayList();


    @PostConstruct
    public void init() {

    }

    /**
     * 短信服务商
     */
    @Data
    public static class SmsProviderInfo {
        /**
         * 服务商名称
         */
        private SmsProvider provider;
        /**
         * 身份标志（例如：服务商分配的商户号，AppId等）
         */
        private String appId;

        /**
         * 访问标志（例如：访问码/账号/用户名等，可能存在于appId一致的服务商）
         */
        private String accessKey;

        /**
         * 认证标志（例如：密码/安全码/秘钥等）
         */
        private String secretKey;

        /**
         * 内容签名
         */
        private String contentSign;

        /**
         * 每天没号码最大发送数量（渠道限制参数）
         */
        private int maxCountOfDay = 10;


        /**
         * 扩展参数（每个渠道特别的扩展参数）
         */
        private Map<String, Object> ext = Maps.newHashMap();
    }

}
