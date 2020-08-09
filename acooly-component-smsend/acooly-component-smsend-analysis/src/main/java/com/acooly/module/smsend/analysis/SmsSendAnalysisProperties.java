/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-24 21:59 创建
 */
package com.acooly.module.smsend.analysis;

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
@ConfigurationProperties(prefix = SmsSendAnalysisProperties.PREFIX)
public class SmsSendAnalysisProperties {
    public static final String PREFIX = "acooly.smsend.analysis";

    public boolean enable = true;

    /**
     * 启动定时仍无处理日终汇总
     */
    public boolean summaryEnable = true;
    /**
     * 汇总执行的cron,默认00:05分(0 5 0 * * ?)
     */
    private String summaryCron = "0 5 0 * * ?";

}
