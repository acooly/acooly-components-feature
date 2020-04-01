package com.acooly.module.obs.client.oss.model;

import com.acooly.module.obs.common.model.ObjectMetadata;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.InputStream;

/**
 * @author shuijing
 */
@Getter
@Setter
public class PutObjectRequest extends GenericRequest {

    private File file;
    private InputStream inputStream;

    private ObjectMetadata metadata;

    // private Callback callback;
    private String process;

    public PutObjectRequest(String bucketName, String key, File file) {
        this(bucketName, key, file, null);
    }

    public PutObjectRequest(String bucketName, String key, File file, ObjectMetadata metadata) {
        super(bucketName, key);
        this.file = file;
        this.metadata = metadata;
    }

    public PutObjectRequest(String bucketName, String key, InputStream input) {
        this(bucketName, key, input, null);
    }

    public PutObjectRequest(
            String bucketName, String key, InputStream input, ObjectMetadata metadata) {
        super(bucketName, key);
        this.inputStream = input;
        this.metadata = metadata;
    }
}
