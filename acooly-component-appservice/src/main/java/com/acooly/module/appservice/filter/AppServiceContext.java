/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-09-22 10:47 创建
 */
package com.acooly.module.appservice.filter;

import com.acooly.module.appservice.AppService;
import com.acooly.module.filterchain.Context;
import lombok.Data;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author qiubo@yiji.com
 */
@Data
public class AppServiceContext extends Context {
    private MethodInvocation methodInvocation;
    private Object target;
    private Object result;
    private Throwable targetThrowable;

    public String getLoggerMethodName() {
        String simpleName = this.target.getClass().getSimpleName();
        int idx = simpleName.indexOf("$$");
        if (idx != -1) {
            simpleName = simpleName.substring(0, idx);
        }
        return simpleName + "#" + this.methodInvocation.getMethod().getName();
    }

    public String getLogPrefix() {
        AppService annotation = this.methodInvocation.getMethod().getAnnotation(AppService.class);
        if (annotation != null) {
            return annotation.logPrefix();
        }
        return null;
    }
}
