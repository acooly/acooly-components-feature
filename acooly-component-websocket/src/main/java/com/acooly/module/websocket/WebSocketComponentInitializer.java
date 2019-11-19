package com.acooly.module.websocket;

import org.springframework.context.ConfigurableApplicationContext;

import com.acooly.core.common.boot.component.ComponentInitializer;

/**
 * @author cuifuqiang
 */
public class WebSocketComponentInitializer implements ComponentInitializer {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		setPropertyIfMissing("acooly.security.csrf.exclusions.websocket", "/websocket/text/*");
		setPropertyIfMissing("acooly.security.xss.exclusions.websocket", "/websocket/text/*");
	}
}
