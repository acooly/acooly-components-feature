/*
 * www.prosysoft.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * shuijing 2018-06-28 23:13 创建
 */
package com.acooly.module.distributedlock.exception;

/**
 * @author shuijing
 */
public class DistributedException extends RuntimeException {

    public DistributedException(String msg) {
        super(msg);
    }

    public DistributedException(String msg, Exception e) {
        super(msg, e);
    }

}
