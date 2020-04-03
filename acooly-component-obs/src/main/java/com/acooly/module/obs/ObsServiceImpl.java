package com.acooly.module.obs;

import com.acooly.module.obs.client.ObsClient;
import com.acooly.module.obs.common.OssFile;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

/**
 * @author shuijing
 */
@Service("ObsService")
public class ObsServiceImpl implements InitializingBean, ObsService {

    @Autowired
    private ObsClient obsClient;

    @Override
    public OssFile putObject(String bucketName, String key, File file) {
        return obsClient.putObject(bucketName, key, file);
    }

    @Override
    public OssFile putObject(String bucketName, String key, InputStream inputStream) {
        return obsClient.putObject(bucketName, key, inputStream);
    }

    @Override
    public String getAccessUrlBySts(String bucketName, String key, String processStyle, Date expireDate) {
        return obsClient.getAccessUrlBySts(bucketName, key, processStyle, expireDate);
    }

    @Override
    public String getUrlByKey(String key) {
        return obsClient.getUrlByKey(key);
    }

    @Override
    public OssFile putObject(String bucketName, String key, File file, ObjectMetadata metadata) {
        return obsClient.putObject(bucketName, key, file, metadata);
    }

    @Override
    public OssFile putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata) {
        return obsClient.putObject(bucketName, key, input, metadata);
    }

    @Override
    public OssFile getObject(String bucketName, String key) {
        return obsClient.getObject(bucketName, key);
    }

    @Override
    public void deleteObject(String bucketName, String key) {
        obsClient.deleteObject(bucketName, key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
