package com.acooly.module.wechat.oauth.service;

import com.acooly.module.wechat.oauth.mini.dto.WechatMiniSession;

public interface WechatMiniService {

	/**
	 * 登录凭证校验
	 * 
	 * @param jsCode
	 *               <li>调用接口获取登录凭证（code）
	 *               <li>详情参考 wx.login(Object object)
	 * 
	 * @return
	 */
	public WechatMiniSession loginAuthVerify(String jsCode);

	/**
	 * 获取小程序码，适用于需要的码数量极多的业务场景。
	 * 
	 * 
	 * @return
	 */
	public String getMiniProgramImgCode(String scene, String page);
}
