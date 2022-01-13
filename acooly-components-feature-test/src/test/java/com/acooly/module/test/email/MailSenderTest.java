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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 邮件组件发送邮件单元测试
 *
 * @author zhangpu
 * @date 2022-01-11 10:20
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MailSenderTest {

    static {
        Apps.setProfileIfNotExists("dev");
    }

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private MailService mailService;

    static final String TEST_TEMPLATE = "DEPOSIT_SUCCESS";
    static final String TEST_TO = "15366632@qq.com";

    @Test
    public void testSendWithTemplate() {
        MailDto dto = new MailDto();
        // 注意修改为你自己的邮箱地址，别一个劲往我的邮箱里面灌！！！！
        dto.to(TEST_TO).subject("【Acooly】充值成功!").param("name", "张三").param("amount", "33600.00")
                .templateName(TEST_TEMPLATE);
        mailService.send(dto);
    }

    /**
     * 初始化
     * 增加邮件模板
     */
    @Before
    public void before() {
        EmailTemplate emailTemplate = emailTemplateService.findByName(TEST_TEMPLATE);
        if (emailTemplate != null) {
            return;
        }
        emailTemplate = new EmailTemplate();
        emailTemplate.setName(TEST_TEMPLATE);
        emailTemplate.setTitle("充值通知邮件");
        emailTemplate.setSubject("充值成功");
        emailTemplate.setContent("<table cellpadding=\"0\" cellspacing=\"0\" style=\"border: 1px solid #cdcdcd; width: 640px; margin:auto;font-size: 12px; color: #1E2731; line-height: 20px;\">\n" +
                "        <tbody><tr>\n" +
                "            <td colspan=\"3\" align=\"center\" style=\"background-color:#393D49; height: 55px; padding: 30px 0\"><a href=\"https://www.acooly.cn/\" target=\"_blank\"><img style=\"width: 300px;\" src=\"https://acooly.cn/assets/image/icon/logo-acooly-white.png\" alt=\"Acooly\"></a></td>\n" +
                "        </tr>\n" +
                "        <tr style=\"height: 30px;\"></tr>\n" +
                "        <tr>\n" +
                "            <td width=\"20\"></td>\n" +
                "            <td style=\"line-height: 40px\">\n" +
                "                您好：${name}<br>\n" +
                "\n" +
                "               您充值的 ${amount}元 已经到账，可以进行交易。<br>\n" +
                "            </td>\n" +
                "            <td width=\"20\"></td>\n" +
                "        </tr>\n" +
                "       <tr style=\"height: 20px;\"></tr>\n" +
                "        <tr>\n" +
                "            <td width=\"20\"></td>\n" +
                "            <td>\n" +
                "                <br>\n" +
                "                Acooly团队演示<br>\n" +
                "                <a href=\"https://www.acooly.cn/\">https://www.acooly.cn/</a><br>\n" +
                "            </td>\n" +
                "            <td width=\"20\"></td>\n" +
                "        </tr>\n" +
                "    <tr style=\"height: 50px;\"></tr>\n" +
                "</tbody></table>" +
                "" +
                "<p>出于对您帐号安全的考虑，我们强烈建议您使用“网易邮箱大师”。作为网易邮箱官方客户端，它采用专属协议传输邮件，能有效避免信息泄露等潜在风险。</p>" +
                "" +
                "<p>出于安全原因，该验证码将于5分钟后失效,请尽快输入。请勿将验证码透露给他人。</p>" +
                "<p>若非您本人操作，请立即修改密码或联系客服。</p>" +
                "<p>出于对您帐号安全的考虑，我们强烈建议您使用“网易邮箱大师”。作为网易邮箱官方客户端，它采用专属协议传输邮件，能有效避免信息泄露等潜在风险。</p>" +
                "" +
                "<p>出于安全原因，该验证码将于5分钟后失效,请尽快输入。请勿将验证码透露给他人。</p>" +
                "<p>若非您本人操作，请立即修改密码或联系客服。</p>" +
                "<p>出于对您帐号安全的考虑，我们强烈建议您使用“网易邮箱大师”。作为网易邮箱官方客户端，它采用专属协议传输邮件，能有效避免信息泄露等潜在风险。</p>" +
                "" +
                "<p>出于安全原因，该验证码将于5分钟后失效,请尽快输入。请勿将验证码透露给他人。</p>" +
                "<p>若非您本人操作，请立即修改密码或联系客服。</p>" +
                "");
        emailTemplateService.save(emailTemplate);
        log.info("邮件模板 初始化 完成: {}", emailTemplate);
    }

}
