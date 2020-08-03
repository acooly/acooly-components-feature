/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-08-02 01:38
 */
package com.acooly.module.smsend.analysis.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangpu
 * @date 2020-08-02 01:38
 */
@Getter
@Setter
public class SmsSendPeriod {

    private String period;

    private String appId;

    private int count;

}
