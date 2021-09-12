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
import com.acooly.module.wechat.oauth.client.dto.WechatUserInfoDto;
import com.acooly.module.wechat.oauth.login.WechatWebLoginClientService;
import com.acooly.module.wechat.oauth.login.dto.WechatWebLoginInfoDto;
import com.acooly.module.wechat.oauth.service.WechatClientService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/wechat/webLogin/api/")
public class WechatWebLoginApiControl {

	@Autowired
	private WechatWebLoginClientService wechatWebLoginClientService;

	@Autowired
	private WechatClientService wechatClientService;

	@Autowired
	private WechatProperties wechatProperties;

	/**
	 * 
	 * http://www.xxxx.com/wechat/webLogin/api/index.html
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
			redirectUri = wechatWebLoginClientService.wechatWebLoginOauth(redirectUri);
		} catch (Exception e) {
			log.error("微信-页面授权登录页面失败:{}", redirectUri);
		}
		return "redirect:" + redirectUri;
	}

	/**
	 * 
	 * http://www.xxxx.com/wechat/webLogin/api/backRedirect.html
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
			WechatWebLoginInfoDto dto = wechatWebLoginClientService.getOpenIdAndUnionid(request, response);
			model.addAttribute("wechatUserInfo", dto);
			log.info("获取用户信息:{}", dto);
		} catch (Exception e) {
			log.error("微信回调跳转获取用户信息失败{}", e);
		}
		return "/wechat/wechat_user_info";
	}

}
