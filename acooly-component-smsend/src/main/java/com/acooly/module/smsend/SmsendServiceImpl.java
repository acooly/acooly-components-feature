/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-04-12 23:20
 */
package com.acooly.module.smsend;

import com.acooly.core.utils.Ids;
import com.acooly.module.smsend.domain.SmsLog;
import com.acooly.module.smsend.enums.SmsStatus;
import com.acooly.module.smsend.sender.ShortMessageSender;
import com.acooly.module.smsend.service.SmsBlacklistService;
import com.acooly.module.smsend.service.SmsLogService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 短信发送服务
 *
 * @author zhangpu
 * @date 2020-04-12 23:20
 */
@Slf4j
@Component
public class SmsendServiceImpl implements SmsendService {

    @Resource
    private ShortMessageSender shortMessageSender;
    @Resource
    private SmsLogService smsLogService;
    @Resource
    private SmsBlacklistService smsBlacklistService;

    @Resource(name = "commonTaskExecutor")
    private TaskExecutor taskExecutor;


    @Override
    public void sendTemplate(String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign, SmsendContext smsendContext) {


    }

    @Override
    public void sendTemplate(List<String> mobileNos, String templateCode, Map<String, String> templateParams, String contentSign, SmsendContext smsendContext) {

    }


    @Override
    public void send(String mobileNo, String content, String contentSign, SmsendContext smsContext) {

    }

    @Override
    public void send(List<String> mobileNos, String content, String contentSign, SmsendContext smsContext) {

    }


    protected void doSendTemplate() {

    }

    protected void saveLog(String mobileNo, String content, SmsStatus smsStatus, String result, String resultMessage, SmsendContext context) {
        SmsLog log = new SmsLog(mobileNo, content);
        log.setProvider(shortMessageSender.getProvider().code());
        log.setProviderStatus(result);
        log.setProviderMemo(resultMessage);
        log.setStatus(smsStatus.getCode());
        if (context != null) {
            log.setClientIp(context.getClientIp());
            log.setComments(context.getComments());
        }
        smsLogService.save(log);
    }

    protected void saveLogs(List<String> mobileNos, String content, SmsStatus smsStatus, String result, String resultMessage) {
        SmsLog log = null;
        List<SmsLog> logs = Lists.newArrayList();
        String batchNo = Ids.getDid();
        for (String mobileNo : mobileNos) {
            log = new SmsLog(mobileNo, content);
            log.setBatchNo(batchNo);
            log.setProvider(shortMessageSender.getProvider().code());
            log.setProviderStatus(result);
            log.setProviderMemo(resultMessage);
            log.setStatus(smsStatus.getCode());
            logs.add(log);
        }
        smsLogService.saves(logs);
    }
}
