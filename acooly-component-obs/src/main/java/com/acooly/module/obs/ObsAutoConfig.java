package com.acooly.module.obs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.acooly.module.obs.ObsProperties.PREFIX;

/**
 * @author shuijing
 */
@Configuration
@EnableConfigurationProperties({ObsProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.module.obs")
public class ObsAutoConfig {
}
