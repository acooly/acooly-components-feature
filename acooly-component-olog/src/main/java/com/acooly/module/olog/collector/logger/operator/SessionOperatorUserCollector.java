/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2016-11-01 02:42 创建
 */
package com.acooly.module.olog.collector.logger.operator;

import com.acooly.module.security.domain.User;
import com.acooly.module.security.utils.ShiroUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author acooly
 */
@Component
public class SessionOperatorUserCollector implements OperatorUserCollector {

    @Override
    public OlogOperator getOperatorUser(HttpServletRequest request) {

        DefaultOlogOperator ologOperator = new DefaultOlogOperator();
        User currentUser = ShiroUtils.getCurrentUser();
        if (currentUser != null) {
            ologOperator.setOperatorUserName(currentUser.getUsername());
            ologOperator.setOperatorUserId(currentUser.getId());
        }
        return ologOperator;
    }
}
