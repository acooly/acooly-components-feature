/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-09-22 15:26 创建
 */
package com.acooly.module.appservice;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * 在服务实现类、公共方法、父类中的公共方法中标注此注解，实现以下能力：
 *
 * <ul>
 * <li>1. 选择性打印日志(当dubbo provider log filter生效时，不打印，当此filter不生效时打印)
 * <li>2. 检查请求对象
 * <li>3. 异常统一处理
 * </ul>
 * <p>
 * 此注解可以用在类上或者方法上，仅非静态公共方法生效
 *
 * @author qiubo@yiji.com
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Service
public @interface AppService {
    boolean enable() default true;

    String logPrefix() default "";

    /**
     * 请求对象校验组 jsr303
     */
    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @interface ValidationGroup {

        /**
         * 校验组,默认不指定任何校验组
         */
        Class<?> value() default Null.class;

        /**
         * 是否启用默认校验组
         */
        boolean checkDefaultGroup() default true;

        interface Null {
        }

        interface Create {
        }

        interface Update {
        }

        interface Delete {
        }
    }
}
