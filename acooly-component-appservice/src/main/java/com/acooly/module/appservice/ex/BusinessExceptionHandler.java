/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * lixiang@yiji.com 2014-12-03 17:47 创建
 *
 */
package com.acooly.module.appservice.ex;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.facade.ResultBase;
import com.acooly.core.utils.enums.ResultStatus;

public class BusinessExceptionHandler implements ExceptionHandler<BusinessException> {

    public void handle(ExceptionContext<?> context, BusinessException e) {
        ResultBase res = context.getResponse();
        res.setStatus(ResultStatus.failure);
        res.setDetail(e.message());
        res.setCode(e.code());
    }
}
