package com.acooly.module.certification.cert;

import com.acooly.module.certification.enums.PhoneCertResult;

/**
 * @author liangsong
 * @date 2020-07-15 14:40
 */
public interface PhoneCertService {

    /**
     * 手机在网三要素
     * @param realName
     * @param certNo
     * @param mobile
     * @return
     */
    PhoneCertResult phoneCert(String realName, String certNo, String mobile);
}
