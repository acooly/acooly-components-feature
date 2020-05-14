/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-13 12:47
 */
package com.acooly.module.smsend.facade.result;

import com.acooly.core.common.facade.ResultBase;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Size;

/**
 * @author zhangpu
 * @date 2020-05-13 12:47
 */
@Slf4j
public class SmsSendResult extends ResultBase {

    /**
     * 发送请求号
     */
    @Size(max = 64)
    private String requestId;

    /**
     * 应用编码
     * 用于识别是那个系统或应用请求发送
     */
    private String appId = "default";

}
