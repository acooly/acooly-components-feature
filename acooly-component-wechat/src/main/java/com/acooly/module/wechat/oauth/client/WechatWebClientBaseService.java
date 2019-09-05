package com.acooly.module.wechat.oauth.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acooly.module.wechat.oauth.client.dto.WechatOpenIdDto;
import com.acooly.module.wechat.oauth.client.dto.WechatUserInfoDto;

/**
 * 微信网页授权
 * 
 * @author CuiFuQ
 *
 */
public interface WechatWebClientBaseService {

	/**
	 * 第一步：用户同意授权，获取code
	 * 
	 * @param redirectUri 回调地址:微信授权成功后的跳转地址
	 *                    <li>如果 填写为空，采用【acooly.wechat.redirectUri】配置
	 *                    <li>回调地址的域名需要与 [微信公众号平台-->开发-->基本设置-->服务器配置]相同
	 *                    <li>使用方式 "redirect:" + wechatOauthUrl;
	 * 
	 * @return
	 */
	public String wechatOauth(String redirectUri);

	/**
	 * 第二步：通过code换取网页授权access_token
	 * 
	 * 
	 **/
	public WechatOpenIdDto getOpenId(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 获取 access_token;
	 * <li>获取access_token
	 * <li>【https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET】
	 * <li>
	 * <li>access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。开发者需要进行妥善保存。access_token的存储至少要保留512个字符空间。access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
	 * 
	 * @return
	 */
	public String getAccessToken();

	/**
	 * 清除access_token
	 */
	public void cleanAccessToken();

	/**
	 * 刷新 系统缓存 access_token（每日2000次）
	 * 
	 * @return
	 */
	public String refreshAccessToken();

	/**
	 * 检验授权凭证（access_token）是否有效
	 * <li>true 有效
	 * <li>false 失效
	 * 
	 * @param openId
	 * @return
	 */
	public boolean accessTokenCheck(String openId);

	/**
	 * 拉取用户信息(需scope为 snsapi_userinfo)
	 * 
	 * @param openId
	 * @param accessToken 网页授权access_token
	 * @return
	 */
	public WechatUserInfoDto getUserInfoByAccessToken(String openId, String accessToken);

	/**
	 * 获取用户基本信息
	 * <li>access_token："获取access_token”接口来获取到的普通access_token
	 * 
	 * @param openId
	 * @return
	 */
	public WechatUserInfoDto getUserInfoByOpenId(String openId);

}
