/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@yiji.com 2014-12-03 00:22 创建
 *
 */
package com.acooly.module.appservice.ex;

import com.acooly.core.common.facade.ResultBase;

/**
 * @author qzhanbo@yiji.com
 */
public class ExceptionContext<T extends ResultBase> {
    private Object request;
    private T response;

    public ExceptionContext(T response, Object... request) {
        this.request = request;
        this.response = response;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
