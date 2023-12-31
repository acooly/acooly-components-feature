package com.acooly.module.sms.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.sms.domain.SmsLog;
import com.acooly.module.sms.enums.SmsStatus;
import com.acooly.module.sms.service.SmsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/manage/module/sms/smsLog")
public class SmsLogManagerController extends AbstractJQueryEntityController<SmsLog, SmsLogService> {

    private static Map<Integer, String> allStatuss = SmsStatus.getMapping();
    @Autowired
    private SmsLogService smsLogService;

    {
        allowMapping = "list";
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allStatuss", allStatuss);
    }
}
