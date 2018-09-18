/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-08-10
 */
package com.acooly.module.certification.platform.web.manage;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.certification.enums.CertTypeEnum;
import com.acooly.module.certification.platform.entity.BankCertificationRecord;
import com.acooly.module.certification.platform.service.BankCertificationRecordService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 银行卡四要素记录表 管理控制器
 *
 * @author shuijing
 * Date: 2017-08-10 18:15:53
 */
@Controller
@RequestMapping(value = "/manage/module/certification/bankCertificationRecord")
public class BankCertificationRecordManagerController extends AbstractJQueryEntityController<BankCertificationRecord, BankCertificationRecordService> {

    private static Map<Integer, String> allStatuss = Maps.newLinkedHashMap();

    static {
        allStatuss.put(1, "验证通过");
        allStatuss.put(0, "未通过");
    }

    @Autowired
    private BankCertificationRecordService bankCertificationRecordService;

    {
        allowMapping = "*";
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allStatuss", allStatuss);
        model.put("allCertTypes", CertTypeEnum.mapping());
    }
}
