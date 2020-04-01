package com.acooly.module.obs.client;

import com.acooly.module.obs.ObsProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author shuijing
 */
public abstract class AbstractObsClient implements ObsClient, InitializingBean {

    protected int timeout;

    @Autowired
    private ObsProperties obsProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        timeout = obsProperties.getTimeout();
    }
}
