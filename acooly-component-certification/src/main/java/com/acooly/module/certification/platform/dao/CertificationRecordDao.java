/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-04-20
 */
package com.acooly.module.certification.platform.dao;

import com.acooly.module.certification.platform.entity.CertificationRecord;
import com.acooly.module.jpa.EntityJpaDao;
import org.springframework.data.jpa.repository.Query;

/**
 * 实名认证记录表 JPA Dao
 *
 * <p>Date: 2017-04-20 16:53:33
 *
 * @author acooly
 */
public interface CertificationRecordDao extends EntityJpaDao<CertificationRecord, Long> {

    @Query("from CertificationRecord where idCarNo = ?1 and realName = ?2")
    CertificationRecord findEntityByCarNoAndRealName(String idCarNo, String realName);
}
