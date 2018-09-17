package com.acooly.module.chat.jchat.message.order;

import org.hibernate.validator.constraints.NotBlank;

import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.module.chat.jchat.enums.JChatRequestUrlEnum;
import com.acooly.module.chat.jchat.message.base.JChatBaseOrder;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/***
 * 用户注册
 * 
 * @author cuifuq
 *
 */
@Setter
@Getter
public class UserRegisterOrder extends JChatBaseOrder {

	/** 用户名 **/
	@NotBlank
	@JsonProperty("username")
	private String userName;
	/** 密码 **/
	@NotBlank
	@JsonProperty("password")
	private String password;
	/** 别名 **/
	@JsonProperty("nickname")
	private String nickName;
	/** 个性签名 **/
	@JsonProperty("signature")
	private String signature;

	@Override
	public String serviceUrl() {
		return JChatRequestUrlEnum.user_register.getCode();
	}

	@Override
	public String toString() {
		// return "[" + JSON.toJSON(this).toString() + "]";
		return "[" + JsonMapper.nonEmptyMapper().toJson(this) + "]";
	}

}
