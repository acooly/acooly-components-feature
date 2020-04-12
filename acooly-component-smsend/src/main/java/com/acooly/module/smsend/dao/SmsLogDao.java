package com.acooly.module.smsend.dao;

import com.acooly.module.jpa.EntityJpaDao;
import com.acooly.module.smsend.domain.SmsLog;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * 短信发送记录 JPA Dao
 *
 * <p>Date: 2013-08-05 22:28:35
 *
 * @author Acooly Code Generator
 */
public interface SmsLogDao extends EntityJpaDao<SmsLog, Long> {

    @Query("select count(e) from SmsLog e where e.clientIp=?1 and e.createTime > ?2")
    Long countByIp(String ip, Date startTime);

    @Query("select count(e) from SmsLog e where e.mobileNo=?1 and e.createTime > ?2")
    Long countByMobileNo(String mobileNo, Date startTime);
}
