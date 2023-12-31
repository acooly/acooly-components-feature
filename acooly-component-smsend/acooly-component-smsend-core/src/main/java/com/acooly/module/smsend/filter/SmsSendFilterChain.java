/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-14 12:07
 */
package com.acooly.module.smsend.filter;

import com.acooly.module.filterchain.AbstractFilterChainBase;
import com.acooly.module.filterchain.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * @author zhangpu
 * @date 2020-05-14 12:07
 */
@Slf4j
@Component
public class SmsSendFilterChain extends AbstractFilterChainBase<SmsSendContext> {

    @Override
    public void doFilter(SmsSendContext context) {
        if (context == null) {
            return;
        }
        Iterator<Filter<SmsSendContext>> iterator = filters.iterator();
        while (iterator.hasNext()) {
            Filter nextFilter = iterator.next();
            nextFilter.doFilter(context, this);
        }
    }
}
