package com.acooly.module.obs.client;

import com.acooly.module.obs.ObsProperties;
import com.acooly.module.obs.common.OssFile;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

/**
 * @author shuijing
 */
@Service("obsClient")
public class ObsClientProxy
        implements ObsClient, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    private Object object = new Object();

    private ApplicationContext applicationContext;

    private ObsClient obsClient;

    @Autowired
    private ObsProperties obsProperties;

    @Override
    public String getProvider() {
        return obsClient.getProvider();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public OssFile putObject(String bucketName, String key, File file) {
        return obsClient.putObject(bucketName, key, file);
    }

    @Override
    public OssFile putObject(String bucketName, String key, InputStream input) {
        return obsClient.putObject(bucketName, key, input);
    }

    @Override
    public String getUrlByKey(String key) {
        return obsClient.getUrlByKey(key);
    }

    @Override
    public String getAccessUrlBySts(String bucketName, String key, String processStyle, Date expireDate) {
        return obsClient.getAccessUrlBySts(bucketName, key, processStyle, expireDate);
    }

    @Override
    public OssFile putObject(String bucketName, String key, File file, ObjectMetadata metadata) {
        return obsClient.putObject(bucketName, key, file, metadata);
    }

    @Override
    public OssFile putObject(
            String bucketName, String key, InputStream input, ObjectMetadata metadata) {
        return obsClient.putObject(bucketName, key, input, metadata);
    }

    @Override
    public OssFile getObject(String bucketName, String key, String processStyle) {
        return obsClient.getObject(bucketName, key, processStyle);
    }

    @Override
    public void deleteObject(String bucketName, String key) {
        obsClient.deleteObject(bucketName, key);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (obsClient == null) {
            synchronized (object) {
                if (obsClient == null && obsProperties.isEnable()) {
                    obsClient = (ObsClient) this.applicationContext.getBean(obsProperties.getProvider().code());
                }
            }
        }
    }
}
