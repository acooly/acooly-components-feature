package com.acooly.module.scheduler.executor;

import com.acooly.core.common.boot.ApplicationContextHolder;
import com.acooly.module.scheduler.domain.SchedulerRule;
import com.acooly.module.scheduler.exceptions.SchedulerExecuteException;
import com.google.common.collect.Maps;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author shuijing
 */
@TaskExecutor.Type(type = TaskTypeEnum.LOCAL_TASK)
public class LocalTaskExecutor implements TaskExecutor {

    //缓存 bean
    private Map<Class<?>, Object> beanMap;

    //缓存 class
    private Map<String, Class<?>> classMap;

    private ExecutorService executorService =
            Executors.newCachedThreadPool(new CustomizableThreadFactory("scheduler-LocalTaskExecutor-"));

    @Override
    public Boolean execute(SchedulerRule schedulerRule) {
        String className = schedulerRule.getClassName();
        try {
            Class<?> clazz = getClassByCache(className);
            Object bean = getBeanByCache(clazz);
            String methodName = schedulerRule.getMethodName();
            Method declaredMethod = clazz.getDeclaredMethod(methodName, null);
            if (!Modifier.isPublic(declaredMethod.getModifiers())) {
                throw new SchedulerExecuteException("本地执行方法必须为public:" + declaredMethod);
            }
            declaredMethod.setAccessible(true);
            CompletableFuture.runAsync(
                    () -> ReflectionUtils.invokeMethod(declaredMethod, bean), executorService)
                    .get(TIME_OUT, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new SchedulerExecuteException(e);
        }
        return true;
    }

    private Class<?> getClassByCache(String className) throws ClassNotFoundException {
        Class<?> clazz;
        if (classMap.containsKey(className)) {
            clazz = classMap.get(className);
        } else {
            clazz = Class.forName(className);
            classMap.put(className, clazz);
        }
        return clazz;
    }

    private Object getBeanByCache(Class<?> clazz) {
        Object object;
        if (beanMap.containsKey(clazz)) {
            object = beanMap.get(clazz);
        } else {
            object = ApplicationContextHolder.get().getBean(clazz);
            beanMap.put(clazz, object);
        }
        return object;
    }

    @Override
    public void destroy() throws Exception {
        classMap.clear();
        beanMap.clear();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        classMap = Maps.newConcurrentMap();
        beanMap = Maps.newConcurrentMap();
    }
}
