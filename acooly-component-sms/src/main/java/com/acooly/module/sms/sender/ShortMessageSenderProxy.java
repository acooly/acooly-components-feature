package com.acooly.module.sms.sender;

import com.acooly.module.sms.SmsProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("shortMessageSender")
public class ShortMessageSenderProxy implements ShortMessageSender, ApplicationContextAware {

    private String provider;

    private Object object = new Object();

    private ApplicationContext applicationContext;

    private ShortMessageSender shortMessageSender;
    @Autowired
    private SmsProperties smsProperties;

    @Override
    public String send(String mobileNo, String content) {
        return getShortMessageSender().send(mobileNo, content);
    }

    @Override
    public String send(List<String> mobileNos, String content) {
        return getShortMessageSender().send(mobileNos, content);
    }

    public String getProvider() {
        return getShortMessageSender().getProvider();
    }

    public ShortMessageSender getShortMessageSender() {
        if (shortMessageSender == null) {
            synchronized (object) {
                if (shortMessageSender == null) {
                    shortMessageSender =
                            (ShortMessageSender)
                                    this.applicationContext.getBean(smsProperties.getProvider().code());
                }
            }
        }
        return shortMessageSender;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
