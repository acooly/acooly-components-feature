/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-05 01:57
 */
package com.acooly.module.smsend.web;

import com.acooly.module.smsend.sender.ShortMessageSender;
import com.acooly.module.smsend.sender.ShortMessageSenderImpl;
import com.acooly.module.smsend.sender.dto.SmsResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author zhangpu
 * @date 2020-05-05 01:57
 */
@Slf4j
@Controller
@RequestMapping("/test/smsend")
public class ShortMessageSenderTestController {

    @Autowired
    private ShortMessageSender shortMessageSender;

    @RequestMapping("sendTemplate")
    @ResponseBody
    public SmsResult testSendTemplate() {
        Map<String, String> smsParam = new ListOrderedMap<String, String>();
        smsParam.put("captcha", "121312");
        smsParam.put("effectiveMinute", "10");
        return shortMessageSender.sendTemplate("13896177630", "SMS_187930527", smsParam);
    }

}
