package com.acooly.module.certification.cert;

import com.acooly.module.certification.CertificationProperties;
import com.acooly.module.certification.enums.CertResult;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service("realNameAuthentication")
public class RealNameAuthenticationProxy
        implements RealNameAuthentication, ApplicationContextAware {

    private String provider;

    private Object object = new Object();

    private ApplicationContext applicationContext;

    private RealNameAuthentication realNameAuthentication;
    @Autowired
    private CertificationProperties certificationProperties;

    public String getProvider() {
        return getRealNameAuthentication().getProvider();
    }

    @Override
    public CertResult certification(String realName, String idCardNo) {
        return getRealNameAuthentication().certification(realName, idCardNo);
    }

    public RealNameAuthentication getRealNameAuthentication() {
        if (realNameAuthentication == null) {
            synchronized (object) {
                if (realNameAuthentication == null) {
                    realNameAuthentication =
                            (RealNameAuthentication)
                                    this.applicationContext.getBean(certificationProperties.getProvider().code());
                }
            }
        }
        return realNameAuthentication;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
