/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-25 11:26 创建
 */
package com.acooly.module.mail.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.exception.CommonErrorCodes;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.validate.Validators;
import com.acooly.module.mail.MailAttachmentDto;
import com.acooly.module.mail.MailDto;
import com.acooly.module.mail.MailProperties;
import com.acooly.module.mail.entity.EmailRecord;
import com.acooly.module.mail.service.EmailRecordService;
import com.acooly.module.mail.service.MailService;
import com.acooly.module.ofile.OFileProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author qiubo@yiji.com
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired
    private MailTemplateService mailTemplateService;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    @Qualifier("commonTaskExecutor")
    private TaskExecutor taskExecutor;
    @Autowired
    private OFileProperties ofileProperties;
    @Autowired
    private EmailRecordService emailRecordService;

    @Override
    public void send(MailDto dto) {
        String content = validateAndParse(dto);
        if (!mailProperties.isMock()) {
            send0(dto, content);
        } else {
            log.info("发送邮件 MOCK 成功: {}", dto);
        }
        saveRecord(dto, content);
    }

    @Override
    public void sendAsync(MailDto dto) {
        String content = validateAndParse(dto);
        taskExecutor.execute(() -> {
            try {
                if (!mailProperties.isMock()) {
                    send0(dto, content);
                } else {
                    log.info("发送邮件 MOCK 成功: {}", dto);
                }
                saveRecord(dto, content);
            } catch (BusinessException e) {
                //ignore
            }
        });
    }

    public String validateAndParse(MailDto dto) {
        Validators.assertJSR303(dto);
        dto.getTo().forEach(EmailValidator.getInstance()::isValid);
        return mailTemplateService.parse(dto.getTemplateName(), dto);
    }


    protected void saveRecord(MailDto dto, String content) {
        EmailRecord emailRecord = new EmailRecord();
        emailRecord.setTemplateName(dto.getTemplateName());
        emailRecord.setTemplateTitle(dto.getTemplateTile());
        emailRecord.setSubject(dto.getSubject());
        emailRecord.setContent(content);
        emailRecord.setFromAddress(mailProperties.getFromAddress());
        emailRecord.setFromName(mailProperties.getFromName());
        emailRecord.setToAddressList(Strings.join(dto.getTo(), ","));
        emailRecordService.save(emailRecord);
    }


    private void send0(MailDto dto, String content) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setDebug(mailProperties.isDebug());
            email.setHostName(mailProperties.getHostname());
            email.setAuthentication(mailProperties.getUsername(), mailProperties.getPassword());

            email.setFrom(mailProperties.getFromAddress(), mailProperties.getFromName());
            email.setSSLOnConnect(true);
            for (String toMail : dto.getTo()) {
                email.addTo(toMail);
            }
            email.setSubject(dto.getSubject());
            email.setHtmlMsg(content);
            email.setCharset(mailProperties.getCharset());
            //处理附件
            List<MailAttachmentDto> attachments = dto.getAttachments();
            if (attachments != null && attachments.size() > 0) {
                for (MailAttachmentDto attachmentDto : attachments) {

                    EmailAttachment attachment = new EmailAttachment();
                    String fileName = attachmentDto.getName();
                    String filepath = getFilePath();
                    String storageFilePath = ofileProperties.getStorageRoot() + filepath;
                    String currentName = getCurrentName(fileName);

                    saveFile(attachmentDto, storageFilePath, currentName);

                    log.info("附件存储路径：{}", storageFilePath + currentName);
                    attachment.setPath(storageFilePath + currentName);
                    attachment.setDisposition(EmailAttachment.ATTACHMENT);
                    attachment.setDescription(attachmentDto.getDescription());
                    attachment.setName(attachmentDto.getName());

                    email.attach(attachment);
                }
            }

            email.send();
            log.info("发送邮件成功,{}", dto);
        } catch (Exception e) {
            log.error("发送邮件失败, {}", dto, e);
            throw new BusinessException("EMAIL_SEND_FAIL", "邮件发送失败", e.getMessage());
        }
    }


    private void saveFile(MailAttachmentDto mailAttachmentDto, String filePath, String fileName) {
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            is = new ByteArrayInputStream(mailAttachmentDto.getContent());
            fos = new FileOutputStream(new File(filePath + fileName));
            IOUtils.copy(is, fos);
        } catch (IOException e) {
            throw new BusinessException("附件传输失败", e);
        } finally {
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(is);
        }
    }

    private String getCurrentName(String fileName) {
        return System.currentTimeMillis() + "_" + fileName;
    }

    private String getFilePath() {
        Calendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        String path = year + File.separator + month + File.separator + date + File.separator;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return path;
    }
}
