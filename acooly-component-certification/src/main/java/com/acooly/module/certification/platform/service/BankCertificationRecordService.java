/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-08-10
 *
 */
package com.acooly.module.certification.platform.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.certification.platform.entity.BankCertificationRecord;

/**
 * 银行卡四要素记录表 Service接口
 *
 * <p>Date: 2017-08-10 18:15:53
 *
 * @author shuijing
 */
public interface BankCertificationRecordService extends EntityService<BankCertificationRecord> {
    BankCertificationRecord findEntityByCardNo(String cardNo);
}
