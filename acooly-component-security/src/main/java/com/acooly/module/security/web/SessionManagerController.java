/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-12-08 12:14
 */
package com.acooly.module.security.web;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Servlets;
import com.acooly.module.security.config.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * @author zhangpu
 * @date 2022-12-08 12:14
 */
@Slf4j
@Controller
@RequestMapping("/manage/session")
public class SessionManagerController extends AbstractJsonEntityController {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private SessionDAO sessionDAO;

    /**
     * 首页
     *
     * @return
     */
    @Override
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("config", securityProperties.getSession());
        model.addAttribute("sessionCount", getCache().size());
        return "/manage/system/session";
    }

    @Override
    public String show(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            String sessionId = Servlets.getParameter(request, "sessionId");
            Session session = sessionDAO.readSession(sessionId);
            model.addAttribute("session", session);
        } catch (Exception e) {
            handleException("查看", e, request);
        }
        return "/manage/system/sessionShow";
    }

    @Override
    public JsonResult deleteJson(HttpServletRequest request, HttpServletResponse response) {
        JsonResult result = new JsonResult();
        try {
            String sessionId = Servlets.getParameter(request, "id");
            SimpleSession session = new SimpleSession();
            session.setId(sessionId);
            sessionDAO.delete(session);
            result.setMessage("删除成功");
        } catch (Exception e) {
            handleException(result, "删除", e);
        }
        return result;
    }

    @RequestMapping("actives")
    @ResponseBody
    public JsonListResult<Session> actives() {
        JsonListResult<Session> result = new JsonListResult<>();
        result.getRows().addAll(sessionDAO.getActiveSessions());
        return result;
    }

    protected Cache<Serializable, Session> getCache() {
        return ((CachingSessionDAO) sessionDAO).getActiveSessionsCache();
    }

}
