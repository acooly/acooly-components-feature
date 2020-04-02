package com.acooly.module.obs.client.oss;

import com.acooly.module.obs.client.oss.model.WebServiceRequest;
import com.acooly.module.obs.common.HttpMethod;
import com.acooly.module.obs.common.util.DateUtil;
import com.acooly.module.obs.common.util.OSSUtils;

import java.io.InputStream;
import java.net.URI;
import java.util.*;

import static com.acooly.module.obs.common.util.OSSUtils.determineFinalEndpoint;

/**
 * HTTP request message builder.
 *
 * @author shuijing
 */
public class OSSRequestMessageBuilder {

    private URI endpoint;
    private HttpMethod method = HttpMethod.GET;
    private String bucket;
    private String key;
    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> parameters = new LinkedHashMap<String, String>();
    private InputStream inputStream;
    private long inputSize = 0;
    // private ServiceClient innerClient;
    private boolean useChunkEncoding = false;

    private WebServiceRequest originalRequest;

    //      public OSSRequestMessageBuilder(ServiceClient innerClient) {
    //          this.innerClient = innerClient;
    //      }

    public OSSRequestMessageBuilder() {
    }

    public URI getEndpoint() {
        return endpoint;
    }

    public OSSRequestMessageBuilder setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public OSSRequestMessageBuilder setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public String getBucket() {
        return bucket;
    }

    public OSSRequestMessageBuilder setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public String getKey() {
        return key;
    }

    public OSSRequestMessageBuilder setKey(String key) {
        this.key = key;
        return this;
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public OSSRequestMessageBuilder setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public OSSRequestMessageBuilder addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    public OSSRequestMessageBuilder setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }

    public OSSRequestMessageBuilder addParameter(String key, String value) {
        parameters.put(key, value);
        return this;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OSSRequestMessageBuilder setInputStream(InputStream instream) {
        this.inputStream = instream;
        return this;
    }

    //      public OSSRequestMessageBuilder setInputStreamWithLength(FixedLengthInputStream instream) {
    //         // assertParameterInRange(inputSize, -1, DEFAULT_FILE_SIZE_LIMIT);
    //          this.inputStream = instream;
    //          this.inputSize = instream.getLength();
    //          return this;
    //      }

    public long getInputSize() {
        return inputSize;
    }

    public OSSRequestMessageBuilder setInputSize(long inputSize) {
        //assertParameterInRange(inputSize, -1, DEFAULT_FILE_SIZE_LIMIT);
        this.inputSize = inputSize;
        return this;
    }

    public boolean isUseChunkEncoding() {
        return useChunkEncoding;
    }

    public OSSRequestMessageBuilder setUseChunkEncoding(boolean useChunkEncoding) {
        this.useChunkEncoding = useChunkEncoding;
        return this;
    }

    public OSSRequestMessageBuilder setOriginalRequest(WebServiceRequest originalRequest) {
        this.originalRequest = originalRequest;
        return this;
    }

    public RequestMessage build() {
        Map<String, String> sentHeaders = new HashMap<String, String>(this.headers);
        sentHeaders.put(OSSHeaders.DATE, DateUtil.formatRfc822Date(new Date()));
        Map<String, String> sentParameters = new LinkedHashMap<String, String>(this.parameters);

        RequestMessage request = new RequestMessage(this.originalRequest);
        request.setEndpoint(determineFinalEndpoint(this.endpoint, this.bucket));
        request.setResourcePath(OSSUtils.urlEncodeKey(key));
        request.setHeaders(sentHeaders);
        request.setParameters(sentParameters);
        request.setMethod(this.method);
        request.setContent(this.inputStream);
        request.setContentLength(this.inputSize);
        request.setUseChunkEncoding(this.inputSize == -1 ? true : this.useChunkEncoding);

        return request;
    }
}
