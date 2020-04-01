package com.acooly.module.obs.common.model;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * 表示obs中的Object。 在 obs 中，用户的每个文件都是一个 Object Object 包含key、data和user meta。其中，key是Object 的名字；
 * data是Object 的数据；user meta是用户对该object的描述。
 */
public class ObsObject implements Closeable {

    // Object key (name)
    private String key;

    // Object所在的Bucket的名称。
    private String bucketName;

    // Object的元数据。
    private ObjectMetadata metadata = new ObjectMetadata();

    // Object所包含的内容，需要调用者主动关闭！
    private InputStream objectContent;

    /**
     * 返回Object的元数据。
     *
     * @return Object的元数据（{@link ObjectMetadata}）。
     */
    public ObjectMetadata getObjectMetadata() {
        return metadata;
    }

    /**
     * 设置Object的元数据。
     *
     * @param metadata Object的元数据（{@link ObjectMetadata}）。
     */
    public void setObjectMetadata(ObjectMetadata metadata) {
        this.metadata = metadata;
    }

    /**
     * 返回包含Object内容的{@link InputStream}。
     *
     * @return 包含Object内容的{@link InputStream}。
     */
    public InputStream getObjectContent() {
        return objectContent;
    }

    /**
     * 设置包含Object内容的{@link InputStream}。
     *
     * @param objectContent 包含Object内容的{@link InputStream}。
     */
    public void setObjectContent(InputStream objectContent) {
        this.objectContent = objectContent;
    }

    /**
     * 获取Object所在的Bucket的名称。
     *
     * @return Object所在的Bucket的名称。
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * 设置Object所在的Bucket的名称。
     *
     * @param bucketName Object所在的Bucket的名称。
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * 获取Object的Key。
     *
     * @return Object Key。
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置Object的Key。
     *
     * @param key Object Key。
     */
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void close() throws IOException {
        if (objectContent != null) {
            objectContent.close();
        }
    }

    @Override
    public String toString() {
        return "ObsObject [key="
                + getKey()
                + ",bucket="
                + (bucketName == null ? "<Unknown>" : bucketName)
                + "]";
    }
}
