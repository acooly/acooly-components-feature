/*
 * acooly.cn Inc.
 * Copyright (c) 2024 All Rights Reserved.
 * create by acooly
 * date:2024-04-02
 *
 */
package com.acooly.module.mail.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.mail.entity.EmailSender;
/**
 * email_sender Service接口
 *
 * @author acooly
 * @date 2024-04-02 09:47:58
 */
public interface EmailSenderService extends EntityService<EmailSender> {

    EmailSender findBySysUserId(long sysUserId);
}
