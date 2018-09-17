package com.acooly.module.chat.portal;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.web.support.JsonResult;
import com.acooly.module.chat.ChatProperties;
import com.acooly.module.chat.facade.ChatFacadeService;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/portal/chat/im/")
public class ChatPortalController {

	@Autowired
	private ChatProperties appProperties;

	@Autowired
	private ChatFacadeService chatFacadeService;

	@RequestMapping(value = "signature",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult signature(HttpServletRequest request, HttpServletResponse response, Model model) {
		JsonResult result = new JsonResult();
		String appKey = appProperties.getChat().getAppKey();
		String masterSecret = appProperties.getChat().getMasterSecret();
		Map<Object, Object> data = Maps.newHashMap();
		data.put("appKey", appKey);
		data.put("masterSecret", masterSecret);
		result.setData(data);
		return result;
	}
}
