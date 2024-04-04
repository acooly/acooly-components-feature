/*
 * acooly.cn Inc.
 * Copyright (c) 2024 All Rights Reserved.
 * create by acooly
 * date:2024-04-02
 */
package com.acooly.module.mail.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.mail.service.EmailSenderService;
import com.acooly.module.mail.dao.EmailSenderDao;
import com.acooly.module.mail.entity.EmailSender;

/**
 * email_sender Service实现
 *
 * @author acooly
 * @date 2024-04-02 09:47:58
 */
@Service("emailSenderService")
public class EmailSenderServiceImpl extends EntityServiceImpl<EmailSender, EmailSenderDao> implements EmailSenderService {

    @Override
    public EmailSender findBySysUserId(long sysUserId) {
        return getEntityDao().findBySysUserId(sysUserId);
    }
}
