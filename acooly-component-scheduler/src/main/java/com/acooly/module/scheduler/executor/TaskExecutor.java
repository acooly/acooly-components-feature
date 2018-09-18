package com.acooly.module.scheduler.executor;

import com.acooly.module.scheduler.domain.SchedulerRule;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author shuijing
 */
public interface TaskExecutor extends InitializingBean, DisposableBean {
    int TIME_OUT = 5 * 1000;

    Boolean execute(SchedulerRule schedulerRule);

    @Service
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface Type {
        TaskTypeEnum type();
    }
}
