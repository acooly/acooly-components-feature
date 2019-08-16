/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-16 17:04 创建
 */
package com.acooly.module.ofile;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.boot.component.ComponentInitializer;
import com.acooly.core.common.exception.AppConfigException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.IOException;

/**
 * @author qiubo@yiji.com
 */
public class OFileComponentInitializer implements ComponentInitializer {
    private static final Logger logger = LoggerFactory.getLogger(OFileComponentInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        OFileProperties oFileProperties = Apps.buildProperties(OFileProperties.class);
        File file = new File(oFileProperties.getStorageRoot());
        if (!file.exists()) {
            if (Apps.isDevMode()) {
                try {
                    FileUtils.forceMkdir(file);
                    logger.info("开发模式下创建ofile存储路径:{}", file.getAbsolutePath());
                } catch (IOException e) {
                    throw new AppConfigException(e);
                }
            } else {
                logger.error("ofile存储路径不存在，请配置nfs路径[{}]映射", file.getAbsolutePath());
                Apps.shutdown();
            }
        }
    }
}
