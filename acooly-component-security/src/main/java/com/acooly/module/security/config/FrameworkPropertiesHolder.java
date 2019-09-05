package com.acooly.module.security.config;

import com.acooly.core.common.boot.EnvironmentHolder;

/**
 * @author qiubo
 */
public class FrameworkPropertiesHolder {

    private static volatile FrameworkProperties config = null;

    @SuppressWarnings("unchecked")
    public static FrameworkProperties get() {
        if (config == null) {
            synchronized (FrameworkPropertiesHolder.class) {
                if (config == null) {
                    config = new FrameworkProperties();
                    EnvironmentHolder.buildProperties(config);
                }
            }
        }
        return config;
    }
}
