/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-30 21:56
 */
package com.acooly.module.test.smsend;

import com.acooly.module.smsend.facade.openapi.SmsSendApiRequest;
import com.acooly.module.smsend.facade.openapi.SmsSendApiResponse;
import com.acooly.openapi.framework.common.utils.json.JsonMarshallor;
import com.acooly.openapi.framework.core.test.AbstractApiServieTests;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author zhangpu
 * @date 2020-05-30 21:56
 */
@Slf4j
public class SmsSendOpenApiTest extends AbstractApiServieTests {
    {
        gatewayUrl = "http://127.0.0.1:8090/gateway.do";
    }

    @Test
    public void smsSend() {
//        Map<String, String> params = Maps.newHashMap();
//        params.put("code", "112233");
//        params.put("expire", "10");
//        SmsSendApiRequest request = new SmsSendApiRequest("youcheyun", "13896177630", "youcheyun_register",
//                params, null, "192.168.0.1");

        SmsSendApiRequest request = SmsSendApiRequest.newRequest().appId("youcheyun").addMobileNo("13896177630").templateCode("youcheyun_register")
                .addTemplateParam("code", "112233").addTemplateParam("expire", "10")
                .clientIp("192.168.0.1");

        SmsSendApiResponse response = request(request, SmsSendApiResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isTrue();
        log.info("[通过] 测试：标准同步接口：smsSend ");
    }
}
