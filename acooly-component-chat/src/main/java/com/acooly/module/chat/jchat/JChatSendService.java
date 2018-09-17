package com.acooly.module.chat.jchat;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.module.chat.ChatProperties;
import com.acooly.module.chat.jchat.enums.HttpRequestMethodEnum;
import com.acooly.module.chat.jchat.message.base.JChatBaseOrder;
import com.acooly.module.chat.jchat.message.base.JChatBaseResult;
import com.acooly.module.chat.jchat.message.order.AdminRegisterOrder;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * JChat 发送服务
 *
 * @author zhangpu
 * @date 2015年11月4日
 */
@Service
public class JChatSendService {

	private static final Logger logger = LoggerFactory.getLogger(JChatSendService.class);

	@Autowired
	private ChatProperties appProperties;

	public JChatBaseResult request(JChatBaseOrder order, HttpRequestMethodEnum requestMethod) {
		try {
			if (!appProperties.getChat().isEnable()) {
				throw new RuntimeException("JChat(IM)发送开关已关闭");
			}

			JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();

			String url = appProperties.getChat().getGateway() + order.serviceUrl();
			byte[] inputData = order.toString().getBytes(HttpRequest.CHARSET_UTF8);

			logger.info("JChat(IM)请求参数：url:{},inputData:{},method:{}", url, order, requestMethod.getCode());

			// http请求
			HttpRequest http = null;
			if (requestMethod == HttpRequestMethodEnum.METHOD_POST) {
				http = HttpRequest.post(url).acceptJson();
			} else if (requestMethod == HttpRequestMethodEnum.METHOD_DELETE) {
				http = HttpRequest.delete(url).acceptJson();
			} else if (requestMethod == HttpRequestMethodEnum.METHOD_PUT) {
				http = HttpRequest.put(url).acceptJson();
			} else {
				http = HttpRequest.get(url).acceptJson();
			}

			// http参数组装
			http.header(HttpRequest.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");
			http.basic(appProperties.getChat().getAppKey(), appProperties.getChat().getMasterSecret());
			http.send(inputData);

			// 响应
			int resultStatus = http.code();
			String resultBody = http.body(HttpRequest.CHARSET_UTF8);

			logger.info("JChat(IM)响应参数: resultStatus:{},resultBody:{}", resultStatus, resultBody);

			JChatBaseResult jChatResult = new JChatBaseResult();
			jChatResult.setCode(String.valueOf(resultStatus));
			jChatResult.setHttpStatus(resultStatus);
			jChatResult.setBody(resultBody);

			if (resultStatus >= 200 && resultStatus <= 300) {
				if (StringUtils.isNotBlank(resultBody)) {
					jsonMapper.update(resultBody, jChatResult);
				}
			} else {
				if (StringUtils.isNotBlank(resultBody)) {
					if (resultBody.startsWith("[")) {
						int length = resultBody.length();
						resultBody = resultBody.substring(1, length - 1);
					}

				}

				JChatReturn jr = jsonMapper.fromJson(resultBody, JChatReturn.class);
				jChatResult.setCode(jr.getError().getCode());
				jChatResult.setMessage(jr.getError().getMessage());
				jChatResult.setSuccess(false);
			}
			return jChatResult;
		} catch (Exception e) {
			throw new RuntimeException("JChat 通信失败:" + e.getMessage());
		}
	}

	protected String getAuthorization() {
		String p = appProperties.getChat().getAppKey() + ":" + appProperties.getChat().getMasterSecret();
		return "Basic " + Base64.encodeBase64String(p.getBytes());
	}

	static class JChatReturn {
		private JChatError error;

		public JChatError getError() {
			return error;
		}

		public void setError(JChatError error) {
			this.error = error;
		}
	}

	static class JChatError {
		private String code;
		private String message;

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
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String url = "https://api.im.jpush.cn/v1/admins/";
		// String url = "https://api.im.jpush.cn/v1/users/cuifuq7";

		String p = "f0c5f57dd1733b00f5a36957:ce1f57ecef53e776b54d985a";
		String authorization = "Basic " + Base64.encodeBase64String(p.getBytes());

		System.out.println(authorization);
		System.out.println("Basic ZjBjNWY1N2RkMTczM2IwMGY1YTM2OTU3OmNlMWY1N2VjZWY1M2U3NzZiNTRkOTg1YQ==");

		// ---------------------------------------------------

		AdminRegisterOrder order = new AdminRegisterOrder();
		order.setUserName("us2r11211");
		order.setPassword("123123123");

		System.out.println(order);
		byte[] input = order.toString().getBytes(HttpRequest.CHARSET_UTF8);

		HttpRequest http = HttpRequest.get(url).acceptJson();
		http.basic("f0c5f57dd1733b00f5a36957", "ce1f57ecef53e776b54d985a");
		http.header("Content-Type", "application/json; charset=utf-8");

		http.send(input);

		// System.out.println(http.code());
		System.out.println(http.body(HttpRequest.CHARSET_UTF8));

		// ---------------------------------------------------

	}
}
