/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@yiji.com 2014-12-03 00:25 创建
 * lixiang@yiji.com 2014-12-04 11:32 修改为按照名称查找处理器
 *
 */
package com.acooly.module.appservice.ex;

import com.acooly.core.common.exception.AppConfigException;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author qzhanbo@yiji.com
 */
public class ExceptionHandlers implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlers.class);

    private Map<Class<?>, ExceptionHandler<?>> handlerMap = Maps.newConcurrentMap();

    @Autowired
    private List<ExceptionHandler> handlerList;

    public static boolean isCglibProxyClass(Class clazz) {
        return clazz != null && clazz.getName().contains("$$");
    }

    private static Class<?> getInterfaceGenricType(
            Class<?> clazz, Class<?> interfaceClazz, int index) {
        Type[] genType = clazz.getGenericInterfaces();
        for (Type type : genType) {
            if (!(type instanceof ParameterizedType)) {
                continue;
            }
            if (((ParameterizedType) type).getRawType() != interfaceClazz) {
                continue;
            }
            Type[] params = ((ParameterizedType) type).getActualTypeArguments();
            if (index >= params.length || index < 0) {
                return Object.class;
            }
            if (!(params[index] instanceof Class)) {
                return Object.class;
            }
            return (Class<?>) params[index];
        }
        return Object.class;
    }

    private void addHandler(ExceptionHandler<?> handler) {
        Class<?> clazz = getInterfaceGenricType(getTargetClass(handler), ExceptionHandler.class, 0);
        if (clazz == Object.class) {
            throw new AppConfigException(handler.getClass().getName() + "没有指定泛型参数");
        }
        handlerMap.put(clazz, handler);
        logger.info("添加异常处理器:{} -> {}", clazz.getName(), handler);
    }

    private Class<?> getTargetClass(Object bean) {
        Class targetClass;
        for (targetClass = AopUtils.getTargetClass(bean);
             isCglibProxyClass(targetClass);
             targetClass = targetClass.getSuperclass()) {
        }
        return targetClass;
    }

    public boolean handle(ExceptionContext<?> context, Throwable throwable) {
        if (context == null || throwable == null) {
            return false;
        }
        return handle(context, throwable, throwable.getClass());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private boolean handle(ExceptionContext context, Throwable throwable, Class<?> throwableClazz) {
        ExceptionHandler handler = handlerMap.get(throwableClazz);
        if (handler == null) {
            Class<?> superclass = throwableClazz.getSuperclass();
            while (superclass != Object.class) {
                boolean result = handle(context, throwable, superclass);
                return result;
            }
            return false;
        }
        try {
            handler.handle(context, throwable);
            if (throwable.getClass() != throwableClazz) {
                handlerMap.put(throwable.getClass(), handler);
            }
        } catch (Exception e) {
            logger.error("异常处理器里抛异常,有才华!", e);
        }
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        handlerList.forEach(this::addHandler);
    }
}
