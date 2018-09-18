/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-08-10
 */
package com.acooly.module.certification.platform.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.certification.platform.dao.BankCertificationRecordDao;
import com.acooly.module.certification.platform.entity.BankCertificationRecord;
import com.acooly.module.certification.platform.service.BankCertificationRecordService;
import org.springframework.stereotype.Service;

/**
 * 银行卡四要素记录表 Service实现
 *
 * <p>Date: 2017-08-10 18:15:53
 *
 * @author shuijing
 */
@Service("bankCertificationRecordService")
public class BankCertificationRecordServiceImpl
        extends EntityServiceImpl<BankCertificationRecord, BankCertificationRecordDao>
        implements BankCertificationRecordService {

    @Override
    public BankCertificationRecord findEntityByCardNo(String cardNo) {
        return getEntityDao().findEntityByCardNo(cardNo);
    }
}
