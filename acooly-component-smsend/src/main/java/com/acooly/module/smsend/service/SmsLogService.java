package com.acooly.module.smsend.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.smsend.domain.SmsLog;

/**
 * 短信发送记录 Service
 *
 * <p>Date: 2013-08-05 22:28:35
 *
 * @author Acooly Code Generator
 */
public interface SmsLogService extends EntityService<SmsLog> {

    void saveInNewTrans(SmsLog smsLog);

    int countByIp(String ip, long ms);

    int countByMobileNo(String mobileNo, long ms);
}
