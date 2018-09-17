package com.acooly.module.chat.jchat.message.base;

/**
 * JPush 响应对象
 *
 * @author zhangpu
 * @date 2015年11月5日
 */
public class JChatBaseResult {

	/**
	 * http 状态码
	 */
	private int httpStatus;

	private String code;

	private String message;

	/**
	 * 请求成功 true
	 * 
	 * 请求失败 false
	 */
	private boolean success = true;

	/**
	 * 响应报文（二次解析）
	 */
	private String body;

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return String.format("{httpStatus:%s, code:%s, success:%s, message:%s}", httpStatus, code, success, message);
	}
}
