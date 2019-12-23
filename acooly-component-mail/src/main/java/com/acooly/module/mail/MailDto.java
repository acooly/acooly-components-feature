/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-25 11:21 创建
 */
package com.acooly.module.mail;

import com.google.common.collect.Maps;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author qiubo@yiji.com
 */
@Data
public class MailDto implements Serializable {
    @NotNull
    @Size(min = 1, max = 20)
    private List<String> to;

    @NotNull
    private String subject;

    private Map<String, Object> params = Maps.newHashMap();

    @NotBlank
    private String templateName;

    /**
     * 邮件附件
     */
    private List<MailAttachmentDto> attachments;

    public MailDto to(String to) {
        if (this.to == null) {
            this.to = new ArrayList<>();
        }
        this.to.add(to);
        return this;
    }

    public MailDto subject(String subject) {
        this.subject = subject;
        return this;
    }

    public MailDto templateName(String templateName) {
        this.templateName = templateName;
        return this;
    }

    public MailDto param(String key, Object value) {
        this.params.put(key, value);
        return this;
    }

    public MailDto addAttachment(MailAttachmentDto attachment) {
        if (this.attachments == null) {
            this.attachments = new ArrayList<>();
        }
        this.attachments.add(attachment);
        return this;
    }
}
