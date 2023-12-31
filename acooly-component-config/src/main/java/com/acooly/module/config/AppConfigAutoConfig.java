package com.acooly.module.config;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.acooly.module.config.service.impl.AppConfigManager;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
@EnableConfigurationProperties({ConfigProperties.class})
@ComponentScan(basePackages = "com.acooly.module.config")
@Slf4j
public class AppConfigAutoConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
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
    public RedisMessageListenerContainer configRedisMessageListenerContainer(RedisConnectionFactory connectionFactory, @Qualifier("configlistenerAdapter") MessageListenerAdapter listenerAdapter,
                                                                             ThreadPoolTaskExecutor commonTaskExecutor) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setTaskExecutor(commonTaskExecutor);
        container.addMessageListener(listenerAdapter, new PatternTopic(ConfigProperties.CONFIG_REDIS_TOPIC));
        return container;
    }

    @Bean
    public MessageListenerAdapter configlistenerAdapter(RedisTemplate redisTemplate, AppConfigManager appConfigManager, ConfigProperties properties) {
        return new MessageListenerAdapter((MessageListener) (message, pattern) -> {
            String key = (String) redisTemplate.getValueSerializer().deserialize(message.getBody());
            log.info("配置管理[key={}]更新", key.replace(ConfigProperties.CACHE_PREFIX, ""));
            // 监听到redis更新时，仅删除当前本地缓存，不再删除redis缓存 by xiyang 2022-11-09
            appConfigManager.invalidateLocal(key);
        });
    }

}
