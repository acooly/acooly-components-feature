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

    void send(MailDto dto);

    void sendAsync(MailDto dto);
}
