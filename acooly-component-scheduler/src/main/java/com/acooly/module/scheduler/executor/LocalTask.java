package com.acooly.module.scheduler.executor;

import java.lang.reflect.Method;
import org.springframework.util.ReflectionUtils;

/**
 * Zhouxi O_o
 *
 * @author xi
 * @description
 */
public class LocalTask implements Runnable {
    Method declaredMethod;
    Object bean;

    public LocalTask( Method declaredMethod, Object bean ) {
        this.declaredMethod = declaredMethod;
        this.bean = bean;
    }

    @Override
    public void run() {
        ReflectionUtils.invokeMethod(declaredMethod, bean);
    }
}
