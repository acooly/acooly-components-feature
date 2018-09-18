/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-09-28 11:16 创建
 */
package com.acooly.module.appservice.ex;

import com.acooly.core.common.exception.OrderCheckException;
import com.acooly.core.common.facade.ResultBase;
import com.acooly.core.utils.enums.ResultStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * @author qiubo@yiji.com
 */
public class ConstraintViolationExceptionHandler
        implements ExceptionHandler<ConstraintViolationException> {
    @Override
    public void handle(ExceptionContext<?> context, ConstraintViolationException e) {
        OrderCheckException exception = new OrderCheckException();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            exception.addError(
                    constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }
        ResultBase res = context.getResponse();
        res.setStatus(ResultStatus.failure);
        res.setDetail(e.getMessage());
        res.setCode(ResultStatus.failure.getCode());
    }
}
