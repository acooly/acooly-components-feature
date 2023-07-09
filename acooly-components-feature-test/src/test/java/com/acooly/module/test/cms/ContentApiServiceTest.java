package com.acooly.module.test.cms;

import com.acooly.module.cms.openapi.request.ContentInfoRequest;
import com.acooly.module.cms.openapi.request.ContentListRequest;
import com.acooly.module.cms.openapi.response.ContentInfoResponse;
import com.acooly.module.cms.openapi.response.ContentListResponse;
import com.acooly.openapi.framework.core.test.AbstractApiServieTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author zhangpu
 * @date 2019-01-02 15:27
 */
@Slf4j
public class ContentApiServiceTest extends AbstractApiServieTests {
    {
        gatewayUrl = "http://127.0.0.1:8090/gateway.do";
    }

    @Test
    public void testContentInfo() {

        ContentInfoRequest request = new ContentInfoRequest();
        // id方式查询
        request.setKey("7");
        request(request, ContentInfoResponse.class);

        // 编码方式查询
//        request.setContentQueryType(ContentQueryTypeEnum.key);
//        request.setKey("201901010001");
        request(request, ContentInfoResponse.class);
    }


    @Test
    public void testContentList() {

        ContentListRequest request = new ContentListRequest();

        request.setTypeCode("aboutus");
        request.setSearchKey("我们");
        request(request, ContentListResponse.class);
    }


}
