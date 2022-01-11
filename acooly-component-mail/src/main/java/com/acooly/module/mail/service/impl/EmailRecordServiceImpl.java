/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-01-11
 */
package com.acooly.module.mail.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Strings;
import com.acooly.module.mail.entity.EmailRecordContent;
import com.acooly.module.mail.service.EmailRecordContentService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.mail.service.EmailRecordService;
import com.acooly.module.mail.dao.EmailRecordDao;
import com.acooly.module.mail.entity.EmailRecord;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 邮件发送记录 Service实现
 *
 * @author zhangpu
 * @date 2022-01-11 09:34:49
 */
@Service("emailRecordService")
public class EmailRecordServiceImpl extends EntityServiceImpl<EmailRecord, EmailRecordDao> implements EmailRecordService {

    @Autowired
    private EmailRecordContentService emailRecordContentService;

    @Override
    public EmailRecord get(Serializable id) throws BusinessException {
        EmailRecord emailRecord = super.get(id);
        EmailRecordContent emailRecordContent = emailRecordContentService.get(id);
        if (emailRecord != null && emailRecordContent != null) {
            emailRecord.setContent(emailRecordContent.getContent());
        }
        return emailRecord;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void save(EmailRecord o) throws BusinessException {
        super.save(o);
        if (Strings.isNotBlank(o.getContent())) {
            emailRecordContentService.save(new EmailRecordContent(o.getId(), o.getContent()));
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saves(List<EmailRecord> emailRecords) throws BusinessException {
        super.saves(emailRecords);
        List<EmailRecordContent> contents = Lists.newArrayList();
        for (EmailRecord emailRecord : emailRecords) {
            if (Strings.isBlank(emailRecord.getContent())) {
                continue;
            }
            contents.add(new EmailRecordContent(emailRecord.getId(), emailRecord.getContent()));
        }
        if (Collections3.isNotEmpty(contents)) {
            emailRecordContentService.saves(contents);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void update(EmailRecord o) throws BusinessException {
        super.update(o);
        if (Strings.isNotBlank(o.getContent())) {
            emailRecordContentService.update(new EmailRecordContent(o.getId(), o.getContent()));
        }
    }

}
