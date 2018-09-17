package com.acooly.module.chat.jchat.message.order;

import org.hibernate.validator.constraints.NotBlank;

import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.module.chat.jchat.enums.JChatRequestUrlEnum;
import com.acooly.module.chat.jchat.message.base.JChatBaseOrder;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/***
 * 修改密码
 * 
 * @author cuifuq
 *
 */
@Setter
@Getter
public class PasswordModifyOrder extends JChatBaseOrder {

	/** 用户名 **/
	@NotBlank
	@JsonProperty("username")
	private String userName;

	/** 新密码 **/
	@NotBlank
	@JsonProperty("new_password")
	private String newPassword;

	@Override
	public String serviceUrl() {
		return JChatRequestUrlEnum.password_modify.getCode() + getUserName() + "/password";
	}

	@Override
	public String toString() {
		return JsonMapper.nonEmptyMapper().toJson(this);
	}
}
