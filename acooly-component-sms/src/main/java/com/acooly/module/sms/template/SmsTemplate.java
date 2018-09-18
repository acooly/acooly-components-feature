package com.acooly.module.sms.template;

import java.util.Map;

public interface SmsTemplate {

    String getMessage(String key, Map<String, Object> data);
}
