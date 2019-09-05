package com.acooly.module.security.shiro.listener;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;

/**
 * 观察者处理Subject
 *
 * <p>具体项目中，如果需要对Shire安全框架中的登录，注销动作进行后续处理，可以实现LoginLogoutListener， 并配置到Spring托管。该类会自动通知登录后和注销前两个状态。
 *
 * @author zhangpu
 */
public class ShireLoginLogoutSubject
        implements LoginLogoutListener, ApplicationContextAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(ShireLoginLogoutSubject.class);

    /**
     * 需要通知的监听器
     */
    private Map<String, LoginLogoutListener> listeners;
    /**
     * Spring 容器
     */
    private ApplicationContext applicationContext;

    @Override
    public void beforeLogout(
            HttpServletRequest request, HttpServletResponse response, Subject subject) {
        if (listeners == null || listeners.size() == 0) {
            return;
        }
        for (Map.Entry<String, LoginLogoutListener> entry : listeners.entrySet()) {
            logger.debug("Notify beforeLogout to " + entry.getKey());
            notifyBeforeLogout(request, response, subject, entry.getValue());
        }
    }

    @Override
    public void afterLogin(
            AuthenticationToken token,
            AuthenticationException e,
            HttpServletRequest request,
            HttpServletResponse response) {
        if (listeners == null || listeners.size() == 0) {
            return;
        }
        for (Map.Entry<String, LoginLogoutListener> entry : listeners.entrySet()) {
            if (entry.getValue().getClass().getName().equals(this.getClass().getName())) {
                continue;
            }
            logger.debug("Notify afterLogin to " + entry.getKey());
            notifyAfterLogin(token, e, request, response, entry.getValue());
        }
    }

    private void notifyBeforeLogout(
            HttpServletRequest request,
            HttpServletResponse response,
            Subject subject,
            LoginLogoutListener listener) {
        try {
            listener.beforeLogout(request, response, subject);
        } catch (Exception e2) {
            // Do not do anything
            logger.debug("notifyAfterLogin failure. " + e2.getMessage());
        }
    }

    private void notifyAfterLogin(
            AuthenticationToken token,
            AuthenticationException e,
            HttpServletRequest request,
            HttpServletResponse response,
            LoginLogoutListener listener) {
        try {
            listener.afterLogin(token, e, request, response);
        } catch (Exception e2) {
            // Do not do anything
            logger.debug("notifyAfterLogin failure. " + e2.getMessage());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        listeners = applicationContext.getBeansOfType(LoginLogoutListener.class);
        Iterator<Map.Entry<String, LoginLogoutListener>> iterator = listeners.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, LoginLogoutListener> entry = iterator.next();
            if (entry.getValue().equals(this)) {
                iterator.remove();
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
