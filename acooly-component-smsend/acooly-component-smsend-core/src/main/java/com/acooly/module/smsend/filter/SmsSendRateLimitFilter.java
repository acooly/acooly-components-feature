/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-14 12:10
 */
package com.acooly.module.smsend.filter;

import com.acooly.core.utils.Strings;
import com.acooly.module.cache.limit.RateChecker;
import com.acooly.module.filterchain.FilterChain;
import com.acooly.module.smsend.common.enums.SmsSendResultCode;
import com.acooly.module.smsend.common.exception.ShortMessageSendException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 短信发送流控Filter
 *
 * @author zhangpu
 * @date 2020-05-14 12:10
 */
@Slf4j
@Component
public class SmsSendRateLimitFilter extends SmsSendAbstractFilter {

    public static final String RATE_LIMIT_KEY_PROFIX = "SmsSend-RateLimit";

    @Autowired
    private RateChecker rateChecker;

    @Override
    public void doFilter(SmsSendContext context, FilterChain<SmsSendContext> filterChain) {
        TimeUnit timeUnit = smsSendProperties.getRateLimit().getTimeUnit();
        Long interval = timeUnit.toMillis(1);

        for (String mobileNo : context.getMobileNos()) {
            String mobileNoKey = getMobileKey(mobileNo);
            Long maxPerMobile = smsSendProperties.getRateLimit().getMaxPerMobile();
            if (!rateChecker.check(mobileNoKey, interval.intValue(), maxPerMobile)) {
                log.warn("短信发送流控 超过单手机号码流控。{}/{}", maxPerMobile, timeUnit.name());
                throw new ShortMessageSendException(SmsSendResultCode.RATE_LIMIT_MOBILE);
            }
        }

        if (Strings.isNoneBlank(context.getClientIp())) {
            String ipKey = getIpKey(context.getClientIp());
            Long maxPerIp = smsSendProperties.getRateLimit().getMaxPerIp();
            if (!rateChecker.check(ipKey, interval.intValue(), maxPerIp)) {
                log.warn("短信发送流控 超过单IP流控。{}/{}", maxPerIp, timeUnit.name());
                throw new ShortMessageSendException(SmsSendResultCode.RATE_LIMIT_IP);
            }
        }
    }

    protected String getMobileKey(String mobileNo) {
        return RATE_LIMIT_KEY_PROFIX + "-MobileNo:" + mobileNo;
    }

    protected String getIpKey(String ip) {
        return RATE_LIMIT_KEY_PROFIX + "-IP:" + ip;
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
