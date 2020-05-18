/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-14 16:13
 */
package com.acooly.module.smsend.filter;

import com.acooly.core.utils.enums.Messageable;
import com.acooly.module.filterchain.Filter;
import com.acooly.module.smsend.SmsSendProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangpu
 * @date 2020-05-14 16:13
 */
@Slf4j
public abstract class SmsSendAbstractFilter implements Filter<SmsSendContext> {

    @Autowired
    protected SmsSendProperties smsSendProperties;

}
