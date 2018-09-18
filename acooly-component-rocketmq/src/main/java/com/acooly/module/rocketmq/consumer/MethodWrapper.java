package com.acooly.module.rocketmq.consumer;


import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author qiubo
 */
@Data
public class MethodWrapper {
    private final Object bean;
    private final Method method;

    public String getMethodName() {
        return method.getDeclaringClass().getName() + "#" + method.getName();
    }
}
