/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-01-07 14:29 创建
 */
package com.acooly.core.test.dubbo;

import com.acooly.core.common.boot.ApplicationContextHolder;
import com.acooly.core.common.facade.SingleOrder;
import com.acooly.core.common.facade.SingleResult;
import com.acooly.core.test.dubbo.mock.XXFacade;
import com.acooly.module.security.service.SSOAuthzService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author qiubo@yiji.com
 */
@RestController
public class DubboController {

    @Reference(version = "1.0")
    private DemoFacade demoFacade;

    @Reference(version = "1.0")
    private Demo1Facade demo1Facade;

    @Reference(version = "1.0", group = "com.acooly.module.security.service")
    private SSOAuthzService roleAuthzFacade;
    @Reference(version = "1.0")
    private XXFacade xxFacade;

    @RequestMapping(value = "/dubbo", method = RequestMethod.GET)
    public SingleResult<String> get(String msg) {
        SingleOrder<String> request = new SingleOrder<>();
        request.gid().partnerId("test");
        request.setDto(msg);
        RpcContext context = RpcContext.getContext();
        return demoFacade.echo(request);
    }

    @RequestMapping(value = "/dubbo1", method = RequestMethod.GET)
    public SingleResult<String> get1(String msg) {
        SingleOrder<String> request = new SingleOrder<>();
        request.gid().partnerId("test");
        request.setDto("a");
        return demoFacade.echo1(request);
    }


    @RequestMapping(value = "/dubbo2", method = RequestMethod.GET)
    public SingleResult<String> get2(String msg) {
        SingleOrder<String> request = new SingleOrder<>();
        request.gid().partnerId("test");
        request.setDto("a");
        return demo1Facade.echo(request);
    }

    @RequestMapping(value = "/dubboMock", method = RequestMethod.GET)
    public SingleResult<String> dubboMock(String msg) {
        SingleOrder<String> request = new SingleOrder<>();
        request.gid().partnerId("test");
        request.setDto(msg);
        return xxFacade.echo(request);
    }

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public void roleGet(String msg) throws Exception {
        SSOAuthzService authzService = ApplicationContextHolder.get().getBean(SSOAuthzService.class);
        Boolean p =
                roleAuthzFacade.permitted("/manage/paycore/account/accountOrder/listJson.html", "admin");
        System.out.println(p);
    }
}
