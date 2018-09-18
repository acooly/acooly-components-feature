/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-03-24 19:43 创建
 */
package com.acooly.module.test.param;

import junitparams.custom.CustomParameters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author qiubo@yiji.com
 */
@Retention(RetentionPolicy.RUNTIME)
@CustomParameters(provider = CsvProvider.class)
public @interface CsvParameter {
    String value();
}
