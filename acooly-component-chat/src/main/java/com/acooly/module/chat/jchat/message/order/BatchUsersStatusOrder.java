package com.acooly.module.chat.jchat.message.order;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.acooly.module.chat.jchat.enums.JChatRequestUrlEnum;
import com.acooly.module.chat.jchat.message.base.JChatBaseOrder;

import lombok.Getter;
import lombok.Setter;

/***
 * 批量查询用户状态
 * 
 * @author cuifuq
 *
 */
@Setter
@Getter
public class BatchUsersStatusOrder extends JChatBaseOrder {

	/** 用户名 **/
	@NotNull
	private List<String> userNames = new ArrayList<>();

	@Override
	public String serviceUrl() {
		return JChatRequestUrlEnum.batch_users_status.getCode();
	}

	@Override
	public String toString() {
		List<String> userNameDatas = getUserNames();

		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (String userName : userNameDatas) {
			sb.append("\"" + userName + "\",");
		}
		sb.append("]");
		return sb.toString().replaceAll(",]", "]");
	}

}
