package com.acooly.module.chat;

import org.springframework.context.ConfigurableApplicationContext;

import com.acooly.core.common.boot.component.ComponentInitializer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cuifuq
 */
@Slf4j
public class ChatComponentInitializer implements ComponentInitializer {
	
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		log.info("加载组件-[acooly-component-chat]");
        setPropertyIfMissing("acooly.security.xss.exclusions.im.chat[0]", "/portal/chat/im/**");
        setPropertyIfMissing("acooly.security.csrf.exclusions.im.chat", "/portal/chat/im/**");
	}
}
