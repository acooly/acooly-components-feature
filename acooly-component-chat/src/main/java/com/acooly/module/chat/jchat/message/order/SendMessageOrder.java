package com.acooly.module.chat.jchat.message.order;

import org.hibernate.validator.constraints.NotBlank;

import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.module.chat.jchat.dto.MessageBodyTextDto;
import com.acooly.module.chat.jchat.enums.JChatRequestUrlEnum;
import com.acooly.module.chat.jchat.message.base.JChatBaseOrder;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/***
 * 消息发送
 * 
 * @author cuifuq
 *
 */
@Setter
@Getter
public class SendMessageOrder extends JChatBaseOrder {

	@NotBlank
	@JsonProperty("version")
	private String version = "1";

	/**
	 * 发送目标类型 single - 个人，group - 群组 chatroom - 聊天室（必填）
	 **/
	@NotBlank
	@JsonProperty("target_type")
	private String targetType = "single";

	/**
	 * 发送消息者身份 当前只限admin用户，必须先注册admin用户 （必填）
	 **/
	@NotBlank
	@JsonProperty("from_type")
	private String fromType="admin";

	/**
	 * 发消息类型
	 * <li>text - 文本，
	 * <li>image - 图片,
	 * <li>custom - 自定义消息（msg_body为json对象即可，服务端不做校验）
	 * <li>voice - 语音 （必填）
	 **/
	@NotBlank
	@JsonProperty("msg_type")
	private String msgType = "text";

	/**
	 * 目标id single填username group 填Group id chatroom 填chatroomid（必填）
	 **/
	@NotBlank
	@JsonProperty("target_id")
	private String targetId;

	/**
	 * 跨应用目标appkey（选填）
	 */
	@JsonProperty("target_appkey")
	private String targetAppkey;

	/**
	 * 发送者的username （必填)
	 */
	@NotBlank
	@JsonProperty("from_id")
	private String fromId;

	/**
	 * 发送者展示名（选填）
	 */
	@JsonProperty("from_name")
	private String fromName;

	/**
	 * 接受者展示名（选填）
	 */
	@JsonProperty("target_name")
	private String targetName;

	/**
	 * 消息是否离线存储 true或者false，默认为false，表示需要离线存储（选填）
	 */
	@JsonProperty("no_offline")
	private String noOffline = "false";

	/**
	 * 消息是否在通知栏展示 true或者false，默认为false，表示在通知栏展示（选填）
	 */
	@JsonProperty("no_notification")
	private String noNotification = "false";

	/**
	 * Json对象的消息体 限制为4096byte
	 */
	@JsonProperty("msg_body")
	private MessageBodyTextDto msgBody;

	@Override
	public String serviceUrl() {
		return JChatRequestUrlEnum.send_messages.getCode();
	}

	@Override
	public String toString() {
		return JsonMapper.nonEmptyMapper().toJson(this);
	}

}
