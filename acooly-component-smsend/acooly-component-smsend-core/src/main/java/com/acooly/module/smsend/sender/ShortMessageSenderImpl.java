package com.acooly.module.smsend.sender;

import com.acooly.core.utils.Asserts;
import com.acooly.core.utils.Collections3;
import com.acooly.module.smsend.common.enums.SmsProvider;
import com.acooly.module.smsend.common.enums.SmsSendResultCode;
import com.acooly.module.smsend.common.exception.ShortMessageSendException;
import com.acooly.module.smsend.sender.dto.SmsResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service("shortMessageSender")
public class ShortMessageSenderImpl implements ShortMessageSender {

    @Autowired
    private ShortMessageSenderManager shortMessageSenderManager;

    @Override
    public SmsResult send(String mobileNo, String content, String contentSign) {
        return null;
    }

    @Override
    public SmsResult send(Set<String> mobileNos, String content, String contentSign) {
        return null;
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
        if (mobileNos.size() == 1) {
            return sender.sendTemplate(Collections3.getFirst(mobileNos), templateCode, templateParams, contentSign);
        } else {
            return sender.sendTemplate(mobileNos, templateCode, templateParams, contentSign);
        }
    }


    @Override
    public SmsProvider getProvider() {
        return null;
    }
}
