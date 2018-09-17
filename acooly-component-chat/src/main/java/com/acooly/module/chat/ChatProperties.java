package com.acooly.module.chat;

import static com.acooly.module.chat.ChatProperties.PREFIX;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author cuifuq
 */
@ConfigurationProperties(prefix = PREFIX)
@Data
public class ChatProperties {
	public static final String PREFIX = "acooly.im";
	private Boolean enable = true;
	private String storagePath = "chat";

	private Chat chat = new Chat();

	/**
	 * 
	 * <li>使用场景: IM聊天、社交
	 * 
	 * <li>面向对象: 用户、帐号
	 * 
	 * <li>消息对象: 用户之间互相交流 s
	 * <li>发送方式: 单聊、群聊
	 *
	 */
	@Data
	public static class Chat {
		private boolean enable = true;
		private String gateway = "https://api.im.jpush.cn";
		private String appKey;
		private String masterSecret;
	}
}
