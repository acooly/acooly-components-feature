package com.acooly.module.obs.client.oss;

import com.acooly.module.obs.common.HttpMesssage;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

@Getter
@Setter
public class ResponseMessage extends HttpMesssage {

    public static final int HTTP_SUCCESS_STATUS_CODE = 200;

    private String uri;
    private int statusCode;
    private String buketName;
    //返回的result
    private String result;

    //private ServiceClient.Request request;
    private CloseableHttpResponse httpResponse;

    // For convenience of logging invalid response
    private String errorResponseAsString;

    //  public ResponseMessage(ServiceClient.Request request) {
    //    this.request = request;
    //  }

    public String getRequestId() {
        return getHeaders().get(OSSHeaders.OSS_HEADER_REQUEST_ID);
    }

    public void abort() throws IOException {
        if (httpResponse != null) {
            httpResponse.close();
        }
    }
}
