package com.acooly.module.sms.template;

import com.acooly.core.utils.FreeMarkers;
import com.acooly.module.sms.SmsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SmsTemplateImpl implements SmsTemplate {

    @Autowired
    private SmsProperties properties;

    public String getMessage(String key, Map<String, Object> data) {
        String template = getTemplates(key);
        return FreeMarkers.rendereString(template, data);
    }

    protected String getTemplates(String key) {
        return properties.getTemplate().get(key);
    }
}
