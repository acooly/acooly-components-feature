package com.acooly.module.wechat;

import org.springframework.context.ConfigurableApplicationContext;

import com.acooly.core.common.boot.component.ComponentInitializer;

/**
 * @author cuifuqiang
 */
public class WechatComponentInitializer implements ComponentInitializer {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		setPropertyIfMissing("acooly.security.csrf.exclusions.wechatWeb", "/wechat/webApi/*.html");
		setPropertyIfMissing("acooly.security.xss.exclusions.wechatWeb", "/wechat/webApi/*.html");
		
		setPropertyIfMissing("acooly.security.csrf.exclusions.wechatWebLogin", "/wechat/webLogin/api/*.html");
		setPropertyIfMissing("acooly.security.xss.exclusions.wechatWebLogin", "/wechat/webLogin/api/*.html");
		
	}
}
