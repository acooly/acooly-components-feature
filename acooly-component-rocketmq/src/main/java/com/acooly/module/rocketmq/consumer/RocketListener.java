package com.acooly.module.rocketmq.consumer;

import java.lang.annotation.*;

/**
 * @author qiubo
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RocketListener {
    /**
     * 设置监听的topic
     */
    String topic();

    String tags() default "*";

}
