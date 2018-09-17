package com.acooly.module.chat.jchat.message.order;

import org.hibernate.validator.constraints.NotBlank;

import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.module.chat.jchat.enums.JChatRequestUrlEnum;
import com.acooly.module.chat.jchat.message.base.JChatBaseOrder;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/***
 * 修改状态（启用，禁用）
 * 
 * @author cuifuq
 *
 */
@Setter
@Getter
public class UserStatusModifyOrder extends JChatBaseOrder {

	@NotBlank
	@JsonProperty("username")
	private String userName;

	/** disable boolean,true代表禁用用户，false代表激活用户 **/
	@NotBlank
	@JsonProperty("disable")
	private String disable;

	@Override
	public String serviceUrl() {
		return JChatRequestUrlEnum.user_status_modify.getCode() + getUserName() + "/forbidden?disable=" + getDisable();
	}

	@Override
	public String toString() {
		return JsonMapper.nonEmptyMapper().toJson(this);
	}
}
