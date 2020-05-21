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
import com.acooly.module.smsend.entity.SmsApp;
import com.acooly.module.smsend.manage.SmsAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 短信发送 发送身份认证 Filter
 *
 * @author zhangpu
 * @date 2020-05-14 12:10
 */
@Slf4j
@Component
public class SmsSendAppAuthFilter extends SmsSendAbstractFilter {

    @Autowired
    private SmsAppService smsAppService;

    @Override
    public void doFilter(SmsSendContext context, FilterChain<SmsSendContext> filterChain) {

        SmsApp smsApp = smsAppService.findByAppId(context.getAppId());
        if (smsApp == null) {
            log.warn("短信发送 AppId认证 [未通过] AppId为注册:{}", context.getAppId());
            throw new ShortMessageSendException(SmsSendResultCode.APP_AUTH_ERROR);
        }
    }


    @Override
    public int getOrder() {
        return SmsSendFilters.AppAuth.ordinal();
    }

    @Override
    public String getName() {
        return SmsSendFilters.AppAuth.code();
    }

}
