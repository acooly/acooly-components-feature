/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-01-07 14:27 创建
 */
package com.acooly.core.test.dubbo;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.facade.PageOrder;
import com.acooly.core.common.facade.PageResult;
import com.acooly.core.common.facade.SingleOrder;
import com.acooly.core.common.facade.SingleResult;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qiubo@yiji.com
 */
@Service(version = "1.0")
@Slf4j
public class Demo1ImplFacade implements Demo1Facade {
    @Override
    public SingleResult<String> echo(SingleOrder<String> msg) {
        log.info(RpcContext.getContext().getRemoteHost() + ":" + msg);
        return SingleResult.from(msg.getDto());
    }

    @Override
    public PageResult<String> echo1(PageOrder msg) {
        log.info("{}", msg);
        PageInfo<String> pageInfo = new PageInfo<>();
        pageInfo.setPageResults(Lists.newArrayList("a", "b", "c"));
        return PageResult.from(pageInfo);
    }
}
