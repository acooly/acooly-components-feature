package com.acooly.module.wechat.oauth.login.impl;

import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.module.wechat.WechatProperties;
import com.acooly.module.wechat.oauth.client.enums.WechatWebClientEnum;
import com.acooly.module.wechat.oauth.login.WechatWebLoginClientService;
import com.acooly.module.wechat.oauth.login.dto.WechatWebLoginInfoDto;
import com.acooly.module.wechat.oauth.login.enums.WechatWebLoginClientEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.esotericsoftware.minlog.Log;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 网站应用微信登录开发
 * 
 * @author CuiFuQ
 *
 */
@Slf4j
@Service("wechatWebLoginClientService")
public class WechatWebLoginClientServiceImpl implements WechatWebLoginClientService {

	@Autowired
	private WechatProperties wechatProperties;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	private final String WECHAT_WEB_LOGIN_ACCESS_TOKEN = "wechat_web_login_access_token";

	@Override
	public String wechatWebLoginOauth(String redirectUri) {
		StringBuffer wechatUrl = new StringBuffer();
		try {
			wechatUrl.append(wechatProperties.getWebLoginClient().getOauthUrl());
			wechatUrl.append("?appid=");
			wechatUrl.append(wechatProperties.getWebLoginClient().getAppid());
			wechatUrl.append("&redirect_uri=");
			if (StringUtils.isNotBlank(redirectUri)) {
				wechatUrl.append(redirectUri);
			} else {
				wechatUrl.append(URLEncoder.encode(wechatProperties.getWebLoginClient().getRedirectUri(), "utf-8"));
			}
			wechatUrl.append("&response_type=");
			wechatUrl.append(wechatProperties.getWebLoginClient().getResponseType());
			wechatUrl.append("&scope=");
			wechatUrl.append(wechatProperties.getWebLoginClient().getScope());
			wechatUrl.append("&state=");
			wechatUrl.append(wechatProperties.getWebLoginClient().getState());
			wechatUrl.append("#wechat_redirect");
		} catch (Exception e) {
			Log.info("微信组件开关：已关闭");
			return wechatProperties.getWebLoginClient().getRedirectUri();
		}
		log.info("访问微信网页授权登录地址:{}", wechatUrl);
		return wechatUrl.toString();
	}

	@Override
	public WechatWebLoginInfoDto getOpenIdAndUnionid(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");

		String code = request.getParameter("code");
		String state = request.getParameter("state");

		log.info("微信公众号页面跳转授权响应参数code:{},state:{}", code, state);

		String openidUrl = wechatProperties.getWebLoginClient().getApiUrl()
				+ WechatWebClientEnum.sns_oauth2_access_token.code();

		Map<String, Object> requestData = Maps.newTreeMap();
		requestData.put("appid", wechatProperties.getWebLoginClient().getAppid());
		requestData.put("secret", wechatProperties.getWebLoginClient().getSecret());
		requestData.put("code", code);
		requestData.put("grant_type", "authorization_code");

		String requestUrl = HttpRequest.append(openidUrl, requestData);
		HttpRequest httpRequest = HttpRequest.get(requestUrl).acceptCharset(HttpRequest.CHARSET_UTF8);
		httpRequest.trustAllCerts();
		httpRequest.trustAllHosts();
		int httpCode = httpRequest.code();
		String resultBody = httpRequest.body(HttpRequest.CHARSET_UTF8);

		JSONObject obj = JSON.parseObject(resultBody);

		if (httpCode != 200) {
			log.info("微信网页授权登录获取授权失败" + obj.get("errmsg"));
			throw new BusinessException(obj.getString("errmsg"), obj.getString("errcode"));
		}
		if (null != obj.get("errcode")) {
			log.info("微信网页授权登录获取授权失败" + obj.get("errmsg"));
			throw new BusinessException(obj.getString("errmsg"), obj.getString("errcode"));
		}

		JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
		WechatWebLoginInfoDto dto = jsonMapper.fromJson(resultBody, WechatWebLoginInfoDto.class);
		return dto;
	}

	public String getAccessToken(String code) {
		String accessToken = (String) redisTemplate.opsForValue().get(WECHAT_WEB_LOGIN_ACCESS_TOKEN);

		// 重新获取 accessToken
		if (StringUtils.isBlank(accessToken)) {
			String openidUrl = wechatProperties.getWebLoginClient().getApiUrl()
					+ WechatWebLoginClientEnum.sns_oauth2_access_token.code();
			Map<String, Object> requestData = Maps.newTreeMap();
			requestData.put("appid", wechatProperties.getWebLoginClient().getAppid());
			requestData.put("secret", wechatProperties.getWebLoginClient().getSecret());
			requestData.put("code", code);
			requestData.put("grant_type", "authorization_code");

			String requestUrl = HttpRequest.append(openidUrl, requestData);

			log.info("微信公众号[获取access_token],请求地址:{}", requestUrl);
			HttpRequest httpRequest = HttpRequest.get(requestUrl).acceptCharset(HttpRequest.CHARSET_UTF8);
			httpRequest.trustAllCerts();
			httpRequest.trustAllHosts();
			int httpCode = httpRequest.code();
			String resultBody = httpRequest.body(HttpRequest.CHARSET_UTF8);
			log.info("微信公众号[获取access_token],响应数据:{}", resultBody);

			JSONObject bodyJson = JSON.parseObject(resultBody);
			if (httpCode != 200) {
				log.info("微信公众号获取accessToken失败，" + bodyJson.get("errmsg"));
				throw new BusinessException(bodyJson.getString("errmsg"), bodyJson.getString("errcode"));
			}
			log.info("公众号重新获取access_token数据{}", bodyJson);
			accessToken = setRedisAccessToken(bodyJson);
		}
		return accessToken;
	}

	/**
	 * 设置redis，时间过期：微信有效时间-15分钟 ，重新获取
	 * 
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	private String setRedisAccessToken(JSONObject obj) {
		log.info("redis设置微信公众号 access_token：{}", obj);
		String accessToken = obj.getString("access_token");
		Long expiresIn = obj.getLong("expires_in");
		redisTemplate.opsForValue().set(WECHAT_WEB_LOGIN_ACCESS_TOKEN, accessToken, (expiresIn - 900),
				TimeUnit.SECONDS);
		return accessToken;
	}

}
