/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-03-18 15:08 创建
 */
package com.acooly.core.test.openapi;

import com.acooly.module.appopenapi.message.BannerListRequest;
import com.acooly.module.appopenapi.message.BannerListResponse;
import com.acooly.openapi.framework.common.enums.DeviceType;
import com.acooly.openapi.framework.core.test.AbstractApiServieTests;
import com.acooly.openapi.framework.domain.LoginRequest;
import com.acooly.openapi.framework.domain.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.UUID;

/**
 * @author qiubo@yiji.com
 */
@Slf4j
public class AnonymousAppBannerListApiServiceTest extends AbstractApiServieTests {
    {
        gatewayUrl = "http://localhost:8081/gateway.do";
        partnerId = "anonymous";
        accessKey = "anonymous";
        secretKey = "anonymouanonymou";
        notifyUrl = null;
        signType = null;
    }

    @Test
    public void testBannerList() throws Exception {
        service = "bannerList";
        BannerListRequest request = new BannerListRequest();
        request.setDeviceId("11111111");
        request.setRequestNo(UUID.randomUUID().toString());
        request.setAppVersion("1.0");
        BannerListResponse bannerListResponse = request(request, BannerListResponse.class);
        log.info("response:{}", bannerListResponse);
    }

    @Test
    public void testLogin() throws Exception {
        service = "login";
        LoginRequest request = new LoginRequest();
        request.setDeviceId("11111111");
        request.setRequestNo(UUID.randomUUID().toString());
        request.setUsername("bohr");
        String password = "bohr";
        request.setPassword(password);
        request.setDeviceType(DeviceType.IPHONE6);
        request.setDeviceModel("xxxxx");
        request.setChannel("Web");
        request.setCustomerIp("218.239.78.70");
        LoginResponse response = request(request, LoginResponse.class);
        log.info("response:{}", response);
    }
}
