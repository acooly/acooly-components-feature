/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-08-10
 */
package com.acooly.module.certification.platform.dao;

import com.acooly.module.certification.platform.entity.BankCertificationRecord;
import com.acooly.module.jpa.EntityJpaDao;
import org.springframework.data.jpa.repository.Query;

/**
 * 银行卡四要素记录表 JPA Dao
 *
 * <p>Date: 2017-08-10 18:15:53
 *
 * @author shuijing
 */
public interface BankCertificationRecordDao extends EntityJpaDao<BankCertificationRecord, Long> {
    @Query("from BankCertificationRecord where cardNo = ?1 ")
    BankCertificationRecord findEntityByCardNo(String cardNo);
}
