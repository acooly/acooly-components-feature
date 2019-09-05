package com.acooly.module.wechat;

import org.springframework.context.ConfigurableApplicationContext;

import com.acooly.core.common.boot.component.ComponentInitializer;

/**
 * @author cuifuqiang
 */
public class WechatComponentInitializer implements ComponentInitializer {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		setPropertyIfMissing("acooly.security.csrf.exclusions.wechat", "/wechat/webApi/*.html");
		setPropertyIfMissing("acooly.security.xss.exclusions.wechat", "/wechat/webApi/*.html");
	}
}
