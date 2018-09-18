package com.acooly.module.config;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.acooly.module.config.service.impl.AppConfigManager;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

/**
 * @author qiubo
 */
@Configuration
@ComponentScan(basePackages = "com.acooly.module.config")
@Slf4j
public class AppConfigAutoConfig {
    public static final String CONFIG_REDIS_TOPIC = Apps.getAppName() + "appConfig";
    public static final String CACHE_PREFIX = Apps.getAppName() + ".appConfig.";

    @Bean
    public StandardDatabaseScriptIniter appConfigScriptIniter() {
        return new StandardDatabaseScriptIniter() {
            @Override
            public String getEvaluateTable() {
                return "sys_app_config";
            }

            @Override
            public String getComponentName() {
                return "config";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("config", "config_urls");
            }
        };
    }

    @Bean
    public RedisMessageListenerContainer configRedisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                             @Qualifier("configlistenerAdapter") MessageListenerAdapter listenerAdapter, ThreadPoolTaskExecutor commonTaskExecutor) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setTaskExecutor(commonTaskExecutor);
        container.addMessageListener(listenerAdapter, new PatternTopic(CONFIG_REDIS_TOPIC));
        return container;
    }

    @Bean
    public MessageListenerAdapter configlistenerAdapter(RedisTemplate redisTemplate, AppConfigManager appConfigManager) {
        return new MessageListenerAdapter((MessageListener) (message, pattern) -> {
            String key = (String) redisTemplate.getValueSerializer().deserialize(message.getBody());
            log.info("配置管理[key={}]更新", key.replace(CACHE_PREFIX, ""));
            appConfigManager.invalidate(key);
        });
    }

}
