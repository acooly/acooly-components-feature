/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-10-04 22:05
 */
package com.acooly.component.data.ip.service;

import java.util.Locale;

/**
 * @author zhangpu
 * @date 2021-10-04 22:05
 */
public interface IpSearchService {

    /**
     * 判断IP是否在指定国家范围
     *
     * @param locale   国家
     * @param publicIp
     * @return
     */
    Boolean isInRange(Locale locale, String publicIp);

    /**
     * 判断是否中国IP
     *
     * @param publicIp
     * @return
     */
    default Boolean isChinaIp(String publicIp) {
        return isInRange(Locale.CHINA, publicIp);
    }

}
