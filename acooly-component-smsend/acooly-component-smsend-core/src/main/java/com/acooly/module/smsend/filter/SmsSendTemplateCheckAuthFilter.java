/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-14 12:10
 */
package com.acooly.module.smsend.filter;

import com.acooly.core.utils.Collections3;
import com.acooly.module.filterchain.FilterChain;
import com.acooly.module.smsend.common.enums.SmsSendResultCode;
import com.acooly.module.smsend.common.exception.ShortMessageSendException;
import com.acooly.module.smsend.entity.SmsTemplateProvider;
import com.acooly.module.smsend.manage.SmsTemplateProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 短信发送 参数检测 Filter
 *
 * @author zhangpu
 * @date 2020-05-14 12:10
 */
@Slf4j
@Component
public class SmsSendTemplateCheckAuthFilter extends SmsSendAbstractFilter {

    @Autowired
    private SmsTemplateProviderService smsTemplateProviderService;

    @Override
    public void doFilter(SmsSendContext context, FilterChain<SmsSendContext> filterChain) {
        List<SmsTemplateProvider> templateProviders = smsTemplateProviderService.findByTemplateCode(context.getTemplateCode());
        if (Collections3.isEmpty(templateProviders)) {
            log.warn("短信发送 模板转换为配置 templateCode:{}", context.getTemplateCode());
            throw new ShortMessageSendException(SmsSendResultCode.TEMPLATE_PROVIDER_NOT_FOUND);
        }
        context.setTemplateProviders(templateProviders.stream().
                collect(Collectors.toMap(SmsTemplateProvider::getProvider, SmsTemplateProvider::getProviderTemplateCode)));
    }


    @Override
    public int getOrder() {
        return SmsSendFilters.TemplateCheck.ordinal();
    }

    @Override
    public String getName() {
        return SmsSendFilters.TemplateCheck.code();
    }

}
