package com.acooly.module.smsend.sender;

import com.acooly.module.smsend.enums.SmsProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("shortMessageSender")
public class ShortMessageSenderProxy implements ShortMessageSender, ApplicationContextAware {

    private String provider;

    private Object object = new Object();

    private ApplicationContext applicationContext;

    private ShortMessageSender shortMessageSender;

    @Override
    public String send(String mobileNo, String content) {
        return null;
    }

    @Override
    public String send(String mobileNo, String templateCode, Map<String, String> templateParams) {
        return null;
    }

    @Override
    public String send(List<String> mobileNos, String content) {
        return null;
    }

    @Override
    public String send(List<String> mobileNos, String templateCode, Map<String, String> templateParams) {
        return null;
    }

    @Override
    public SmsProvider getProvider() {
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
