/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-10-10 20:20 创建
 */
package com.acooly.core.test.appservice;

import com.acooly.core.common.facade.ResultBase;
import com.acooly.core.common.facade.SingleOrder;
import com.acooly.core.utils.Ids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiubo@yiji.com
 */
@RestController
@RequestMapping("appservice")
public class AppServiceTestContorller {

    @Autowired(required = false)
    private AppServiceTestService appServiceTestService;

    @RequestMapping("/test")
    public ResultBase test(AppDto appRequest) {
        return appServiceTestService.test(newTestOrder(appRequest));
    }

    @RequestMapping("/test1")
    public ResultBase test1(AppDto appRequest) {
        return appServiceTestService.test1(newTestOrder(appRequest));
    }

    @RequestMapping("/test2")
    public ResultBase test2(AppDto appRequest) {
        return appServiceTestService.test2(newTestOrder(appRequest));
    }

    @RequestMapping("/test3")
    public ResultBase test3(AppDto appRequest) {
        return appServiceTestService.test3(newTestOrder(appRequest));
    }

    private SingleOrder<AppDto> newTestOrder(AppDto appRequest) {
        SingleOrder<AppDto> testOrder = new SingleOrder<>();
        testOrder.setGid(Ids.gid());
        testOrder.setPartnerId("1");
        testOrder.setDto(appRequest);
        return testOrder;
    }
}
