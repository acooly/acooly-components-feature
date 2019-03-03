package com.acooly.module.eav;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.dao.dialect.DatabaseType;
import com.acooly.core.common.dao.support.AbstractDatabaseScriptIniter;
import com.acooly.module.eav.service.impl.EavEntityService;
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
 * @author qiuboboy@qq.com
 * @date 2018-06-26 20:48
 */
// todo: 待扩展客户端事件，包括：字段的事件（onChange等）和表单事件
// todo: 待扩展服务器端事件
@Configuration
@ComponentScan(basePackages = "com.acooly.module.eav")
@Slf4j
public class EavAutoConfig {
    public static final String EAV_REDIS_TOPIC = Apps.getAppName() + ".eavTopic";

    @Bean
    public RedisMessageListenerContainer eavRedisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                          ThreadPoolTaskExecutor commonTaskExecutor, @Qualifier("eavlistenerAdapter") MessageListenerAdapter eavlistenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setTaskExecutor(commonTaskExecutor);
        container.addMessageListener(eavlistenerAdapter, new PatternTopic(EAV_REDIS_TOPIC));
        return container;
    }

    @Bean
    public MessageListenerAdapter eavlistenerAdapter(RedisTemplate redisTemplate, EavEntityService eavEntityService) {
        return new MessageListenerAdapter((MessageListener) (message, pattern) -> {
            String key = (String) redisTemplate.getValueSerializer().deserialize(message.getBody());
            log.info("eav[key={}]更新", key);
            eavEntityService.invalidateCache(key);
        });
    }

    @Bean
    public AbstractDatabaseScriptIniter eavScriptIniter() {
        return new AbstractDatabaseScriptIniter() {
            @Override
            public String getEvaluateSql(DatabaseType databaseType) {
                return "SELECT count(*) FROM eav_entity";
            }

            @Override
            public List<String> getInitSqlFile(DatabaseType databaseType) {
                if (databaseType == DatabaseType.mysql) {
                    return Lists.newArrayList(
                            "META-INF/database/eav/mysql/eav.sql",
                            "META-INF/database/eav/mysql/eav_urls.sql");
                } else {
                    throw new UnsupportedOperationException("还不支持oracle");
                }
            }
        };
    }
}
