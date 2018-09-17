package com.acooly.module.chat.jchat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.module.chat.jchat.JChatSendService;
import com.acooly.module.chat.jchat.analysis.JChatAnalysis;
import com.acooly.module.chat.jchat.enums.HttpRequestMethodEnum;
import com.acooly.module.chat.jchat.message.base.JChatBaseResult;
import com.acooly.module.chat.jchat.message.order.AdminRegisterOrder;
import com.acooly.module.chat.jchat.message.order.BatchUsersStatusOrder;
import com.acooly.module.chat.jchat.message.order.SendMessageOrder;
import com.acooly.module.chat.jchat.message.order.UserInfoModifyOrder;
import com.acooly.module.chat.jchat.message.order.PasswordModifyOrder;
import com.acooly.module.chat.jchat.message.order.UserRegisterOrder;
import com.acooly.module.chat.jchat.message.order.UserStatusModifyOrder;
import com.acooly.module.chat.jchat.service.JChatService;
import com.google.common.collect.Lists;

/**
 * 极光 IM 服务
 * 
 * @author cuifuq
 *
 */
@Service("jChatService")
public class JChatServiceImpl implements JChatService {

	@Autowired
	private JChatSendService jChatSendService;

	@Override
	public JChatBaseResult userRegister(UserRegisterOrder order) {
		return jChatSendService.request(order, HttpRequestMethodEnum.METHOD_POST);
	}

	@Override
	public JChatBaseResult adminRegister(AdminRegisterOrder order) {
		return jChatSendService.request(order, HttpRequestMethodEnum.METHOD_POST);
	}

	@Override
	public JChatBaseResult passwordModify(PasswordModifyOrder order) {
		return jChatSendService.request(order, HttpRequestMethodEnum.METHOD_PUT);
	}

	@Override
	public JChatBaseResult userInfoModify(UserInfoModifyOrder order) {
		return jChatSendService.request(order, HttpRequestMethodEnum.METHOD_PUT);
	}

	@Override
	public JChatBaseResult userStatusModify(UserStatusModifyOrder order) {
		return jChatSendService.request(order, HttpRequestMethodEnum.METHOD_PUT);
	}

	@Override
	public List<String> batchUsersStatus(BatchUsersStatusOrder order) {
		List<String> userOnlineList = Lists.newArrayList();
		JChatBaseResult result = jChatSendService.request(order, HttpRequestMethodEnum.METHOD_POST);
		if (result.isSuccess()) {
			String resultBody = result.getBody();
			userOnlineList = JChatAnalysis.userOnline(resultBody);
		}
		return userOnlineList;
	}

	@Override
	public JChatBaseResult sendMessages(SendMessageOrder order) {
		return jChatSendService.request(order, HttpRequestMethodEnum.METHOD_POST);
	}

}
