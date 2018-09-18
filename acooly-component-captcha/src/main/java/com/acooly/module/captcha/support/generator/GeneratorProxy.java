package com.acooly.module.captcha.support.generator;

import com.acooly.module.captcha.Captcha;
import com.acooly.module.captcha.CaptchaGenerator;
import com.acooly.module.captcha.CaptchaProperties;
import com.acooly.module.captcha.exception.CaptchaGenerateException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @author shuijing
 */
@Service("captchaGenerator")
public class GeneratorProxy<V>
        implements CaptchaGenerator<V>,
        ApplicationContextAware,
        ApplicationListener<ContextRefreshedEvent> {

    private ApplicationContext applicationContext;

    private CaptchaGenerator generator;

    @Autowired
    private CaptchaProperties properties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (generator == null) {
            generator =
                    (CaptchaGenerator)
                            this.applicationContext.getBean(properties.getGeneratorType().code());
        }
    }

    @Override
    public Captcha<V> createCaptcha(String key, Long seconds) throws CaptchaGenerateException {
        return generator.createCaptcha(key, seconds);
    }
}
