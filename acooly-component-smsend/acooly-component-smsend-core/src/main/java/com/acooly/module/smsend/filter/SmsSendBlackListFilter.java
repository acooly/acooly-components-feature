/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-14 12:10
 */
package com.acooly.module.smsend.filter;

import com.acooly.module.filterchain.FilterChain;
import com.acooly.module.smsend.common.enums.SmsSendResultCode;
import com.acooly.module.smsend.common.exception.ShortMessageSendException;
import com.acooly.module.smsend.manage.SmsBlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 短信发送 黑名单 Filter
 *
 * @author zhangpu
 * @date 2020-05-14 12:10
 */
@Slf4j
@Component
public class SmsSendBlackListFilter extends SmsSendAbstractFilter {

    @Autowired
    private SmsBlacklistService smsBlacklistService;

    @Override
    public void doFilter(SmsSendContext context, FilterChain<SmsSendContext> filterChain) {
        for (String mobileNo : context.getMobileNos()) {
            if (smsBlacklistService.inBlacklist(mobileNo)) {
                log.warn("短信发送黑名单 [命中] {}", mobileNo);
                throw new ShortMessageSendException(SmsSendResultCode.BLACK_LIST_HIT);
            }
        }
    }


    @Override
    public int getOrder() {
        return SmsSendFilters.BlackList.ordinal();
    }

    @Override
    public String getName() {
        return SmsSendFilters.BlackList.code();
    }

}
