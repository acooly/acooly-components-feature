package com.acooly.module.obs.client.oss;

import com.acooly.module.obs.client.oss.model.WebServiceRequest;
import com.acooly.module.obs.common.HttpMesssage;
import com.acooly.module.obs.common.HttpMethod;

import java.net.URI;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represent HTTP requests sent to OSS.
 */
public class RequestMessage extends HttpMesssage {

    /* The original request provided by user */
    private final WebServiceRequest originalRequest;
    /* The resource path being requested */
    private String resourcePath;
    /* The service endpoint to which this request should be sent */
    private URI endpoint;
    /* The HTTP method to use when sending this request */
    private HttpMethod method = HttpMethod.GET;
    /* Use a LinkedHashMap to preserve the insertion order. */
    private Map<String, String> parameters = new LinkedHashMap<String, String>();
    /* The absolute url to which the request should be sent */
    private URL absoluteUrl;
    /* Indicate whether using url signature */
    private boolean useUrlSignature = false;
    /* Indicate whether using chunked encoding */
    private boolean useChunkEncoding = false;

    public RequestMessage() {
        this(null);
    }

    public RequestMessage(WebServiceRequest originalRequest) {
        this.originalRequest = (originalRequest == null) ? WebServiceRequest.NOOP : originalRequest;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public URI getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters.clear();
        if (parameters != null && !parameters.isEmpty()) {
            this.parameters.putAll(parameters);
        }
    }

    public void addParameter(String key, String value) {
        this.parameters.put(key, value);
    }

    public void removeParameter(String key) {
        this.parameters.remove(key);
    }

    /**
     * Indicate whether the request should be repeatedly sent.
     */
    public boolean isRepeatable() {
        return this.getContent() == null || this.getContent().markSupported();
    }

    public String toString() {
        return "Endpoint: "
                + this.getEndpoint().getHost()
                + ", ResourcePath: "
                + this.getResourcePath()
                + ", Headers:"
                + this.getHeaders();
    }

    public URL getAbsoluteUrl() {
        return absoluteUrl;
    }

    public void setAbsoluteUrl(URL absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }

    public boolean isUseUrlSignature() {
        return useUrlSignature;
    }

    public void setUseUrlSignature(boolean useUrlSignature) {
        this.useUrlSignature = useUrlSignature;
    }

    public boolean isUseChunkEncoding() {
        return useChunkEncoding;
    }

    public void setUseChunkEncoding(boolean useChunkEncoding) {
        this.useChunkEncoding = useChunkEncoding;
    }

    //    public WebServiceRequest getOriginalRequest() {
    //        return originalRequest;
    //    }

}
