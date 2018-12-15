/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-19 22:11 创建
 */
package com.acooly.core.test.openapi;

import com.acooly.core.utils.Money;
import com.acooly.openapi.framework.core.test.AbstractApiServieTests;
import org.junit.Test;

import java.util.UUID;

/**
 * @author qiubo@yiji.com
 */
public class AppPayOrderApiServiceTest extends AbstractApiServieTests {
    {
        gatewayUrl = "http://localhost:8081/gateway.do";
        notifyUrl = "http://127.0.0.1:8081/notify/receiver";
        signType = null;
    }

    @Test
    public void testPayOrder1() throws Exception {
        service = "payOrder";
        Money amount = Money.amout("1000.00");
        PayOrderRequest request = new PayOrderRequest();
        request.setRequestNo(UUID.randomUUID().toString());
        request.setAmount(amount);
        request.setPayerUserId("09876543211234567890");
        request.setContext("这是客户端参数:{userName:1,\"password\":\"12121\"}");
        request(request, PayOrderResponse.class);
    }
}
