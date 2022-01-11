/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-01-11 10:20
 */
package com.acooly.module.test.email;

import com.acooly.core.common.boot.Apps;
import com.acooly.module.mail.MailDto;
import com.acooly.module.mail.entity.EmailTemplate;
import com.acooly.module.mail.service.EmailTemplateService;
import com.acooly.module.mail.service.MailService;
import com.acooly.module.test.NoWebTestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * 邮件组件发送邮件单元测试
 *
 * @author zhangpu
 * @date 2022-01-11 10:20
 */
@Slf4j
public class MailSenderTest extends NoWebTestBase {

    static {
        Apps.setProfileIfNotExists("dev");
    }

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private MailService mailService;

    static final String TEST_TEMPLATE = "Registry_User";


    @Test
    public void testSendWithTemplate() {
        MailDto dto = new MailDto();
        dto.to("qiuboboy@qq.com").subject("恭喜您注册成功").param("name", "x").param("message", "how are you!")
                .templateName(TEST_TEMPLATE);
        mailService.send(dto);
    }


    /**
     * 初始化
     * 增加邮件模板
     */
    @Before
    public void before() {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setName(TEST_TEMPLATE);
        emailTemplate.setTitle("用户注册成功通知");
        emailTemplate.setSubject("恭喜您注册成功");
        emailTemplate.setContent("<html>\n" +
                "              <head>\n" +
                "                    <title>恭喜您注册成功</title>\n" +
                "                </head>\n" +
                "                <body>\n" +
                "                    <h1>${message},${name}</h1>\n" +
                "                </body>\n" +
                "            </html>");
        emailTemplateService.save(emailTemplate);
        log.info("邮件模板 初始化 完成: {}", emailTemplate);
    }

    @After
    public void after() {
        emailTemplateService.deleteByName(TEST_TEMPLATE);
        log.info("邮件模板 清理 完成: {}", TEST_TEMPLATE);
    }


}
