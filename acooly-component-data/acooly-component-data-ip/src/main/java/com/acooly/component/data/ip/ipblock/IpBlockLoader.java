/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-10-04 19:49
 */
package com.acooly.component.data.ip.ipblock;

import java.util.List;
import java.util.Locale;

/**
 * IP段加载器
 *
 * @author zhangpu
 * @date 2021-10-04 19:49
 */
public interface IpBlockLoader {

    /**
     * 加载ip段集合列表
     *
     * @return
     */
    List<String> load(Locale locale);

}
