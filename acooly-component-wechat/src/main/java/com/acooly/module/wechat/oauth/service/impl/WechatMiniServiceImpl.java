package com.acooly.module.wechat.oauth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.module.wechat.oauth.mini.WechatMiniClientService;
import com.acooly.module.wechat.oauth.mini.dto.WechatMiniSession;
import com.acooly.module.wechat.oauth.service.WechatMiniService;

@Service("wechatMiniService")
public class WechatMiniServiceImpl implements WechatMiniService {

	@Autowired
	private WechatMiniClientService wechatMiniClientService;

	@Override
	public WechatMiniSession loginAuthVerify(String jsCode) {
		return wechatMiniClientService.loginAuthVerify(jsCode);
	}

	@Override
	public String getMiniProgramImgCode(String scene, String page) {
		return wechatMiniClientService.getMiniProgramImgCode(scene, page);
	}

}
