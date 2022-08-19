package com.acooly.module.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;

import com.acooly.core.common.boot.component.ComponentInitializer;

/**
 * @author cuifuqiang
 */
@Slf4j
public class WebSocketComponentInitializer implements ComponentInitializer {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		log.info("");
		setPropertyIfMissing("acooly.security.csrf.exclusions.websocket", "/websocket/text/*");
		setPropertyIfMissing("acooly.security.xss.exclusions.websocket", "/websocket/text/*");
	}
}
