package com.acooly.module.certification.cert;

import com.acooly.core.common.boot.ApplicationContextHolder;
import com.acooly.module.certification.CertificationProperties;
import com.acooly.module.certification.enums.PhoneCertResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @author shuijing
 */
@Service("phoneCertService")
public class PhoneCertServiceProxy
        implements PhoneCertService, ApplicationListener<ContextRefreshedEvent> {

    private PhoneCertService phoneCertService;

    @Autowired
    private CertificationProperties properties;

    @Override
    public PhoneCertResult phoneCert(
            String realName, String certNo, String mobile) {
        return phoneCertService.phoneCert(realName, certNo, mobile);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (phoneCertService == null) {
            if (properties.getPhoneCertProvider() == null) {
                return;
            }
            phoneCertService =
                    (PhoneCertService)
                            ApplicationContextHolder.get().getBean(properties.getPhoneCertProvider().code());
        }
    }
}
