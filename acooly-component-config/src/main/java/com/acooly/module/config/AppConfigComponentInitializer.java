
package com.acooly.module.config;

import com.acooly.core.common.boot.component.ComponentInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author qiubo
 */
public class AppConfigComponentInitializer implements ComponentInitializer {

    private static final Logger logger = LoggerFactory.getLogger(AppConfigComponentInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
    }
}
