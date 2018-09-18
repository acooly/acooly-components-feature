/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@yiji.com 2014-12-03 01:53 创建
 *
 */
package com.acooly.module.appservice.ex;

import com.acooly.core.common.facade.ResultBase;
import com.acooly.core.utils.enums.ResultStatus;

/**
 * @author qzhanbo@yiji.com
 */
public class ThrowableExceptionHandler implements ExceptionHandler<Throwable> {

    @Override
    public void handle(ExceptionContext<?> context, Throwable throwable) {
        ResultBase res = context.getResponse();
        res.setStatus(ResultStatus.failure);
        res.setCode(ResultStatus.failure.code());
        res.setDetail("内部异常");
    }
}
