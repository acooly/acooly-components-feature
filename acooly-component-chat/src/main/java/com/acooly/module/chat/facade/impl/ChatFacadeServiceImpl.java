package com.acooly.module.chat.facade.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.module.chat.entity.ChatInfoTemplate;
import com.acooly.module.chat.entity.ChatUser;
import com.acooly.module.chat.facade.ChatFacadeService;
import com.acooly.module.chat.facade.dto.CustomerServiceChatDto;
import com.acooly.module.chat.jchat.dto.MessageBodyTextDto;
import com.acooly.module.chat.jchat.message.base.JChatBaseResult;
import com.acooly.module.chat.jchat.message.order.BatchUsersStatusOrder;
import com.acooly.module.chat.jchat.message.order.SendMessageOrder;
import com.acooly.module.chat.jchat.service.JChatService;
import com.acooly.module.chat.service.ChatInfoTemplateService;
import com.acooly.module.chat.service.ChatUserService;
import com.acooly.module.chat.service.impl.ChatInfoTemplateServiceImpl;
import com.google.common.collect.Lists;

/**
 * IM聊天-
 *
 * Date: 2018-08-30 17:57:10
 * 
 * @author acooly
 *
 */
@Service("chatFacadeService")
public class ChatFacadeServiceImpl implements ChatFacadeService {

	private static final Logger logger = LoggerFactory.getLogger(ChatInfoTemplateServiceImpl.class);

	@Autowired
	private ChatUserService chatUserService;
	@Autowired
	private ChatInfoTemplateService chatInfoTemplateService;
	@Autowired
	private JChatService jChatService;

	@Override
	public CustomerServiceChatDto autoCustomerService(String targetUserName) {

		if (StringUtils.isBlank(targetUserName)) {
			throw new BusinessException("消息接收者 -用户名不能为空");
		}

		try {
			/** 发送内容文本 */
			CustomerServiceChatDto dto = new CustomerServiceChatDto();

			List<String> onlineUser = Lists.newArrayList();
			List<ChatUser> chatUserList = chatUserService.findAdminUserNames();
			for (ChatUser chatUser : chatUserList) {
				onlineUser.add(chatUser.getUserName());
			}
			int onlineSize = onlineUser.size();
			if (onlineSize == 0) {
				throw new BusinessException("系统未配置IM管理员用户");
			}

			// 默认发送者用户
			int randomInt = (int) (Math.random() * (onlineSize - 1 + 1));
			String userNameOnline = onlineUser.get(randomInt);

			// 查询IM用户登陆状态
			BatchUsersStatusOrder uso = new BatchUsersStatusOrder();
			uso.setUserNames(onlineUser);
			onlineUser = jChatService.batchUsersStatus(uso);
			onlineSize = onlineUser.size();
			if (onlineSize > 0) {
				randomInt = (int) (Math.random() * (onlineSize - 1 + 1));
				// 默认发送者用户
				userNameOnline = onlineUser.get(randomInt);
			}

			// 发送信息模板
			ChatInfoTemplate info = null;
			ChatUser chatUser = chatUserService.findAdminByUserName(userNameOnline);
			Long infoTempId = chatUser.getInfoTempId();
			
			if (infoTempId != null) {
				info = chatInfoTemplateService.get(infoTempId);
			}
			
			if (info == null) {
				info = chatInfoTemplateService.findByMaxId();
			}
			
			String sendContent = info.getText();

			logger.info("自动客服聊天,默认发送者用户:{},接收者用户:{}", userNameOnline, targetUserName);

			// 发送消息
			SendMessageOrder order = new SendMessageOrder();
			order.setFromId(userNameOnline);
			order.setTargetId(targetUserName);

			MessageBodyTextDto msgBody = new MessageBodyTextDto();
			msgBody.setText(sendContent);
			order.setMsgBody(msgBody);
			JChatBaseResult result = jChatService.sendMessages(order);

			if (!result.isSuccess()) {
				logger.error("自动客服聊天失败:{}", result.getMessage());
				throw new BusinessException("自动客服聊天失败");
			}

			dto.setFormUserName(userNameOnline);
			dto.setTargetUserName(targetUserName);
			dto.setSendContent(sendContent);
			return dto;

		} catch (Exception e) {
			logger.error("自动客服聊天异常:{}", e);
			throw new BusinessException("自动客服聊天失败");
		}

	}
}
