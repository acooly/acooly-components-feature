package com.acooly.module.olog.collector.logger.impl;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.olog.annotation.*;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.module.olog.collector.logger.OlogCollector;
import com.acooly.module.olog.collector.logger.OlogTarget;
import com.acooly.module.olog.collector.logger.operator.OlogOperator;
import com.acooly.module.olog.collector.logger.operator.OperatorUserCollector;
import com.acooly.module.olog.config.OLogProperties;
import com.acooly.module.olog.facade.dto.OlogDTO;
import com.google.common.collect.Maps;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class HttpServletOlogCollector implements OlogCollector {
    private static final Logger logger = LoggerFactory.getLogger(HttpServletOlogCollector.class);

    @Autowired
    private OLogProperties oLogProperties;
    @Autowired
    private OperatorUserCollector operatorUserCollector;

    @Override
    public OlogDTO collect(
            HttpServletRequest request, HttpServletResponse response, OlogTarget target) {
        return collect(request, response, target, null);
    }

    @Override
    public OlogDTO collect(
            HttpServletRequest request,
            HttpServletResponse response,
            OlogTarget target,
            Map<String, Object> context) {
        // 收集日志信息，并持久化，如果出错忽略。
        if (isIgnoreClasse(target) || isIgnoreMethod(target, request)) {
            return null;
        }
        try {
            OlogDTO olog = new OlogDTO();
            handleSystem(olog);
            handleModule(target, request, olog);
            handleAction(target, olog);
            handleOperater(request, olog);
            handleParameters(target, request, olog);
            handleClientInformations(request, olog);
            handleResult(target, request, olog);
            handleContext(context, olog);
            return olog;
        } catch (Exception e) {
            logger.warn("Save olog failure, direct retuen. [{}]", e.getMessage());
            return null;
        }
    }

    protected void handleSystem(OlogDTO olog) {
        olog.setSystem(Apps.getAppName());
    }

    /**
     * 处理会话，如果context中的key与olog属性名相同，则覆盖
     *
     * @param context
     * @param olog
     */
    protected void handleContext(Map<String, Object> context, OlogDTO olog) {
        if (context == null || context.size() == 0) {
            return;
        }
        try {
            BeanUtils.populate(olog, context);
        } catch (Exception e) {
            logger.debug("handle context mapping fail, ignore。{}", e.getMessage());
        }
    }

    /**
     * 处理：模块MODULE和MODULENAME
     *
     * @param request
     * @param olog
     */
    protected void handleModule(OlogTarget target, HttpServletRequest request, OlogDTO olog) {
        // Annotation方式标注模块和模块名称优先级高于配置
        OlogModule ologModule = target.getBean().getClass().getAnnotation(OlogModule.class);
        if (ologModule != null) {
            if (StringUtils.isNotBlank(ologModule.module())) {
                olog.setModule(ologModule.module());
            }
            if (StringUtils.isNotBlank(ologModule.moduleName())) {
                olog.setModuleName(ologModule.moduleName());
            }
        } else {
            if (request != null) {
                logger.debug(
                        "Find one argument is HttpServletRequest, handle module with HttpServletRequest and resourceMapping.");
                String module = request.getRequestURI();
                olog.setModule(module);
                olog.setModuleName(getModuleName(module));
                if (Strings.isBlank(olog.getModuleName())) {
                    String name = target.getBean().getClass().getName();
                    if (name.startsWith("com.acooly.module.")) {
                        name = name.substring("com.acooly.module.".length());
                        name = name.substring(0, name.indexOf('.'));
                    } else {
                        name = module;
                    }
                    olog.setModuleName(name);
                }
            }
        }
    }

    /**
     * 处理:action和ActionName,并设置到Olog中
     *
     * @param target
     */
    protected void handleAction(OlogTarget target, OlogDTO olog) {
        // 获取Action和ActionName,Annotation优先于mapping配置
        String action = target.getMethod().getName();
        String actionName = getActionName(action);
        OlogAction ologAction = (OlogAction) target.getMethod().getAnnotation(OlogAction.class);
        if (ologAction != null) {
            if (StringUtils.isNotBlank(ologAction.action())) {
                action = ologAction.action();
            }
            if (StringUtils.isNotBlank(ologAction.actionName())) {
                actionName = ologAction.actionName();
            }
        }
        olog.setAction(action);
        olog.setActionName(actionName);
    }

    /**
     * 处理:获取操作员信息
     *
     * @param request
     * @param olog
     */
    protected void handleOperater(HttpServletRequest request, OlogDTO olog) {
        OlogOperator ologOperator = operatorUserCollector.getOperatorUser(request);
        if (ologOperator != null) {
            olog.setOperateUserId(ologOperator.getOperatorUserId());
            olog.setOperateUser(ologOperator.getOperatorUserName());
        }
    }

    /**
     * 处理:请求参数
     *
     * @param request
     * @param target
     * @param olog
     */
    protected void handleParameters(OlogTarget target, HttpServletRequest request, OlogDTO olog) {
        if (!oLogProperties.getCollector().isSaveParameter()) {
            return;
        }
        String requestParameters = getRequestParameters(request, target.getMethod());
        requestParameters = StringUtils.substring(requestParameters, 0, 512);
        olog.setRequestParameters(requestParameters);
    }

    /**
     * 处理:客户端信息
     *
     * @param request
     * @param olog
     */
    protected void handleClientInformations(HttpServletRequest request, OlogDTO olog) {
        if (request != null) {
            olog.setClientInformations(getClientInformations(request));
        }
    }

    protected void handleResult(OlogTarget target, HttpServletRequest request, OlogDTO olog) {
        olog.setExecuteMilliseconds(target.getExecuteMilliseconds());
        Object result = target.getResult();

        if (result instanceof JsonResult) {
            // json方式，通过返回值判读
            JsonResult r = (JsonResult) result;
            olog.setOperateResult(r.isSuccess());
            olog.setOperateMessage(getOperateMessage(r.getMessage()));
            return;
        } else if (String.class.isAssignableFrom(result.getClass())) {
            // 字符串返回（URL）模式，则从session中获取结果
            List<String> messages = (List<String>) WebUtils.getSessionAttribute(request, "messages");
            Boolean operateResult = Boolean.TRUE;
            if (messages != null && messages.size() > 0) {
                for (String message : messages) {
                    if (StringUtils.contains(message, "错误") || StringUtils.contains(message, "失败")) {
                        operateResult = Boolean.FALSE;
                        break;
                    }
                }
                String operateMessage = getOperateMessage(messages.toString());
                olog.setOperateResult(operateResult);
                olog.setOperateMessage(operateMessage);
                return;
            }
        } else if (Exception.class.isAssignableFrom(result.getClass())) {
            // 直接异常外抛出个框架或容器
            Exception e = (Exception) result;
            olog.setOperateResult(Boolean.FALSE);
            olog.setOperateMessage(getOperateMessage(e.getMessage()));
        }
    }

    private String getOperateMessage(String originalMessage) {
        return Strings.abbreviate(originalMessage, 256);
    }

    private String getParametersMessage(String originalMessage) {
        return Strings.abbreviate(originalMessage, 512);
    }

    private String getRequestParameters(HttpServletRequest request, Method method) {
        String parameters = "";
        if (oLogProperties.getCollector().isSaveParameter()) {
            Map<String, Object> requestParameters = Servlets.getParametersStartingWith(request, null);
            Map<String, String> paramNameMapping = getParameterMapping(method);

            String ignorParams = "";
            OlogIgnorParameters ologIgnorParameters =
                    (OlogIgnorParameters) method.getAnnotation(OlogIgnorParameters.class);
            if (ologIgnorParameters != null) {
                String ignors[] = ologIgnorParameters.value();
                if (ignors != null && ignors.length > 0) {
                    ignorParams = "," + StringUtils.join(ignors, ",") + ",";
                }
            }

            Map<String, Object> confirmParameters = new TreeMap<String, Object>();
            for (Map.Entry<String, Object> entry : requestParameters.entrySet()) {
                if (!isIgnoreParameter(entry.getKey())
                        && !StringUtils.contains(ignorParams, "," + entry.getKey() + ",")
                        && entry.getValue() != null
                        && StringUtils.isNotBlank(entry.getValue().toString())) {
                    String key = entry.getKey();
                    if (StringUtils.isNotBlank(paramNameMapping.get(key))) {
                        key = paramNameMapping.get(key);
                    }
                    confirmParameters.put(key, entry.getValue());
                }
            }
            if (confirmParameters.size() > 0) {
                parameters = JsonMapper.nonEmptyMapper().toJson(confirmParameters);
            }
        }
        return getParametersMessage(parameters);
    }

    private Map<String, String> getParameterMapping(Method method) {
        OlogParameterMapping ologParameterMapping = method.getAnnotation(OlogParameterMapping.class);
        Map<String, String> paramMapping = Maps.newHashMap();
        if (ologParameterMapping != null) {
            String[] mapping = ologParameterMapping.value();
            if (mapping != null && mapping.length > 0) {
                for (String e : mapping) {
                    String[] entry = StringUtils.split(e, ":");
                    if (entry != null && entry.length == 2) {
                        paramMapping.put(entry[0], entry[1]);
                    }
                }
            }
        }
        return paramMapping;
    }

    private boolean isIgnoreParameter(String parameterName) {
        String[] ignores = StringUtils.split(oLogProperties.getCollector().getIgnoreParameters(), ",");
        for (String ignoreStr : ignores) {
            if (isLike(ignoreStr, parameterName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLike(String srcIncludeStar, String dest) {
        if (srcIncludeStar.equals(dest)) {
            return true;
        }
        if ("*".equals(srcIncludeStar)) {
            return true;
        }

        if (Strings.startsWithIgnoreCase(srcIncludeStar, "*")
                && Strings.endsWithIgnoreCase(srcIncludeStar, "*")) {
            return Strings.containsIgnoreCase(
                    dest, Strings.substring(srcIncludeStar, 1, srcIncludeStar.length() - 1));
        }

        if (Strings.startsWithIgnoreCase(srcIncludeStar, "*")) {
            return Strings.containsIgnoreCase(dest, Strings.substring(srcIncludeStar, 1));
        }

        if (Strings.endsWithIgnoreCase(srcIncludeStar, "*")) {
            return Strings.containsIgnoreCase(
                    dest, Strings.substring(srcIncludeStar, 0, srcIncludeStar.length() - 1));
        }
        return false;
    }

    private boolean isIgnoreClasse(OlogTarget target) {
        return target.getBean().getClass().getAnnotation(Olog.Ignore.class) != null;
    }

    private boolean isIgnoreMethod(OlogTarget target, HttpServletRequest request) {
        boolean result = target.getMethod().getAnnotation(Olog.Ignore.class) != null;
        if (result) {
            return result;
        }
        if (!result) {
            // Check是否有全局忽略方法
            String[] ignores = StringUtils.split(oLogProperties.getCollector().getIgnoreMethods(), ",");
            if (ignores != null) {
                for (String ignoreStr : ignores) {
                    if (isLike(ignoreStr, target.getMethod().getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取请求客户端信息
     *
     * @param request
     * @return
     */
    private String getClientInformations(HttpServletRequest request) {
        String clientIP = request.getRemoteAddr();
        UserAgent userAgent = Servlets.getUserAgent(request);
        OperatingSystem os = userAgent.getOperatingSystem();
        Browser browser = userAgent.getBrowser();
        String clientInfo =
                clientIP
                        + " - "
                        + os.getName()
                        + " - "
                        + browser.getName()
                        + "/"
                        + browser.getBrowserType().getName();
        return clientInfo;
    }

    private String getModuleName(String requestUri) {
        return oLogProperties.getCollector().getModuleNameMapping().get(requestUri);
    }

    private String getActionName(String action) {
        // 从配置文件的mapping中获取名称
        String actionName = oLogProperties.getCollector().getActionNameMapping().get(action);
        return actionName != null ? actionName : action;
    }
}
