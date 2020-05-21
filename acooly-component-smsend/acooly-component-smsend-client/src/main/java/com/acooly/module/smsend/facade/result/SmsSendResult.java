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
import com.acooly.core.utils.enums.Messageable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangpu
 * @date 2020-05-13 12:47
 */
@Getter
@Setter
public class SmsSendResult extends ResultBase {


    /**
     * 应用编码
     * 用于识别是那个系统或应用请求发送
     */
    private String appId;

    public SmsSendResult() {
    }

    public SmsSendResult(Messageable messageable) {
        setStatus(messageable);
    }
}
