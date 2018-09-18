/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@yiji.com 2014-12-03 00:20 创建
 *
 */
package com.acooly.module.appservice.ex;

/**
 * @author qzhanbo@yiji.com
 */
public interface ExceptionHandler<EX extends Throwable> {
    void handle(ExceptionContext<?> context, EX ex);
}
