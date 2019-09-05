package com.acooly.module.wechat.oauth.client.dto;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 用户基本信息
 * 
 * @author CuiFuQ
 *
 */
@Data
public class WechatOpenIdDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 网页授权接口调用凭证
	 */
	@JsonProperty("access_token")
	private String accessToken;

	/**
	 * 用户唯一标识
	 */
	@JsonProperty("openid")
	private String openId;

	/**
	 * access_token接口调用凭证超时时间，单位（秒）
	 */
	@JsonProperty("expires_in")
	private String expiresIn;

	/**
	 * 用户刷新access_token
	 */
	@JsonProperty("refresh_token")
	private String refreshToken;

	/**
	 * 用户授权的作用域，使用逗号（,）分隔
	 */
	@JsonProperty("scope")
	private String scope;

}
