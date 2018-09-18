/*
 * 修订记录:
 * zhike@yiji.com 2017-04-20 17:31 创建
 *
 */
package com.acooly.module.certification.cert;

import com.acooly.module.certification.enums.CertResult;

/**
 * 修订记录：
 *
 * @author zhike@yiji.com
 */
public interface RealNameAuthentication {

    /**
     * 实名认证
     *
     * @param realName
     * @param idCardNo
     */
    CertResult certification(String realName, String idCardNo);

    String getProvider();
}
