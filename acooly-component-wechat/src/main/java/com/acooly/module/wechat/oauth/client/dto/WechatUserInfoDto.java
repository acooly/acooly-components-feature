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
public class WechatUserInfoDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 普通用户的标识，对当前开发者帐号唯一
	 */
	@JsonProperty("openid")
	private String openId;

	/**
	 * 普通用户昵称
	 */
	@JsonProperty("nickname")
	private String nickName;

	/**
	 * 普通用户性别，1为男性，2为女性
	 */
	@JsonProperty("sex")
	private String sex;

	/**
	 * 普通用户个人资料填写的省份
	 */
	@JsonProperty("province")
	private String province;

	/**
	 * 普通用户个人资料填写的城市
	 */
	@JsonProperty("city")
	private String city;

	/**
	 * 国家，如中国为CN
	 */
	@JsonProperty("country")
	private String country;

	/**
	 * 用户头像，最后一个数值代表正方形头像大小 （有0、46、64、96、132数值可选，0代表640*640正方形头像）， 用户没有头像时该项为空
	 */
	@JsonProperty("headimgurl")
	private String headImgUrl;

	/**
	 * 用户特权信息，json数组，如微信沃卡用户为（chinaunicom）
	 */
	@JsonProperty("privilege")
	private Object privilege;

	/**
	 * 用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
	 */
	@JsonProperty("unionid")
	private String unionId;

	/**
	 * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号
	 */
	@JsonProperty("subscribe")
	private String subscribe="0";

	/**
	 * 用户关注时间
	 */
	@JsonProperty("subscribe_time")
	private String subscribeTime;

	/**
	 * 公众号运营者对粉丝的备注
	 */
	@JsonProperty("remark")
	private String remark;

	/**
	 * 用户所在的分组ID（兼容旧的用户分组接口）
	 */
	@JsonProperty("groupid")
	private String groupId;

	/**
	 * 用户被打上的标签ID列表
	 */
	@JsonProperty("tagid_list")
	private Object tagIdList;

	/**
	 * 用户关注的渠道来源
	 */
	@JsonProperty("subscribe_scene")
	private String subscribeScene;

	public static String filterEmoji(String source) {
		if (source == null) {
			return source;
		}
		Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
				Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		Matcher emojiMatcher = emoji.matcher(source);
		if (emojiMatcher.find()) {
			source = emojiMatcher.replaceAll("*");
			return source;
		}
		return source;
	}
}
