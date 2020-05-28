/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-24 21:59 创建
 */
package com.acooly.module.sms;

import com.acooly.core.utils.enums.Messageable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

import static com.acooly.module.sms.SmsProperties.PREFIX;

/**
 * @author qiubo@yiji.com
 */
@ConfigurationProperties(prefix = PREFIX)
@Data
@Slf4j
@Validated
public class SmsProperties {
    public static final String PREFIX = "acooly.sms";

    public boolean enable;
    /**
     * 短信服务提供商
     */
    @NotNull
    private Provider provider;

    private String url;
    private String username;

    private String password;
    private String batchUser;
    private String batchPswd;
    private String prefix;
    private String posfix;
    /**
     * 仅当使用亿美通道时配置
     */
    private Emay emay;

    /**
     * 仅当使用创蓝253短信平台时配置
     */
    private CL253 cl253;
    /**
     * 仅当使用创蓝253 2.0接口时配置
     */
    private CL253Plus cl253plus;

    /**
     * 仅当使用阿里云短信平台时配置
     */
    private Aliyun aliyun;

    /**
     * 仅当使用容联.云通讯短信平台时配置
     */
    private Cloopen cloopen;

    private int timeout = 20000;
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
    /**
     * 短信模板定义
     */
    private Map<String, String> template = Maps.newHashMap();

    @PostConstruct
    public void init() {
        if (this.provider == Provider.EMAY) {
            Assert.notNull(this.emay);
            Assert.hasText(this.emay.getSn());
            Assert.hasText(this.emay.getPassword());
            if (emay.getSign() == null) {
                emay.sign = "";
            } else {
                if (!emay.getSign().startsWith("【")) {
                    emay.sign = "【" + emay.sign.trim() + "】";
                }
            }
        }
        if (this.provider == Provider.CL253) {
            Assert.notNull(this.cl253);
            Assert.hasText(this.cl253.getUn());
            Assert.hasText(this.cl253.getPw());
            Assert.hasText(this.cl253.getSign());
            if (!cl253.getSign().startsWith("【")) {
                cl253.sign = "【" + cl253.sign.trim() + "】";
            }
        }

        if (this.provider == Provider.CL253Plus) {
            Assert.notNull(this.cl253plus);
            Assert.hasText(this.cl253plus.getAccount());
            Assert.hasText(this.cl253plus.getPswd());
        }

        if (this.provider == Provider.CL253Json) {
            Assert.notNull(this.getUrl(), "请求地址不能为空");
            Assert.hasText(this.getUsername(), "账户不能为空");
            Assert.hasText(this.getPassword(), "密码不能为空");
        }

        if (this.provider == Provider.Aliyun) {
            Assert.notNull(this.aliyun);
            Assert.hasText(this.aliyun.getAccessKeyId());
            Assert.hasText(this.aliyun.getAccessKeySecret());
            Assert.hasText(this.aliyun.getAccountId());
            Assert.hasText(this.aliyun.getTopicName());
        }
        if (this.provider == Provider.Cloopen) {
            Assert.notNull(this.cloopen);
            Assert.hasText(this.cloopen.getAppId());
            Assert.hasText(this.cloopen.getAccountId());
        }
    }

    public enum Provider implements Messageable {
        /**
         * 亿美
         */
        EMAY("emayShortMessageSender", "亿美"),
        /**
         * 漫道
         */
        MAIDAO("maidaoShortMessageSender", "漫道"),
        /**
         * 重庆客亲通
         */
        KLUM("chinaklumShortMessageSender", "重庆客亲通"),
        /**
         * 创蓝253
         */
        CL253("cl253ShortMessageSender", "创蓝253"),
        /**
         * 创蓝253 2.0接口
         */
        CL253Plus("cl253PlusShortMessageSender", "创蓝253 2.0接口"),
        /**
         * 创蓝253 Json接口
         */
        CL253Json("cl253JsonShortMessageSender", "创蓝253 Json接口"),
        /**
         * 阿里云
         */
        Aliyun("aliyunMessageSender", "阿里云"),
        /**
         * 容联.云通讯
         */
        Cloopen("cloopenMessageSender", "容联.云通讯"),

        /**
         * 云信留客 2分一条 短信内容可以自定义  2018-08-30
         */
        Winnerlook("winnerlookMessageSender", "云信留客"),
        /**
         * 测试
         */
        MOCK("mockShortMessageSender", "测试");
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
    public static class Emay {
        /**
         * 序列号,请通过亿美销售人员获取
         */
        private String sn;
        /**
         * 密码,请通过亿美销售人员获取
         */
        private String password;
        /**
         * 短信签名
         */
        private String sign;
    }

    @Data
    public static class CL253 {
        /**
         * 用户账号
         */
        private String un;
        /**
         * 用户密码
         */
        private String pw;

        /**
         * 短信签名,正式账号支持
         */
        private String sign;
    }

    @Data
    public static class CL253Plus {
        /**
         * 用户账号
         */
        private String account;
        /**
         * 用户密码
         */
        private String pswd;
    }

    /**
     * 阿里云短信通道 阿里云通道只支持模板和签名为短信内容 @See com.acooly.core.test.web.TestController#testAliyunSms()
     */
    @Data
    public static class Aliyun {
        /**
         * 主账号id
         */
        private String accountId;
        /**
         * 接入keyId
         */
        private String accessKeyId;
        /**
         * 接入key秘钥
         */
        private String accessKeySecret;
        /**
         * 主题名称，如：sms.topic-cn-hangzhou
         */
        private String topicName;

        /**
         * 登录验证码模板id，仅当开启登录短信的时候配置
         */
        private String loginCodeTemplate;

        /**
         * 默认短信签名
         */
        private String contentSign;
    }

    /**
     * 容联.云通讯
     */
    @Data
    public static class Cloopen {
        /**
         * 主账号id
         */
        private String accountId;

        private String accountToken;
        private String appId;

        /**
         * 登录验证码模板id，仅当开启登录短信的时候配置
         */
        private String loginVerifCodeTemplateId = "183256";
    }
}
