/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-19 22:11 创建
 */
package com.acooly.core.test.openapi;

import com.acooly.module.appopenapi.message.BusinessLogRequest;
import com.acooly.openapi.framework.common.message.ApiResponse;
import com.acooly.openapi.framework.core.test.AbstractApiServieTests;
import org.junit.Test;

import java.util.UUID;

/**
 * @author qiubo@yiji.com
 */
public class BLogApiServiceTest extends AbstractApiServieTests {
    {
        gatewayUrl = "http://localhost:8081/gateway.do";
        notifyUrl = "http://127.0.0.1:8081/notify/receiver";
        signType = null;
    }

    @Test
    public void testPayOrder1() throws Exception {
        service = "bLog";
        BusinessLogRequest request = new BusinessLogRequest();
        request.setRequestNo(UUID.randomUUID().toString());
        BusinessLogRequest.BLog bLog = new BusinessLogRequest.BLog();
        bLog.name("x").body("k", "v").body("k1", "v1");
        request.addContents(bLog);
        bLog = new BusinessLogRequest.BLog();
        bLog.name("x1").body("k1", "v1").body("k2", 3);
        request.addContents(bLog);
        ApiResponse apiResponse = request(request, ApiResponse.class);
        System.out.println(apiResponse);
    }
}
