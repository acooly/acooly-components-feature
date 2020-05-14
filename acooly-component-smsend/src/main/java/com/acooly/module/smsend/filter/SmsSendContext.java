/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-14 12:05
 */
package com.acooly.module.smsend.filter;

import com.acooly.module.filterchain.Context;
import com.acooly.module.smsend.facade.order.SmsSendOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2020-05-14 12:05
 */
@Slf4j
@Getter
@Setter
public class SmsSendContext extends Context {

    private SmsSendOrder smsSendOrder;

}
