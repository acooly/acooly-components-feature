package com.acooly.module.config;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.utils.Strings;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * @author shuijing
 */
@Data
@ConfigurationProperties(prefix = ConfigProperties.PREFIX)
public class ConfigProperties {
    public static final String PREFIX = "acooly.config";

    private String configTopic;

    public static String CONFIG_REDIS_TOPIC = Apps.getAppName() + "appConfig";
    public static String CACHE_PREFIX = Apps.getAppName() + ".appConfig.";

    @PostConstruct
    public void init() {
        if (Strings.isNotBlank(configTopic)) {
            CONFIG_REDIS_TOPIC = this.configTopic + "appConfig";
            CACHE_PREFIX = this.configTopic + ".appConfig.";
        }
    }
}
