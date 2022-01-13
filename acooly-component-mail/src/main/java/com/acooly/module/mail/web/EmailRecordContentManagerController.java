/*
* acooly.cn Inc.
* Copyright (c) 2022 All Rights Reserved.
* create by zhangpu
* date:2022-01-11
*/
package com.acooly.module.mail.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.module.mail.entity.EmailRecordContent;
import com.acooly.module.mail.service.EmailRecordContentService;

import com.google.common.collect.Maps;

/**
 * 邮件发送内容 管理控制器
 *
 * @author zhangpu
 * @date 2022-01-11 09:34:49
 */
@Controller
@RequestMapping(value = "/manage/module/mail/emailRecordContent")
public class EmailRecordContentManagerController extends AbstractJsonEntityController<EmailRecordContent, EmailRecordContentService> {


	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private EmailRecordContentService emailRecordContentService;




}
