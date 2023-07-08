/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2023-07-06 14:47
 */
package com.acooly.module.smsend.sender.support.huaweiyun;

import com.acooly.core.common.facade.InfoBase;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangpu
 * @date 2023-07-06 14:47
 */
@Getter
@Setter
public class SmsId extends InfoBase {
    private String smsMsgId;
    private String from;
    private String originTo;
    private String status;
    private String createTime;
}
