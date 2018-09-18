package com.acooly.module.sms.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Dates;
import com.acooly.module.sms.dao.SmsLogDao;
import com.acooly.module.sms.domain.SmsLog;
import com.acooly.module.sms.service.SmsLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("smsSendService")
public class SmsLogServiceImpl extends EntityServiceImpl<SmsLog, SmsLogDao>
        implements SmsLogService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void saveInNewTrans(SmsLog smsLog) {
        save(smsLog);
    }

    @Override
    public int countByIp(String ip, long ms) {
        Date startTime = Dates.addDate(new Date(), -ms);
        return getEntityDao().countByIp(ip, startTime).intValue();
    }

    @Override
    public int countByMobileNo(String mobileNo, long ms) {
        Date startTime = Dates.addDate(new Date(), -ms);
        return getEntityDao().countByMobileNo(mobileNo, startTime).intValue();
    }
}
