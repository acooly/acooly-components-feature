package com.acooly.module.wechat.portal;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.module.wechat.WechatProperties;
import com.acooly.module.wechat.oauth.client.WechatWebClientService;
import com.acooly.module.wechat.oauth.client.dto.WechatUserInfoDto;
import com.acooly.module.wechat.oauth.service.WechatClientService;

import lombok.extern.slf4j.Slf4j;
import net.engio.mbassy.bus.config.Feature.SyncPubSub;

@Slf4j
@Controller
@RequestMapping(value = "/wechat/webApi/")
public class WechatWebApiControl {

	@Autowired
	private WechatWebClientService wechatWebClientService;

	@Autowired
	private WechatClientService wechatClientService;

	@Autowired
	private WechatProperties wechatProperties;

	/**
	 * 
	 * http://www.xxxx.com/wechat/webApi/index.html
	 * 
	 * <li>redirectUri 需要 URLEncoder.encode("xxxxxxx", "utf-8")
	 * <li>
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		String redirectUri = request.getParameter("redirectUri");
		try {
			redirectUri = wechatWebClientService.getWechatOauthUrl(redirectUri);
		} catch (Exception e) {
			log.error("微信-用户授权页面失败:{}", redirectUri);
		}
		return "redirect:" + redirectUri;
	}

	/**
	 * 
	 * http://www.xxxx.com/wechat/webApi/backRedirect.html
	 * 
	 * 微信回调跳转
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping({ "backRedirect" })
	public String backRedirect(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			WechatUserInfoDto dto = wechatWebClientService.getWechatUserInfo(request, response);
			dto = wechatClientService.getUserInfoBySubscribe(dto.getOpenId());
			model.addAttribute("wechatUserInfo", dto);

			log.info("获取用户信息:{}", dto);
		} catch (Exception e) {
			log.error("微信回调跳转获取用户信息失败{}", e);
		}
		return "/wechat/wechat_user_info";
	}

	/**
	 * 微信回调验证（微信开发平台-->服务器配置）
	 * 
	 * http://www.xxxx.com/wechat/webApi/callback.html
	 * 
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping({ "callback" })
	@ResponseBody
	public void callback(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String token = wechatProperties.getServerToken();
			if (StringUtils.isBlank(token)) {
				log.error("配置文件[acooly.wechat.serverToken]未设置,微信回调跳转验证失败");
				return;
			}
			// 微信加密签名
			String signature = request.getParameter("signature");
			// 随机字符串
			String echostr = request.getParameter("echostr");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");

			String[] str = { token, timestamp, nonce };
			Arrays.sort(str); // 字典序排序
			String bigStr = str[0] + str[1] + str[2];

			String digest = Hex.encodeHexString(MessageDigest.getInstance("SHA1").digest(bigStr.getBytes()))
					.toLowerCase();

			// 确认请求来至微信
			if (digest.equals(signature)) {
				response.getWriter().print(echostr);
			} else {
				log.error("微信回调成功,验证失败");
			}
		} catch (Exception e) {
			log.error("微信回调跳转失败", e);
		}
	}
}
