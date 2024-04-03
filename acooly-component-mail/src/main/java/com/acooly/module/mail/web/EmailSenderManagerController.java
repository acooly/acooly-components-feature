/*
 * acooly.cn Inc.
 * Copyright (c) 2024 All Rights Reserved.
 * create by acooly
 * date:2024-04-02
 */
package com.acooly.module.mail.web;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.enums.WhetherStatus;
import com.acooly.module.mail.MailDto;
import com.acooly.module.mail.entity.EmailSender;
import com.acooly.module.mail.service.EmailSenderService;
import com.acooly.module.mail.service.MailService;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * email_sender 管理控制器
 *
 * @author acooly
 * @date 2024-04-02 09:47:58
 */
@Controller
@RequestMapping(value = "/manage/module/mail/emailSender")
public class EmailSenderManagerController extends AbstractJsonEntityController<EmailSender, EmailSenderService> {


    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Override
    protected EmailSender onSave(HttpServletRequest request, HttpServletResponse response, Model model, EmailSender entity, boolean isCreate) throws Exception {
        String sysUserIdStr = request.getParameter("sysUserId");
        User user = userService.get(Long.parseLong(sysUserIdStr));
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (isCreate) {
            EmailSender emailSender = emailSenderService.findBySysUserId(user.getId());
            if (emailSender != null) {
                throw new RuntimeException("已存在邮箱配置,无需重复配置");
            }
        }
        entity.setSysUserId(user.getId());
        entity.setSysUserName(user.getUsername());
        entity.setSysRealName(user.getRealName());
        return entity;
    }


    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allStartTlss", WhetherStatus.mapping());
        model.put("allSslConnects", WhetherStatus.mapping());
    }

}
