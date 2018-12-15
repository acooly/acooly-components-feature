package com.acooly.core.test.utils;

import com.acooly.module.appopenapi.message.BannerListRequest;
import com.acooly.openapi.framework.common.ApiConstants;
import com.acooly.openapi.framework.common.enums.SignTypeEnum;
import com.acooly.openapi.framework.common.message.ApiResponse;
import com.acooly.openapi.framework.common.utils.json.JsonMarshallor;
import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static com.acooly.openapi.framework.common.ApiConstants.TEST_ACCESS_KEY;
import static com.acooly.openapi.framework.common.ApiConstants.TEST_SECRET_KEY;

/**
 * @author qiuboboy@qq.com
 * @date 2018-08-24 16:37
 */
public class RTest {
    protected static String accessKey = TEST_ACCESS_KEY;
    protected static String secretKey = TEST_SECRET_KEY;

    @Test
    public void name() {
        ApiRestTemplate restTemplate = new ApiRestTemplate("http://192.168.55.31:8081/gateway.do", accessKey, secretKey);
        BannerListRequest request = new BannerListRequest();
        request.setRequestNo(UUID.randomUUID().toString());
        request.setPartnerId("test");
        request.setService("bannerList");
        request.setVersion("1.0");
        request.setDeviceId("111111111");
        request.setAppVersion("dfdf");
        ResponseEntity<ApiResponse> responseEntity = restTemplate.send(request, ApiResponse.class);
        System.out.println(responseEntity);
    }


    public static class ApiClientHttpMessageConverter extends AbstractHttpMessageConverter {
        private String accessKey;
        private String secretKey;

        public ApiClientHttpMessageConverter(String accessKey, String secretKey) {
            this.accessKey = accessKey;
            this.secretKey = secretKey;
        }

        @Override
        protected boolean supports(Class clazz) {
            return true;
        }

        @Override
        public boolean canRead(MediaType mediaType) {
            return true;
        }

        @Override
        protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
            InputStream inputStream = inputMessage.getBody();
            return JsonMarshallor.INSTANCE.parse(new String(ByteStreams.toByteArray(inputStream)), clazz);
        }

        @Override
        protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
            String body = JsonMarshallor.INSTANCE.marshall(o);
            outputMessage.getHeaders().add(ApiConstants.ACCESS_KEY, accessKey);
            outputMessage.getHeaders().add(ApiConstants.SIGN_TYPE, SignTypeEnum.MD5.toString());
            outputMessage.getHeaders().add(ApiConstants.SIGN, sign(body));
            StreamUtils.copy(body, Charsets.UTF_8, outputMessage.getBody());
        }

        public String sign(String body) {
            return DigestUtils.md5Hex(body + secretKey);
        }
    }

    public static class ApiRestTemplate extends RestTemplate {
        private String accessKey;
        private String secretKey;
        private String gatewayUrl;

        public ApiRestTemplate(String gatewayUrl, String accessKey, String secretKey) {
            super(Lists.newArrayList(new ApiClientHttpMessageConverter(accessKey, secretKey)));
            this.accessKey = accessKey;
            this.secretKey = secretKey;
            this.gatewayUrl = gatewayUrl;
        }

        public <T> ResponseEntity<T> send(Object request, Class<T> responseType)
                throws RestClientException {
            RequestCallback requestCallback = httpEntityCallback(request, responseType);
            ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(responseType);
            return execute(gatewayUrl, HttpMethod.POST, requestCallback, responseExtractor);
        }

    }
}
