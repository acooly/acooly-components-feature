package com.acooly.module.smsend.sender;

import com.acooly.core.utils.Asserts;
import com.acooly.core.utils.Collections3;
import com.acooly.module.smsend.common.enums.SmsProvider;
import com.acooly.module.smsend.common.enums.SmsSendResultCode;
import com.acooly.module.smsend.common.exception.ShortMessageSendException;
import com.acooly.module.smsend.entity.SmsTemplateProvider;
import com.acooly.module.smsend.manage.SmsTemplateProviderService;
import com.acooly.module.smsend.sender.dto.SmsResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service("shortMessageSender")
public class ShortMessageSenderImpl implements ShortMessageSender {

    @Autowired
    private ShortMessageSenderManager shortMessageSenderManager;
    @Resource
    private SmsTemplateProviderService smsTemplateProviderService;

    @Override
    public SmsResult send(String mobileNo, String content, String contentSign) {
        return new SmsResult(SmsSendResultCode.NOT_SUPPORT_OPERATE, getProvider());
    }

    @Override
    public SmsResult send(Set<String> mobileNos, String content, String contentSign) {
        return new SmsResult(SmsSendResultCode.NOT_SUPPORT_OPERATE, getProvider());
    }


    @Override
    public SmsResult sendTemplate(String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign) {
        Set<String> mobileNos = Sets.newLinkedHashSet();
        mobileNos.add(mobileNo);
        return doSendTemplate(mobileNos, templateCode, templateParams, contentSign);
    }


    @Override
    public SmsResult sendTemplate(Set<String> mobileNos, String templateCode, Map<String, String> templateParams, String contentSign) {
        return doSendTemplate(mobileNos, templateCode, templateParams, contentSign);
    }

    protected SmsResult doSendTemplate(Set<String> mobileNos, String templateCode, Map<String, String> templateParams, String contentSign) {
        Iterator<ShortMessageSender> iterator = Lists.newLinkedList(shortMessageSenderManager.getAllSender()).iterator();
        int maxRetry = shortMessageSenderManager.getAllSender().size() - 1;
        SmsResult result = null;
        int retry = 0;
        while (retry <= maxRetry) {
            result = doSendTemplate(iterator, mobileNos, templateCode, templateParams, contentSign);
            if (result.isSuccess()) {
                break;
            }
            retry = retry + 1;
            log.debug("短信发送 [失败] result:{}, 第{}次重试, result:", result, retry);
        }
        if (result != null && !result.isSuccess() && retry == maxRetry) {
            log.warn("短信发送 [失败] result:{}, 已重试:{}次。", result, retry);
        }
        return result;
    }

    protected SmsResult doSendTemplate(Iterator<ShortMessageSender> iterator,
                                       Set<String> mobileNos, String templateCode, Map<String, String> templateParams, String contentSign) {
        Asserts.notEmpty(mobileNos, "手机号码");
        ShortMessageSender sender = iterator.next();
        if (sender == null) {
            log.warn("短信发送 [失败] {} mobileNo:{},template:{}", SmsSendResultCode.NO_PROVIDER_AVAILABLE, mobileNos, templateCode);
            throw new ShortMessageSendException(SmsSendResultCode.NO_PROVIDER_AVAILABLE);
        }
        SmsResult result = null;
        String providerTemplate = getProviderTemplateCode(templateCode, sender.getProvider());
        if (mobileNos.size() == 1) {
            result = sender.sendTemplate(Collections3.getFirst(mobileNos), providerTemplate, templateParams, contentSign);
        } else {
            result = sender.sendTemplate(mobileNos, providerTemplate, templateParams, contentSign);
        }
        result.setTemplateProvider(providerTemplate);
        return result;
    }

    protected String getProviderTemplateCode(String templateCode, SmsProvider provider) {
        String providerTemplateCode = templateCode;
        SmsTemplateProvider smsTemplateProvider = smsTemplateProviderService.findUnique(templateCode, provider);
        if (smsTemplateProvider != null) {
            providerTemplateCode = smsTemplateProvider.getProviderTemplateCode();
        } else {
            log.warn("短信发送 [{}] 未配置模板编码({})对应的渠道模板编码，直接采用模板编码发送。", provider, templateCode);
        }
        return providerTemplateCode;
    }

    @Override
    public SmsProvider getProvider() {
        return null;
    }
}
