/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-09-23 10:36 创建
 */
package com.acooly.module.appservice;

import com.acooly.module.appservice.filter.AppServiceContext;
import com.acooly.module.appservice.filter.AppServiceFilterChain;
import com.google.common.collect.Sets;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author qiubo@yiji.com
 */
public class AppServiceMethodInterceptor implements MethodInterceptor {
    private Object target;

    private Set<String> proxiedSet = Sets.newHashSet();
    private AppServiceFilterChain appServiceFilterChain;

    public AppServiceMethodInterceptor(Object target, AppServiceFilterChain appServiceFilterChain) {
        this.appServiceFilterChain = appServiceFilterChain;
        this.target = target;
    }

    public void register(Method method, Class targetClass) {
        AppService appService = method.getAnnotation(AppService.class);
        if (appService == null) {
            appService = method.getDeclaringClass().getAnnotation(AppService.class);
        }
        if (appService == null) {
            appService = (AppService) targetClass.getAnnotation(AppService.class);
        }
        if (appService == null || !appService.enable()) {
            return;
        }
        proxiedSet.add(getMethodKey(method));
    }

    public boolean canBeProxy() {
        return !proxiedSet.isEmpty();
    }

    private String getMethodKey(Method method) {
        StringBuilder key = new StringBuilder();
        key.append(method.getName());
        key.append("#");
        Class<?>[] classes = method.getParameterTypes();
        for (Class<?> aClass : classes) {
            key.append(aClass.getName());
            key.append(",");
        }
        return key.toString();
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (proxiedSet.contains(getMethodKey(methodInvocation.getMethod()))) {
            AppServiceContext appServiceContext = new AppServiceContext();
            appServiceContext.setTarget(target);
            appServiceContext.setMethodInvocation(methodInvocation);
            appServiceFilterChain.doFilter(appServiceContext);
            return appServiceContext.getResult();
        } else {
            try {
                return methodInvocation.getMethod().invoke(target, methodInvocation.getArguments());
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
    }
}
