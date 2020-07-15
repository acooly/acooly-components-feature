/*
 * 修订记录:
 * zhike@yiji.com 2017-04-21 10:13 创建
 *
 */
package com.acooly.module.certification;

import com.acooly.module.certification.enums.BankCardResult;
import com.acooly.module.certification.enums.CertResult;
import com.acooly.module.certification.enums.EnterpriseBusinessInfoResult;
import com.acooly.module.certification.enums.PhoneCertResult;

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

    /**
     * 企业工商信息查询
     * @param comInfo(完整的公司名称、注册号、信用代码、组织机构代码、税务登记号 任选其一)
     * @return
     */
    EnterpriseBusinessInfoResult enterpriseBusinessInfo(String comInfo);

    /**
     * 手机在网三要素
     * @param realName
     * @param certNo
     * @param mobile
     * @return
     */
    PhoneCertResult phoneCert(String realName, String certNo, String mobile);
}
