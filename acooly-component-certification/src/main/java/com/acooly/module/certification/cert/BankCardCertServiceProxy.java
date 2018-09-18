package com.acooly.module.certification.cert;

import com.acooly.core.common.boot.ApplicationContextHolder;
import com.acooly.module.certification.CertificationProperties;
import com.acooly.module.certification.enums.BankCardResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @author shuijing
 */
@Service("bankCardCertService")
public class BankCardCertServiceProxy
        implements BankCardCertService, ApplicationListener<ContextRefreshedEvent> {

    private BankCardCertService bankCardCertService;

    @Autowired
    private CertificationProperties properties;

    @Override
    public BankCardResult bankCardCert(
            String realName, String cardNo, String certId, String phoneNum) {
        return bankCardCertService.bankCardCert(realName, cardNo, certId, phoneNum);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (bankCardCertService == null) {
            bankCardCertService =
                    (BankCardCertService)
                            ApplicationContextHolder.get().getBean(properties.getBankCertProvider().code());
        }
    }
}
