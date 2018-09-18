/*
 * 修订记录:
 * zhike@yiji.com 2017-04-21 10:13 创建
 *
 */
package com.acooly.module.certification;

import com.acooly.module.certification.enums.BankCardResult;
import com.acooly.module.certification.enums.CertResult;

/**
 * 修订记录：
 *
 * @author zhike@yiji.com
 * @author shuijing
 */
public interface CertificationService {

    /**
     * 单条实名认证
     *
     * @param realName
     * @param idCardNo
     */
    CertResult certification(String realName, String idCardNo);

    /**
     * 银行卡二要素校验
     *
     * @param realName 持卡人姓名
     * @param cardNo   银行卡帐号
     * @return
     */
    BankCardResult bankCardCertTwo(String realName, String cardNo);

    /**
     * 银行卡三要素校验
     *
     * @param realName 持卡人姓名
     * @param cardNo   银行卡帐号
     * @param certId   身份证号
     * @return
     */
    BankCardResult bankCardCertThree(String realName, String cardNo, String certId);

    /**
     * 银行卡四要素校验
     *
     * @param realName 姓名
     * @param cardNo   银行卡帐号
     * @param certId   身份证号
     * @param phoneNum 手机号码
     * @return
     */
    BankCardResult bankCardCertFour(String realName, String cardNo, String certId, String phoneNum);
}
