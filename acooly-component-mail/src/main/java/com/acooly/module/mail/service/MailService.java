/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-25 11:17 创建
 */
package com.acooly.module.mail.service;

import com.acooly.module.mail.MailDto;

/**
 * @author qiubo@yiji.com
 */
public interface MailService {

    /**
     * 邮件发送（同步）
     *
     * @param dto
     */
    void send(MailDto dto);

    /**
     * 邮件发送（异步）
     *
     * @param dto
     */
    void sendAsync(MailDto dto);


    /**
     * 邮件发送（同步）
     * <p>
     * 指定登陆用户的配置，如果没有配置，则使用默认配置
     *
     * @param dto
     */
    void send(MailDto dto, Long sysUserId);
}
