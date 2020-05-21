/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-24 21:59 创建
 */
package com.acooly.module.smsend.facade;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


/**
 * 短信发送组件 配置参数
 *
 * @author zhangpu@acooly.cn
 * @date 2020-04-12
 */
@Slf4j
@Data
@Validated
@ConfigurationProperties(prefix = SmsSendClientProperties.PREFIX)
public class SmsSendClientProperties {
    public static final String PREFIX = "acooly.smsend.client";

    public boolean enable = true;

    /**
     * 接口模式
     */
    private Type type = Type.dubbo;

    public static enum Type {
        mock, dubbo
    }

}
