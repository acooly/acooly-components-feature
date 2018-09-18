/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-04-20
 */
package com.acooly.module.certification.platform.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.certification.platform.dao.CertificationRecordDao;
import com.acooly.module.certification.platform.entity.CertificationRecord;
import com.acooly.module.certification.platform.service.CertificationRecordService;
import org.springframework.stereotype.Service;

/**
 * 实名认证记录表 Service实现
 *
 * <p>Date: 2017-04-20 16:53:33
 *
 * @author acooly
 */
@Service("certificationRecordService")
public class CertificationRecordServiceImpl
        extends EntityServiceImpl<CertificationRecord, CertificationRecordDao>
        implements CertificationRecordService {

    @Override
    public CertificationRecord findEntityByCarNoAndRealName(String idCarNo, String realName) {
        return getEntityDao().findEntityByCarNoAndRealName(idCarNo, realName);
    }
}
