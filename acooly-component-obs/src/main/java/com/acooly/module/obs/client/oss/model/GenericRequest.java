package com.acooly.module.obs.client.oss.model;


import lombok.Getter;
import lombok.Setter;

/**
 * A generic request that contains some basic request options, such as
 * bucket name, object key, costom headers, progress listener and so on.
 *
 * @author shuijing
 */
@Getter
@Setter
public class GenericRequest extends WebServiceRequest {

    private String bucketName;
    private String key;


    public GenericRequest() {
    }

    public GenericRequest(String bucketName) {
        this(bucketName, null);
    }

    public GenericRequest(String bucketName, String key) {
        this.bucketName = bucketName;
        this.key = key;
    }

    public GenericRequest withBucketName(String bucketName) {
        setBucketName(bucketName);
        return this;
    }

    public GenericRequest withKey(String key) {
        setKey(key);
        return this;
    }
}
