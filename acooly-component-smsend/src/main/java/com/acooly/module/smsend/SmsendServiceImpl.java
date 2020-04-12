/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-04-12 23:20
 */
package com.acooly.module.smsend;

import com.acooly.module.smsend.sender.ShortMessageSender;
import com.acooly.module.smsend.service.SmsBlacklistService;
import com.acooly.module.smsend.service.SmsLogService;
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
    public void send(String mobileNo, String content) {

    }

    @Override
    public void send(List<String> mobileNos, String content) {

    }

    @Override
    public void send(String mobileNo, String content, SmsContext smsContext) {

    }

    @Override
    public void send(List<String> mobileNos, String content, SmsContext smsContext) {

    }

    @Override
    public void send(String mobileNo, String templateCode, Map<String, String> templateParams, SmsContext smsContext) {

    }

    @Override
    public void send(String mobileNo, String templateCode, Map<String, String> templateParams) {

    }

    @Override
    public void send(List<String> mobileNos, String templateCode, Map<String, String> templateParams, SmsContext smsContext) {

    }

    @Override
    public void send(List<String> mobileNos, String templateCode, Map<String, String> templateParams) {

    }
}
