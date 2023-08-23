/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-12-07 14:38
 */
package com.acooly.module.security.shiro.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

import java.io.Serializable;

/**
 * @author zhangpu
 * @date 2022-12-07 14:38
 */
@Slf4j
public class AcoolyCacheSessionDAO extends EnterpriseCacheSessionDAO {

    @Override
    protected void assignSessionId(Session session, Serializable sessionId) {
        ((AcoolySession) session).setId(sessionId);
    }
}
