package com.acooly.module.olog.collector.intercept;

import com.acooly.core.common.web.support.JsonResult;
import com.acooly.module.olog.collector.OlogForwarder;
import com.acooly.module.olog.collector.logger.OlogCollector;
import com.acooly.module.olog.collector.logger.OlogTarget;
import com.acooly.module.olog.facade.dto.OlogDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Olog HandleInterceptor实现
 *
 * @author zhangpu
 */
public class OlogHandleInterceptor extends HandlerInterceptorAdapter {

    public static final String EXECUTE_START_KEY = "ologExecuteStart";
    public static final String RESPONSE_BODY_KEY = "OlogHandleInterceptor.responseBody";
    public static final String OLOG_DTO_KEY = "OlogHandleInterceptor.ologDto";
    private static final Logger logger = LoggerFactory.getLogger(OlogHandleInterceptor.class);
    @Resource
    private OlogCollector ologCollector;
    @Autowired
    private OlogForwarder ologClient;

    public static boolean responseBody(HttpServletRequest request) {

        return Boolean.parseBoolean(
                (String) request.getAttribute(OlogHandleInterceptor.RESPONSE_BODY_KEY));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        long ologExecuteStart = System.currentTimeMillis();
        request.setAttribute(EXECUTE_START_KEY, ologExecuteStart);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView)
            throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        try {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Object[] args = {request, response, ex};
            Object result = getResult(request, response, handlerMethod, ex);
            OlogTarget target =
                    new OlogTarget(
                            handlerMethod.getBean(),
                            handlerMethod.getMethod(),
                            args,
                            result,
                            getExecuteTimes(request));
            OlogDTO collected = ologCollector.collect(request, response, target);
            if (collected != null) {
                if (!OlogHandleInterceptor.responseBody(request)) {
                    ologClient.logger(collected);
                } else {
                    request.setAttribute(OLOG_DTO_KEY, collected);
                }
            }
        } catch (Exception e) {
            logger.warn("afterCompletion fail,ignore: {}", e.getMessage());
        }

        super.afterCompletion(request, response, handler, ex);
    }

    protected long getExecuteTimes(HttpServletRequest request) {
        Long startTime = (Long) request.getAttribute(EXECUTE_START_KEY);
        if (startTime == null) {
            return 0L;
        } else {
            request.removeAttribute(EXECUTE_START_KEY);
            return System.currentTimeMillis() - startTime;
        }
    }

    protected Object getResult(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handlerMethod,
            Exception ex) {

        if (ex != null) {
            return ex;
        }

        if (String.class.isAssignableFrom(handlerMethod.getMethod().getReturnType())) {
            // 返回时URL页面路径，则从session中获取处理结果，这里随便返回一个URL,由collector从session中获取
            return new String("");
        } else {
            // 对象格式返回，暂时无法从response流获取Json，直接返回成功
            request.setAttribute(OlogHandleInterceptor.RESPONSE_BODY_KEY, "true");
            return new JsonResult();
        }
    }
}
