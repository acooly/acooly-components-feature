package com.acooly.core.test.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author qiuboboy@qq.com
 * @date 2018-06-30 10:22
 */
@RestController
@RequestMapping(value = "/session")
@Slf4j
public class SessionController {
    @RequestMapping("create")
    public String testEx(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("a","aaa");
        return "create";
    }
    @RequestMapping("get")
    public String get(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        Object a = session.getAttribute("a");
        return "get";
    }
}
