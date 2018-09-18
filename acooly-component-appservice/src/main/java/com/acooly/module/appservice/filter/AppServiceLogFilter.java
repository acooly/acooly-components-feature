/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-09-22 11:09 创建
 */
package com.acooly.module.appservice.filter;

import com.acooly.module.filterchain.Filter;
import com.acooly.module.filterchain.FilterChain;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;

/**
 * @author qiubo@yiji.com
 */
public class AppServiceLogFilter implements Filter<AppServiceContext> {
    private static final Logger logger = LoggerFactory.getLogger(AppServiceLogFilter.class);

    private boolean dubboNotSupport = false;
    private Class providerLogFilter;
    private Method method;
    private Method markCurrentMethodNotPrintLogMethod;

    public AppServiceLogFilter() {
        try {
            providerLogFilter = Class.forName("com.acooly.module.dubbo.ProviderLogFilter");
            method = providerLogFilter.getMethod("isDubboProviderLogEnable");
            markCurrentMethodNotPrintLogMethod =
                    providerLogFilter.getMethod("markCurrentMethodNotPrintLog");
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            dubboNotSupport = true;
        }
    }

    @Override
    public void doFilter(AppServiceContext context, FilterChain<AppServiceContext> filterChain) {
        boolean dubboLogEnable = dubboLogged();
        if (dubboLogEnable) {
            try {
                markCurrentMethodNotPrintLogMethod.invoke(providerLogFilter);
            } catch (Exception e) {
                //do nothing
            }
        }
        long begin = System.currentTimeMillis();
        String logPrefix = context.getLogPrefix();
        Object[] args = context.getMethodInvocation().getArguments();
        if (Strings.isNullOrEmpty(logPrefix)) {
            logPrefix = context.getLoggerMethodName();
        }
        logger.info("[{}]请求入参:{}", logPrefix, args == null ? "无" : args);
        filterChain.doFilter(context);
        logger.info(
                "[{}]请求响应:{},耗时:{}ms",
                logPrefix,
                context.getResult() == null ? "无" : context.getResult(),
                System.currentTimeMillis() - begin);
    }

    private boolean dubboLogged() {
        if (dubboNotSupport) {
            return false;
        } else {
            try {
                Boolean logged = (Boolean) method.invoke(providerLogFilter);
                if (logged != null) {
                    return logged;
                }
            } catch (Throwable e) {
                //ignore
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
