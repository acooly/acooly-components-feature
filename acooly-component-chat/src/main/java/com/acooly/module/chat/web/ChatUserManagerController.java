/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-08-30
*/
package com.acooly.module.chat.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.MappingMethod;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.enums.AbleStatus;
import com.acooly.module.chat.entity.ChatUser;
import com.acooly.module.chat.enums.TypeEnum;
import com.acooly.module.chat.jchat.dto.MessageBodyTextDto;
import com.acooly.module.chat.jchat.message.base.JChatBaseResult;
import com.acooly.module.chat.jchat.message.order.AdminRegisterOrder;
import com.acooly.module.chat.jchat.message.order.PasswordModifyOrder;
import com.acooly.module.chat.jchat.message.order.SendMessageOrder;
import com.acooly.module.chat.jchat.message.order.UserInfoModifyOrder;
import com.acooly.module.chat.jchat.message.order.UserStatusModifyOrder;
import com.acooly.module.chat.jchat.service.JChatService;
import com.acooly.module.chat.service.ChatUserService;
import com.google.common.collect.Maps;

/**
 * IM聊天-用户名 管理控制器
 * 
 * @author acooly Date: 2018-08-30 17:57:10
 */
@Controller
@RequestMapping(value = "/manage/component/chat/chatUser")
public class ChatUserManagerController extends AbstractJQueryEntityController<ChatUser, ChatUserService> {

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private ChatUserService chatUserService;

	@Autowired
	private JChatService jChatService;

	@Override
	protected ChatUser onSave(HttpServletRequest request, HttpServletResponse response, Model model, ChatUser entity,
			boolean isCreate) throws Exception {

		if (isCreate) {
			AdminRegisterOrder order = new AdminRegisterOrder();
			order.setUserName(entity.getUserName());
			order.setPassword(entity.getPassword());
			order.setNickName(entity.getNickName());
			order.setSignature(entity.getSignature());
			order.setBirthday(Dates.format(entity.getBirthday(), Dates.CHINESE_DATE_FORMAT_LINE));
			order.setGender("" + entity.getGender());

			JChatBaseResult result = jChatService.adminRegister(order);
			if (!result.isSuccess()) {
				throw new RuntimeException("开通im账户失败，错误：" + result.getMessage());
			}

		} else {
			// 修改用户信息
			UserInfoModifyOrder umo = new UserInfoModifyOrder();
			umo.setUserName(entity.getUserName());
			umo.setNickName(entity.getNickName());
			umo.setSignature(entity.getSignature());
			umo.setBirthday(Dates.format(entity.getBirthday(), Dates.CHINESE_DATE_FORMAT_LINE));
			umo.setGender("" + entity.getGender());
			JChatBaseResult result = jChatService.userInfoModify(umo);
			if (!result.isSuccess()) {
				throw new RuntimeException("修改im账户失败，错误：" + result.getMessage());
			}
		}

		return super.onSave(request, response, model, entity, isCreate);
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping({ "passwordModify" })
	public String passwordModify(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAllAttributes(this.referenceData(request));
			String id = request.getParameter("id");
			ChatUser entity = getEntityService().get(Long.parseLong(id));
			model.addAttribute("chatUser", entity);
		} catch (Exception e) {
		}
		return "/manage/component/chat/chatUserPasswordModify";
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping({ "passwordModifyJson" })
	@ResponseBody
	public JsonEntityResult<ChatUser> passwordModifyJson(HttpServletRequest request, HttpServletResponse response) {
		this.allow(request, response, MappingMethod.update);
		JsonEntityResult<ChatUser> result = new JsonEntityResult<ChatUser>();
		try {
			String id = request.getParameter("id");
			String password = request.getParameter("newPassword");
			ChatUser entity = getEntityService().get(Long.parseLong(id));
			// 修改用户密码
			PasswordModifyOrder pmo = new PasswordModifyOrder();
			pmo.setUserName(entity.getUserName());
			pmo.setNewPassword(password);
			JChatBaseResult imResult = jChatService.passwordModify(pmo);
			if (!imResult.isSuccess()) {
				throw new RuntimeException("密码修改失败，错误：" + imResult.getMessage());
			}
			entity.setPassword(password);
			getEntityService().update(entity);

			result.setEntity(entity);
			result.setMessage("密码修改成功");

		} catch (Exception e) {
			this.handleException(result, "密码修改失败", e);
		}

		return result;
	}

	/**
	 * 强制聊天
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping({ "sendMessage" })
	public String sendMessage(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAllAttributes(this.referenceData(request));
			String id = request.getParameter("id");
			ChatUser entity = getEntityService().get(Long.parseLong(id));
			model.addAttribute("chatUser", entity);
		} catch (Exception e) {
		}
		return "/manage/component/chat/chatSendMessage";
	}

	/**
	 * 强制聊天
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping({ "sendMessageJson" })
	@ResponseBody
	public JsonEntityResult<ChatUser> sendMessageJson(HttpServletRequest request, HttpServletResponse response) {
		JsonEntityResult<ChatUser> result = new JsonEntityResult<ChatUser>();
		this.allow(request, response, MappingMethod.update);
		try {
			String id = request.getParameter("id");
			String targetUserName = request.getParameter("targetUserName");
			String messages = request.getParameter("messages");
			ChatUser entity = getEntityService().get(Long.parseLong(id));

			SendMessageOrder smo = new SendMessageOrder();
			smo.setFromId(entity.getUserName());
			smo.setTargetId(targetUserName);

			MessageBodyTextDto msgBody = new MessageBodyTextDto();
			msgBody.setText(messages);
			smo.setMsgBody(msgBody);
			
			JChatBaseResult imResult = jChatService.sendMessages(smo);
			if (!imResult.isSuccess()) {
				throw new RuntimeException("发送信息失败，错误：" + imResult.getMessage());
			}

			result.setEntity(entity);
			result.setMessage("发送信息成功");
		} catch (Exception e) {
			this.handleException(result, "发送信息失败", e);
		}

		return result;
	}

	/**
	 * 用户状态修改
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping({ "statusUpdate" })
	public String statusUpdate(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAllAttributes(this.referenceData(request));
			String id = request.getParameter("id");
			ChatUser entity = getEntityService().get(Long.parseLong(id));
			model.addAttribute("chatUser", entity);
		} catch (Exception e) {
		}
		return "/manage/component/chat/chatUserStatusUpdate";
	}

	/**
	 * 用户状态修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping({ "statusUpdateJson" })
	@ResponseBody
	public JsonEntityResult<ChatUser> statusUpdateJson(HttpServletRequest request, HttpServletResponse response) {
		this.allow(request, response, MappingMethod.update);
		JsonEntityResult<ChatUser> result = new JsonEntityResult<ChatUser>();
		try {
			String id = request.getParameter("id");
			String status = request.getParameter("status");
			ChatUser entity = getEntityService().get(Long.parseLong(id));

			AbleStatus statusEnum = AbleStatus.findStatus(status);

			// 修改用户密码
			UserStatusModifyOrder pmo = new UserStatusModifyOrder();
			pmo.setUserName(entity.getUserName());

			pmo.setDisable("false");
			if (statusEnum == AbleStatus.disable) {
				pmo.setDisable("true");
			}

			JChatBaseResult imResult = jChatService.userStatusModify(pmo);
			if (!imResult.isSuccess()) {
				throw new RuntimeException("状态修改失败，错误：" + imResult.getMessage());
			}
			entity.setStatus(statusEnum);
			getEntityService().update(entity);

			result.setEntity(entity);
			result.setMessage("状态修改成功");

		} catch (Exception e) {
			this.handleException(result, "状态修改失败", e);
		}

		return result;
	}

	public static Map<Integer, String> allGenders = Maps.newLinkedHashMap();
	static {
		allGenders.put(ChatUser.man, "男");
		allGenders.put(ChatUser.woman, "女");
	}

	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allTypes", TypeEnum.mapping());
		model.put("allStatuss", AbleStatus.mapping());
		model.put("allGenders", allGenders);
	}

}
