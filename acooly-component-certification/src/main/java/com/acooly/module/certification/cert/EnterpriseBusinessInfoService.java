/**
 * acooly-components-feature-parent
 * <p>
 * Copyright 2019 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhike
 * @date 2019-09-05 17:46
 */
package com.acooly.module.certification.cert;

import com.acooly.module.certification.enums.EnterpriseBusinessInfoResult;

/**
 *
 * @author zhike
 * @date 2019-09-05 17:46
 */
public interface EnterpriseBusinessInfoService {

    /**
     * 企业工商信息查询
     * @param comInfo(完整的公司名称、注册号、信用代码、组织机构代码、税务登记号 任选其一)
     * @return
     */
    EnterpriseBusinessInfoResult enterpriseBusinessInfo(String comInfo);
}
