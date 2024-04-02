/*
 * acooly.cn Inc.
 * Copyright (c) 2024 All Rights Reserved.
 * create by acooly
 * date:2024-04-02
 */
package com.acooly.module.mail.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.utils.enums.WhetherStatus;
import com.acooly.core.utils.ie.anno.ExportColumn;
import com.acooly.core.utils.ie.anno.ExportModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * email_sender Entity
 *
 * @author acooly
 * @date 2024-04-02 09:47:58
 */
@Entity
@Table(name = "email_sender")
@Getter
@Setter
@ExportModel(name = "email_sender", border = true, headerShow = true)
public class EmailSender extends AbstractEntity {

    /**
     * boss用户id
     */
    @ExportColumn(header = "boss用户id", order = 1)
    private Long sysUserId;

    /**
     * boss登录名
     */
    @Size(max = 64)
    @ExportColumn(header = "boss登录名", order = 2)
    private String sysUserName;

    /**
     * boss真实姓名
     */
    @Size(max = 64)
    @ExportColumn(header = "boss真实姓名", order = 3)
    private String sysRealName;

    /**
     * SMTP服务器地址
     */
    @Size(max = 128)
    @ExportColumn(header = "SMTP服务器地址", order = 4)
    private String hostName;

    /**
     * SMTP服务器端口
     */
    @ExportColumn(header = "SMTP服务器端口", order = 5)
    private Integer hostPort=25;

    /**
     * 邮箱名称
     */
    @Size(max = 255)
    @ExportColumn(header = "邮箱名称", order = 6)
    private String emailName;

    /**
     * 邮箱密码
     */
    @Size(max = 255)
    @ExportColumn(header = "邮箱密码", order = 7)
    private String emailPassword;

    /**
     * 发送者名称
     */
    @Size(max = 255)
    @ExportColumn(header = "发送者邮箱", order = 8)
    private String fromAddress;

    /**
     * 发送者名称
     */
    @Size(max = 255)
    @ExportColumn(header = "发送者名称", order = 8)
    private String fromName;

    /**
     * SSL链接的端口
     */
    @ExportColumn(header = "SSL链接的端口", order = 9)
    private Integer sslPort=465;

    /**
     * 启动tls
     */
    @ExportColumn(header = "启动tls", order = 10)
    private WhetherStatus startTls=WhetherStatus.yes;

    /**
     * 使用SSL连接
     */
    @ExportColumn(header = "使用SSL连接", order = 11)
    private WhetherStatus sslConnect=WhetherStatus.yes;

}
