/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-11-14 12:38 创建
 */
package com.acooly.module.safety;

import com.acooly.core.common.boot.Apps;
import com.acooly.module.safety.signature.SignTypeEnum;
import com.acooly.module.safety.signature.Signer;
import com.acooly.module.safety.signature.SignerFactory;

/**
 * @author zhangpu 2017-11-14 12:38
 */
public class Safes {

    private static SignerFactory signerFactory;

    public static Signer getSigner(String signType) {
        if (signerFactory == null) {
            synchronized (Safes.class) {
                if (signerFactory == null) {
                    signerFactory = Apps.getApplicationContext().getBean(SignerFactory.class);
                }
            }
        }
        return signerFactory.getSigner(signType);
    }

    public static Signer getSigner(SignTypeEnum signType) {
        return Safes.getSigner(signType.name());
    }

}
