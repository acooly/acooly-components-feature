/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-04-20
 */
package com.acooly.module.certification.platform.web.manage;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.certification.platform.entity.CertificationRecord;
import com.acooly.module.certification.platform.service.CertificationRecordService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 实名认证记录表 管理控制器
 *
 * @author acooly Date: 2017-04-20 16:53:33
 */
@Controller
@RequestMapping(value = "/manage/module/certification/certificationRecord")
public class CertificationRecordManagerController
        extends AbstractJQueryEntityController<CertificationRecord, CertificationRecordService> {

    private static Map<Integer, String> allStatuss = Maps.newLinkedHashMap();

    static {
        allStatuss.put(1, "验证通过");
        allStatuss.put(0, "未通过");
    }

    @SuppressWarnings("unused")
    @Autowired
    private CertificationRecordService certificationRecordService;

    {
        allowMapping = "*";
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allStatuss", allStatuss);
    }
}
