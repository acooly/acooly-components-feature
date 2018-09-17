package com.acooly.module.chat.jchat.service;

import java.util.List;

import com.acooly.module.chat.jchat.message.base.JChatBaseResult;
import com.acooly.module.chat.jchat.message.order.AdminRegisterOrder;
import com.acooly.module.chat.jchat.message.order.BatchUsersStatusOrder;
import com.acooly.module.chat.jchat.message.order.SendMessageOrder;
import com.acooly.module.chat.jchat.message.order.PasswordModifyOrder;
import com.acooly.module.chat.jchat.message.order.UserInfoModifyOrder;
import com.acooly.module.chat.jchat.message.order.UserRegisterOrder;
import com.acooly.module.chat.jchat.message.order.UserStatusModifyOrder;

/**
 * 极光 IM 服务
 * 
 * @author cuifuq
 *
 */
public interface JChatService {

	/**
	 * 普通用户注册
	 * 
	 * @param order
	 * @return
	 */
	public JChatBaseResult userRegister(UserRegisterOrder order);

	/**
	 * 管理员注册
	 * 
	 * @param order
	 * @return
	 */
	public JChatBaseResult adminRegister(AdminRegisterOrder order);

	/**
	 * 修改密码
	 * 
	 * @param order
	 * @return
	 */
	public JChatBaseResult passwordModify(PasswordModifyOrder order);

	/**
	 * 用户信息变更
	 * 
	 * @param order
	 * @return
	 */
	public JChatBaseResult userInfoModify(UserInfoModifyOrder order);
	
	/**
	 * 用户状态
	 * 
	 * @param order
	 * @return
	 */
	public JChatBaseResult userStatusModify(UserStatusModifyOrder order);

	/**
	 * 批量用户状态
	 * 
	 * @param order
	 * @return
	 */
	public List<String> batchUsersStatus(BatchUsersStatusOrder order);

	/**
	 * 发送聊天消息
	 * 
	 * @param order
	 * @return
	 */
	public JChatBaseResult sendMessages(SendMessageOrder order);

}
