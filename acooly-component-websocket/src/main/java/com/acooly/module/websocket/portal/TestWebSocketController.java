package com.acooly.module.websocket.portal;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile("!online")
@Controller
@RequestMapping("/test/websocket")
public class TestWebSocketController {

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(HttpSession session, Model model) {
		return "/test/webSocket";
	}
}