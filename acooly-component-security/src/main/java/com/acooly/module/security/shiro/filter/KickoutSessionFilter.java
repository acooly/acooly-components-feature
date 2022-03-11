package com.acooly.module.security.shiro.filter;

import com.acooly.module.security.domain.User;
import com.acooly.module.security.shiro.cache.ShiroCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 单用户登录访问控制器
 * <p>
 * 相同用户名登录，踢出前面的用户
 *
 * @author zhangpu
 */
@Slf4j
public class KickoutSessionFilter extends AccessControlFilter {

    /**
     * 同一个帐号最大会话数 默认1
     */
    private int maxSession = 1;

    /**
     * 踢出后到的地址
     */
    private String kickoutUrl;

    /**
     * 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
     */
    private boolean kickoutAfter = false;

    /**
     * session管理器
     */
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private CacheManager cacheManager;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            // 如果没有登录，直接进行之后的流程
            return true;
        }

        Session session = subject.getSession();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Serializable sessionId = session.getId();
        // 同步控制
        Cache<String, Deque<Serializable>> cache = cacheManager.getCache(ShiroCacheManager.KEY_KICKOUT);
        Deque<Serializable> deque = cache.get(user.getUsername());
        if (deque == null) {
            deque = new LinkedList<>();
            cache.put(user.getUsername(), deque);
        }

        // 如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
        }

        // 如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            // 如果踢出后者
            if (kickoutAfter) {
                kickoutSessionId = deque.removeFirst();
            }
            // 否则踢出前者
            else {
                kickoutSessionId = deque.removeLast();
            }
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (kickoutSession != null) {
                    // 设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {
                log.warn("Kickout 获取被提出会话失败。sessionId: {}", kickoutSessionId, e);
            }
        }
        cache.put(user.getUsername(), deque);

        // 如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute("kickout") != null) {
            // 会话被踢出了
            try {
                subject.logout();
            } catch (Exception e) {
                e.printStackTrace();
            }
            saveRequest(request);
            HttpServletRequest httpRequest = WebUtils.toHttp(request);
            // 如果是ajax请求
            if (isAjax(httpRequest)) {
                HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
                // 使得http会话过期, 2:表示被主动提出，在浏览器段通过Ajax全局ajax错误处理拦截处理
                httpServletResponse.setHeader("SessionState", "2");
                PrintWriter printWriter = response.getWriter();
                printWriter.flush();
                printWriter.close();
            } else {
                WebUtils.issueRedirect(request, response, "/manage/login.html?errorKey=kickout");
            }

            log.info("Kickout 单用户登录策略提出用户登录。username:{}, sessionId: {}", user.getUsername(), session.getId());
            return false;
        }
        return true;
    }


    /**
     * 判断是否为ajax请求
     *
     * @param request
     * @return boolean对象
     */
    public static boolean isAjax(ServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
    }


    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
