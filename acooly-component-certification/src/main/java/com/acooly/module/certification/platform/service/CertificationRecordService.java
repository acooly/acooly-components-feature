/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-04-20
 *
 */
package com.acooly.module.certification.platform.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.certification.platform.entity.CertificationRecord;

/**
 * 实名认证记录表 Service接口
 *
 * <p>Date: 2017-04-20 16:53:33
 *
 * @author acooly
 */
public interface CertificationRecordService extends EntityService<CertificationRecord> {
    /**
     * 根据身份证号查询数据
     *
     * @param idCarNo
     * @return
     */
    CertificationRecord findEntityByCarNoAndRealName(String idCarNo, String realName);
}
