/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-01-07 14:27 创建
 */
package com.acooly.core.test.dubbo;

import com.acooly.core.common.facade.PageOrder;
import com.acooly.core.common.facade.PageResult;
import com.acooly.core.common.facade.SingleOrder;
import com.acooly.core.common.facade.SingleResult;
import com.acooly.module.appservice.AppService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qiubo@yiji.com
 */
@Service(version = "1.0")
@Slf4j
public class DemoFacadeImpl implements DemoFacade {
    @Reference(version = "1.0")
    private Demo1Facade demo1Facade;

    @Override
    public SingleResult<String> echo(SingleOrder<String> msg) {
        return getStringSingleResult(msg);
    }

    private SingleResult<String> getStringSingleResult(SingleOrder<String> msg) {
        log.info(RpcContext.getContext().getRemoteHost() + ":" + msg);
        demo1Facade.echo(SingleOrder.from(msg.getDto()));
        PageResult<String> result =
                demo1Facade.echo1(
                        PageOrder.from()
                                .pageInfo()
                                .map("EQ_name", "bohr")
                                .sortMap("name", Boolean.TRUE));
        if (result.success()) {
            log.info("remote invoke success:{}", result.getDto().getPageResults());
        }
        return SingleResult.from(msg.getDto());
    }

    @AppService(logPrefix = "测试")
    @Override
    public SingleResult<String> echo1(SingleOrder<String> msg) {
        return SingleResult.from("a");
    }
}
