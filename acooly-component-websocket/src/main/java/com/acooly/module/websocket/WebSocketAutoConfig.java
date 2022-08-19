package com.acooly.module.websocket;

import static com.acooly.module.websocket.WebSocketProperties.PREFIX;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.acooly.core.utils.Strings;
import com.acooly.module.security.config.SecurityAutoConfig;
import com.acooly.module.websocket.listener.WebSocketRedisListenerHandler;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cuifuqiang
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({ WebSocketProperties.class })
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.module.websocket")
@AutoConfigureAfter(SecurityAutoConfig.class)

public class WebSocketAutoConfig {

	@Autowired
	private WebSocketProperties webSocketProperties;

	@Autowired
	private WebSocketRedisListenerHandler webSocketRedisListenerHandler;

	@Bean
	public StandardDatabaseScriptIniter webSocketScriptIniter() {
		log.info("加载[acooly-component-websocket]组件。。。。。。");

		return new StandardDatabaseScriptIniter() {

			@Override
			public String getEvaluateTable() {
				return "websocket";
			}

			@Override
			public String getComponentName() {
				return "websocket";
			}

			@Override
			public List<String> getInitSqlFile() {
				return Lists.newArrayList();
			}
		};
	}

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

	@Bean
	public RedisMessageListenerContainer webSocketRedisMessageListenerContainer(
			RedisConnectionFactory connectionFactory, Environment environment) {
		String subscribe = webSocketProperties.getSubscribeKey();
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		String redisDataBase = environment.getProperty("spring.redis.database");
		if (Strings.isBlank(redisDataBase)) {
			redisDataBase = "0";
		}
		log.info("[WebSocket组件]:初始化RedisMessageListenerContainer监听事件,spring.redis.database:{},订阅渠道:{}", redisDataBase,
				subscribe);
		container.addMessageListener(webSocketRedisListenerHandler, new PatternTopic(subscribe));
		return container;
	}

}