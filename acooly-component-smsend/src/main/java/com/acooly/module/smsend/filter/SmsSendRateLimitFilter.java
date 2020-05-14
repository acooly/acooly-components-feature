/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-14 12:10
 */
package com.acooly.module.smsend.filter;

import com.acooly.module.cache.limit.RateChecker;
import com.acooly.module.filterchain.FilterChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangpu
 * @date 2020-05-14 12:10
 */
@Slf4j
@Component
public class SmsSendRateLimitFilter extends SmsSendAbstractFilter {

    public static final String RATE_LIMIT_KEY_PROFIX = "RateLimit-SMS-";

    @Autowired
    private RateChecker rateChecker;

    @Override
    public void doFilter(SmsSendContext context, FilterChain<SmsSendContext> filterChain) {
    }

    @Override
    public int getOrder() {
        return SmsSendFilters.RateLimit.ordinal();
    }

    @Override
    public String getName() {
        return SmsSendFilters.RateLimit.code();
    }


}
