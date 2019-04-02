package com.acooly.module.wechat.portal.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.module.wechat.oauth.service.WechatMiniService;

import lombok.extern.slf4j.Slf4j;

@Profile("!online")
@Slf4j
@Controller
@RequestMapping(value = "/wechat/miniApi/test")
public class WechatMiniApiControl {

	@Autowired
	private WechatMiniService wechatMiniService;

	/**
	 * 
	 * http://www.xxxx.com/wechat/miniApi/index.html
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
		String scene = null;
		String page = null;
		wechatMiniService.getMiniProgramImgCode(scene, page);
		return null;
	}
}
