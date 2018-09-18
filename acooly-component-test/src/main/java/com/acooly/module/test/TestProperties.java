package com.acooly.module.test;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author qiubo@yiji.com
 */
@Data
@ConfigurationProperties(TestProperties.PREFIX)
public class TestProperties {
    public static final String PREFIX = "acooly.test";

    /**
     * 懒加载bean会加快启动时间，但是可能会遇到其他问题，默认开启
     */
    private boolean lazyInitBean = true;
}
