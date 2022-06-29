/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2016-11-01 02:41 创建
 */
package com.acooly.module.olog.collector.logger.operator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author acooly
 */
public interface OperatorUserCollector {

    /**
     * 从请求收集操作员信息
     *
     * @param request
     * @return
     */
    OlogOperator getOperatorUser(HttpServletRequest request);
}
