package com.acooly.module.smsend.service;

import com.acooly.module.smsend.common.dto.SenderInfo;

/**
 * 短信发送服务
 *
 * @author zhangpu
 */
public interface SmsSendService {

    void send(SenderInfo senderInfo);
}
