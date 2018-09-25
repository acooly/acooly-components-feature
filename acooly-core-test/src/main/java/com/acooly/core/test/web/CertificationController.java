package com.acooly.core.test.web;

import com.acooly.module.certification.CertificationService;
import com.acooly.module.certification.enums.BankCardResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shuijing
 */
@Slf4j
@RestController
@RequestMapping(value = "/cert")
public class CertificationController {

    @Autowired(required = false)
    private CertificationService certificationService;

    @RequestMapping("/four")
    public void four(String realName, String cardNo, String certId, String phoneNum) {
        BankCardResult result =
                certificationService.bankCardCertFour(realName, cardNo, certId, phoneNum);
        log.info("银行卡四要素验证结果:{}", result.toString());
    }

    @RequestMapping("/three")
    public void three(String realName, String cardNo, String certId) {
        BankCardResult result = certificationService.bankCardCertThree(realName, cardNo, certId);
        log.info("银行卡三要素验证结果:{}", result.toString());
    }

    @RequestMapping("/two")
    public void two(String realName, String cardNo) {
        BankCardResult result = certificationService.bankCardCertTwo(realName, cardNo);
        log.info("银行卡二要素验证结果:{}", result.toString());
    }
}
