package com.acooly.module.test;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author qiubo@yiji.com
 */
@TestConfiguration
public class TestAutoConfig {
    @ConditionalOnProperty(value = "acooly.test.lazyInitBean", matchIfMissing = true)
    @Bean
    public static BeanFactoryPostProcessor lazyBeanPostProcessor() {
        return new LazyInitBeanFactoryPostProcessor();
    }
}
