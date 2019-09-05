package com.acooly.module.wechat.oauth.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acooly.module.wechat.oauth.client.dto.WechatUserInfoDto;

/**
 * 微信网页授权
 * 
 * @author CuiFuQ
 *
 */
public interface WechatWebClientService {

	/**
	 * 微信页面确认授权(step 1)
	 * 
	 * @return
	 */
	public String getWechatOauthUrl(String redirectUri);

	/**
	 * 微信页面确认授权(step 2)
	 * 
	 * @return
	 */
	public WechatUserInfoDto getWechatUserInfo(HttpServletRequest request, HttpServletResponse response);


}
