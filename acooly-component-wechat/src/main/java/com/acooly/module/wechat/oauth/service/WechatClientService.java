package com.acooly.module.wechat.oauth.service;

import com.acooly.module.wechat.oauth.client.dto.WechatUserInfoDto;

public interface WechatClientService {

	/**
	 * 非用户授权 access_token
	 * 
	 * access_token是公众号的全局唯一接口调用凭据； 公众号调用各接口时都需使用access_token
	 * 
	 * @return
	 */
	public String getAccessToken();

	/**
	 * 清除系统缓存 access_token
	 */
	public void cleanAccessToken();

	/**
	 * 刷新 系统缓存 access_token（每日2000次）
	 */
	public String refreshAccessToken();

	/**
	 * 是否已经关注公众号
	 * 
	 * <li>true 已关注
	 * <li>false 未关注
	 * 
	 * @param openId
	 * @return
	 */
	public boolean isSubscribe(String openId);

	/**
	 * 获取用户用户基本信息
	 * <li>已经关注了公众号，并且已经授权
	 * 
	 * @param openId
	 * @return
	 */
	public WechatUserInfoDto getUserInfoBySubscribe(String openId);

	/**
	 * 获取用户用户基本信息
	 * <li>未关注了公众号，已经授权过
	 * 
	 * @param openId
	 * @return
	 */
	public WechatUserInfoDto getUserInfoByOpenId(String openId);

}
