package com.acooly.module.chat.jchat.message.order;

import org.hibernate.validator.constraints.NotBlank;

import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.module.chat.jchat.enums.JChatRequestUrlEnum;
import com.acooly.module.chat.jchat.message.base.JChatBaseOrder;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/***
 * Admin Register 管理员注册 (管理员api发送消息接口的权限)
 * 
 * @author cuifuq
 *
 */
@Setter
@Getter
public class UserInfoModifyOrder extends JChatBaseOrder {

	/** 用户名 **/
	@NotBlank
	@JsonProperty("username")
	private String userName;

	/** 别名 **/
	@NotBlank
	@JsonProperty("nickname")
	private String nickName;

	/** 个性签名 **/
	@JsonProperty("signature")
	private String signature;
	/** 生日 **/
	@JsonProperty("birthday")
	private String birthday;
	/** 性别 **/
	@JsonProperty("gender")
	private String gender;

	@Override
	public String serviceUrl() {
		return JChatRequestUrlEnum.user_info_modify.getCode() + getUserName();
	}

	@Override
	public String toString() {
		return JsonMapper.nonEmptyMapper().toJson(this);
	}
}
