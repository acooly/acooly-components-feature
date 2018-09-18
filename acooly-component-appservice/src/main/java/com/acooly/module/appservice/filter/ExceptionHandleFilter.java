/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-09-22 10:56 创建
 */
package com.acooly.module.appservice.filter;

import com.acooly.core.common.exception.AppConfigException;
import com.acooly.core.common.facade.ResultBase;
import com.acooly.core.utils.enums.ResultStatus;
import com.acooly.module.appservice.ex.ExceptionContext;
import com.acooly.module.appservice.ex.ExceptionHandlers;
import com.acooly.module.filterchain.Filter;
import com.acooly.module.filterchain.FilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;

import java.lang.reflect.InvocationTargetException;

/**
 * @author qiubo@yiji.com
 */
public class ExceptionHandleFilter implements Filter<AppServiceContext> {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandleFilter.class);

    @Autowired
    private ExceptionHandlers exceptionHandlers;

    @Override
    public void doFilter(AppServiceContext context, FilterChain<AppServiceContext> filterChain) {
        try {
            filterChain.doFilter(context);
            Throwable targetThrowable = context.getTargetThrowable();
            if (targetThrowable != null) {
                handleThrowable(context, targetThrowable);
            }
        } catch (Throwable e) {
            handleThrowable(context, e);
        }
    }

    private void handleThrowable(AppServiceContext context, Throwable e) {
        if (e instanceof InvocationTargetException) {
            e = ((InvocationTargetException) e).getTargetException();
        }
        logger.error("处理异常:", e);
        Class<?> returnType = context.getMethodInvocation().getMethod().getReturnType();
        if (returnType != null && ResultBase.class.isAssignableFrom(returnType)) {
            Object result = instantiate(returnType);
            ResultBase newResult = (ResultBase) result;
            ExceptionContext<?> exceptionContext =
                    new ExceptionContext<>(newResult, context.getMethodInvocation().getArguments());
            if (!exceptionHandlers.handle(exceptionContext, e)) {
                newResult.setDetail(e.getMessage());
                newResult.setStatus(ResultStatus.failure);
            }
            context.setResult(newResult);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

    private <T> T instantiate(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new AppConfigException(clazz.getName() + "不能初始化");
        }
    }
}
