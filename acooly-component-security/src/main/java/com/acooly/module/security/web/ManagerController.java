package com.acooly.module.security.web;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.boot.Env;
import com.acooly.core.common.olog.annotation.Olog;
import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.module.security.captche.SecurityCaptchaManager;
import com.acooly.module.security.config.FrameworkProperties;
import com.acooly.module.security.config.SecurityProperties;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.dto.UserFavorites;
import com.acooly.module.security.service.ResourceService;
import com.acooly.module.security.service.UserFavoriteService;
import com.acooly.module.security.service.UserService;
import com.acooly.module.security.utils.ShiroUtils;
import com.acooly.module.security.utils.jwts;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.acooly.module.security.utils.jwts.SIGN_KEY;

/**
 * 登录及主页
 *
 * <p>真正登录的POST请求由Filter完成,
 *
 * @author zhangpu
 */
@Controller
@RequestMapping(value = "/manage")
@ConditionalOnProperty(
        value = SecurityProperties.PREFIX + ".shiro.auth.enable",
        matchIfMissing = true
)
@Olog.Ignore
public class ManagerController extends AbstractJsonEntityController<User, UserService> {

    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private FrameworkProperties frameworkProperties;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private SecurityCaptchaManager securityCaptchaManager;
    @Autowired(required = false)
    private UserFavoriteService userFavoriteService;

    @RequestMapping("")
    public String none(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "redirect:/manage/index.html";
    }


    @Override
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        request.setAttribute("securityConfig", frameworkProperties);
        Subject subject = SecurityUtils.getSubject();
//        request.getSession(true).setAttribute("securityConfig", frameworkProperties);
        if (subject.isAuthenticated()) {
            // 如果已经登录的情况，直接回到主框架界面
            doExtendResources(request, model);
            User user = (User) subject.getPrincipal();
            user = getEntityService().get(user.getId());
            String roleName = null;
            if (Collections3.isNotEmpty(user.getRoles())) {
                roleName = Collections3.getFirst(user.getRoles()).getDescn();
            }
            model.addAttribute("user", user);
            model.addAttribute("roleName", roleName);
            // theme处理
            String acoolyTheme = (String) request.getSession().getAttribute("acoolyTheme");
            String refreshTheme = Servlets.getParameter(request, "acoolyTheme");
            if (Strings.isNotBlank(refreshTheme)) {
                acoolyTheme = refreshTheme;
            }
            if (Strings.isBlank(acoolyTheme)) {
                acoolyTheme = frameworkProperties.getDefaultTheme();
            }
            request.getSession().setAttribute("acoolyTheme", acoolyTheme);
            boolean isOnline = (Lists.newArrayList(Apps.getEnvironment().getActiveProfiles()).contains(Env.online.name()));
            model.addAttribute("isOnline", isOnline);

            if (Strings.equals(acoolyTheme, "easyui")) {
                return "/manage/index_easyui";
            }

            // 新版直接返回菜单数据
            model.addAttribute("menu", resourceService.getAuthorizedResourceNode(user.getId()));
            if (frameworkProperties.isEnableFavorite()) {
                List<UserFavorites> favorites = userFavoriteService.queryFavorites(user.getId());
                if (Collections3.isNotEmpty(favorites)) {
                    model.addAttribute("favorites", favorites);
                }
            }
            return "/manage/index_adminlte3";
        } else {
            // 如果没有登录的首次进入登录界面，直接返回到登录界面。
//            request.getSession(true).setAttribute("securityConfig", frameworkProperties);
            return "/manage/login";
        }
    }

    @RequestMapping("theme")
    @ResponseBody
    public JsonResult saveTheme(HttpServletRequest request) {
        String acoolyTheme = Servlets.getParameter(request, "acoolyTheme");
        if (Strings.isNoneBlank(acoolyTheme)) {
            request.getSession().setAttribute("acoolyTheme", acoolyTheme);
        }
        return new JsonResult();
    }

    /**
     * GET访问，直接进入登陆界面
     *
     * @return
     */
    @RequestMapping(value = "login")
    public String login(HttpServletRequest request, Model model) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            /** 如果已经登录的情况，其他系统集成sso则重定向目标地址，否则直接跳主页 */
            String targetUrl = Servlets.getRequestParameter(jwts.KEY_TARGETURL);
//             targetUrl = (String) ServletUtil.getSessionAttribute(jwts.KEY_TARGETURL);
            if (StringUtils.isNotBlank(targetUrl)) {
                String jwt = jwts.getJwtFromCookie(request.getCookies());
                if (!StringUtils.isEmpty(jwt)) {
                    Jwt<Header, Claims> jws = null;
                    try {
                        jws = jwts.parseJws(jwt, SIGN_KEY);
                    } catch (Exception e) {
                        logger.error("解析jwt错误", e);
                    }
                    boolean timeout = jwts.validateTimeout(jws);
                    if (timeout) {
                        String newJws = jwts.refreshJwt(jws);
                        return "redirect:" + fomartRederectUrl(targetUrl, newJws);
                    } else {
                        return "redirect:" + fomartRederectUrl(targetUrl, jwt);
                    }
                } else {
                    User user = (User) subject.getPrincipal();
                    if (user != null) {
                        String newJwt = genarateJwt(user);
                        jwts.addJwtCookie(Servlets.getResponse(), newJwt, jwts.getDomainName());
                        return "redirect:" + fomartRederectUrl(targetUrl, newJwt);
                    }
                }
            }
            return "redirect:/manage/index.html";
        } else {
            // 如果没有登录的首次进入登录界面，直接返回到登录界面。
            request.setAttribute("securityConfig", frameworkProperties);
            request.setAttribute("securityProperties", securityProperties);
            request.setAttribute("passwordRegex", frameworkProperties.getPasswordStrength().getRegexForJs());
            request.setAttribute("notFirstVerify", !securityCaptchaManager.isFirstVerify(request));
            request.setAttribute("loginSmsEnable", "true");
            // 单用户登录提出后的错误消息提示
            String errorKey = Servlets.getParameter("errorKey");
            if (Strings.equals(errorKey, "kickout")) {
                model.addAttribute("message", "已有相同用户登录，强制注销。");
            }
            return "/manage/login";
        }
    }

    @RequestMapping(value = "onLoginSuccess")
    @ResponseBody
    public JsonResult onLoginSuccess(HttpServletRequest request) {
        logger.debug("OnLoginSuccess, redirect to index.jsp");
        JsonResult jsonResult = new JsonResult();
        User user = ShiroUtils.getCurrentUser();
        if (user != null) {
            String jwt = genarateJwt(user);
            jwts.addJwtCookie(Servlets.getResponse(), jwt, jwts.getDomainName());

            HashMap<Object, Object> resmap = Maps.newHashMap();
            String targetUrl = (String) Servlets.getSessionAttribute(jwts.KEY_TARGETURL);
            if (StringUtils.isNotBlank(targetUrl)) {
                resmap.put("isRedirect", true);
                resmap.put(jwts.KEY_TARGETURL, fomartRederectUrl(targetUrl, jwt));
            } else {
                resmap.put("isRedirect", false);
                resmap.put(jwts.KEY_TARGETURL, "");
            }
            jsonResult.setData(resmap);
        }
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    private String genarateJwt(User user) {
        String username = user.getUsername();
        String subjectStr = "";
        try {
            subjectStr = JsonMapper.nonEmptyMapper().getMapper().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            logger.error("创建jwt时user转String失败", e.getMessage());
        }
        return jwts.createJwt(username, subjectStr);
    }

    private String fomartRederectUrl(String targetUrl, String jwt) {
        if (targetUrl.contains("?")) {
            targetUrl = String.format("%s&jwt=%s", targetUrl, jwt);
        } else {
            targetUrl = String.format("%s?jwt=%s", targetUrl, jwt);
        }
        return targetUrl;
    }

    @RequestMapping(value = "onLoginFailure")
    @ResponseBody
    public JsonResult onLoginFailure(HttpServletRequest request) {
        logger.debug("OnLoginFailure");
        // 获取Shiro的错误信息
        String obj = request.getParameter(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String message = request.getParameter("message");
        String msg = "";
        if (obj != null) {
            if ("org.apache.shiro.authc.UnknownAccountException".equals(obj)) {
                msg = "用户名或密码错误";
            } else if ("org.apache.shiro.authc.IncorrectCredentialsException".equals(obj)) {
                msg = "用户名或密码错误";
            } else if ("com.acooly.module.security.shiro.exception.InvaildCaptchaException".equals(obj)) {
                msg = "验证码错误";
            } else if ("org.apache.shiro.authc.AuthenticationException".equals(obj)) {
                msg = message;
            } else {
                msg = message;
            }
            logger.debug(msg + " --> " + obj + ": " + message);
        }
        JsonResult result = new JsonResult(obj, msg);
        result.appendData(Servlets.getParameters(request, null, false));
        return result;
    }

    /**
     * 会话过期，或未授权访问的情况的回调，框架配置中没有使用，而是使用遇到未授权访问直接注销返回到登录界面。 这里保留是为了，如果有需求可以配置框架返回到这里进行后续处理。
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "onUnauthorized")
    public String onUnauthorized(HttpServletRequest request) {
        return "/manage/error/403";
    }

    @RequestMapping(value = "north")
    public String north(HttpServletRequest request) {
        return "/manage/north";
    }

    @RequestMapping(value = "menu")
    public String menu(HttpServletRequest request) {
        return "/manage/menu";
    }

    private void doExtendResources(HttpServletRequest request, Model model) {
        Set<String> scripts = frameworkProperties.mergeScripts();
        if (Collections3.isNotEmpty(scripts)) {
            StringBuilder sb = new StringBuilder();
            for (String script : scripts) {
                sb.append("<script type=\"text/javascript\" src=\"" + script + "\" charset=\"utf-8\"></script>\n");
            }
            model.addAttribute("extendScripts", sb.toString());
        }
        Set<String> styles = frameworkProperties.mergeStyles();
        if (Collections3.isNotEmpty(styles)) {
            StringBuilder sb = new StringBuilder();
            for (String style : styles) {
                sb.append("<link rel=\"stylesheet\" href=\"" + style + "\">\n");
            }
            model.addAttribute("extendStyles", sb.toString());
        }
    }

}
