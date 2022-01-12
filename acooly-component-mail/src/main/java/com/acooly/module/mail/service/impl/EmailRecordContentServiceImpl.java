/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-01-11
 */
package com.acooly.module.mail.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.mail.service.EmailRecordContentService;
import com.acooly.module.mail.dao.EmailRecordContentDao;
import com.acooly.module.mail.entity.EmailRecordContent;

/**
 * 邮件发送内容 Service实现
 *
 * @author zhangpu
 * @date 2022-01-11 09:34:49
 */
@Service("emailRecordContentService")
public class EmailRecordContentServiceImpl extends EntityServiceImpl<EmailRecordContent, EmailRecordContentDao> implements EmailRecordContentService {

}
