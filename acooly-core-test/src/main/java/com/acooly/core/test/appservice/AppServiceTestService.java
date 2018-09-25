/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-10-10 20:21 创建
 */
package com.acooly.core.test.appservice;

import com.acooly.core.common.facade.SingleOrder;
import com.acooly.core.common.facade.SingleResult;
import com.acooly.module.appservice.AppService;

/**
 * @author qiubo@yiji.com
 */
@AppService
public class AppServiceTestService {

    public SingleResult<AppDto> test(SingleOrder<AppDto> orderBase) {
        //do what you like
        return SingleResult.from(orderBase.getDto());
    }

    @AppService.ValidationGroup(AppDto.Test1.class)
    public SingleResult<AppDto> test1(SingleOrder<AppDto> orderBase) {
        //do what you like
        return SingleResult.from(orderBase.getDto());
    }

    @AppService.ValidationGroup(AppDto.Test2.class)
    public SingleResult<AppDto> test2(SingleOrder<AppDto> orderBase) {
        //do what you like
        return SingleResult.from(orderBase.getDto());
    }

    @AppService.ValidationGroup(value = AppDto.Test2.class, checkDefaultGroup = false)
    public SingleResult<AppDto> test3(SingleOrder<AppDto> orderBase) {
        //do what you like
        return SingleResult.from(orderBase.getDto());
    }
}
