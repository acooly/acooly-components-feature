/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-12-07 14:15
 */
package com.acooly.module.security.shiro.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;

/**
 * @author zhangpu
 * @date 2022-12-07 14:15
 */
@Slf4j
public class AcoolySessionFactory implements SessionFactory {
    @Override
    public Session createSession(SessionContext initData) {
        if (initData != null) {
            String host = initData.getHost();
            if (host != null) {
                return new AcoolySession(host);
            }
        }
        return new AcoolySession();
    }
}
