package com.acooly.module.certification.cert;

import com.acooly.module.certification.enums.BankCardResult;

/**
 * @author shuijing
 */
public interface BankCardCertService {

    BankCardResult bankCardCert(String realName, String cardNo, String certId, String phoneNum);
}
