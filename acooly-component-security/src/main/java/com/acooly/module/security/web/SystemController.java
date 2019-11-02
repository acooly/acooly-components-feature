package com.acooly.module.security.web;

import com.acooly.core.common.olog.annotation.Olog;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Strings;
import com.acooly.module.security.config.FrameworkPropertiesHolder;
import com.acooly.module.security.config.SecurityProperties;
import com.acooly.module.security.domain.Portallet;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.dto.ResourceNode;
import com.acooly.module.security.service.PortalletService;
import com.acooly.module.security.service.ResourceService;
import com.acooly.module.security.service.UserService;
import com.acooly.module.security.shiro.realm.PathMatchPermission;
import com.acooly.module.security.utils.ShiroUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统功能，用户认证通过后可以访问的默认资源 包括：授权功能菜单，修改密码，查看个人的信息等
 *
 * @author zhangpu
 */
@Controller
@RequestMapping(value = "/manage/system/")
@ConditionalOnProperty(
        value = SecurityProperties.PREFIX + ".shiro.auth.enable",
        matchIfMissing = true
)
@Olog.Ignore
public class SystemController extends AbstractJsonEntityController<User, UserService> {

    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private PortalletService portalletService;

    /**
     * 授权功能顶级菜单
     *
     * @return
     */
    @Olog.Ignore
    @RequestMapping("authorisedMenus")
    @ResponseBody
    public List<ResourceNode> authorisedMenus(
            HttpServletRequest request, HttpServletResponse response) {
        User user = ShiroUtils.getCurrentUser();
        List<ResourceNode> resourceNodes = resourceService.getAuthorizedResourceNode(user.getId());
        return resourceNodes;
    }

    @RequestMapping(value = "menu")
    public String menu(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("menu", authorisedMenus(request, response));
        return "/manage/system/menu";
    }


    @RequestMapping(value = "portallets")
    @ResponseBody
    @Olog.Ignore
    public List<Portallet> portallets(
            Model model, HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = ShiroUtils.getCurrentUser();
            List<Portallet> portallets = portalletService.queryByUserName(user.getUsername());
            List<Portallet> authPortallets = Lists.newArrayList();
            for (Portallet p : portallets) {
                String url = p.getHref();
                if (StringUtils.isNotBlank(url)
                        && (Strings.endsWith(url, ".html") || Strings.endsWith(url, ".jsp"))) {
                    if (SecurityUtils.getSecurityManager()
                            .isPermitted(
                                    SecurityUtils.getSubject().getPrincipals(),
                                    "do" + PathMatchPermission.PART_DIVIDER_TOKEN + url)) {
                        authPortallets.add(p);
                    }
                } else {
                    authPortallets.add(p);
                }
            }
            return authPortallets;
        } catch (Exception e) {
            return Lists.newArrayList();
        }
    }

    @RequestMapping(value = "changePasswordView")
    public String changePasswordView(
            Model model, HttpServletRequest request, HttpServletResponse response) {
        User user = ShiroUtils.getCurrentUser();
        model.addAttribute("user", user);

        model.addAttribute("PASSWORD_REGEX", FrameworkPropertiesHolder.get().getPasswordRegex());
        model.addAttribute("PASSWORD_ERROR", FrameworkPropertiesHolder.get().getPasswordError());

        return "/manage/system/changePassword";
    }

    @RequestMapping(value = "changePassword")
    @ResponseBody
    public JsonResult changePassword(
            Model model, HttpServletRequest request, HttpServletResponse response) {
        String orginalPassword = request.getParameter("password");
        String newPassword = request.getParameter("newPassword");

        JsonResult result = new JsonResult();
        try {
            User user = ShiroUtils.getCurrentUser();
            if (user != null) {
                boolean checkResult = userService.validatePassword(user, orginalPassword);
                if (checkResult) {
                    userService.changePassword(user, newPassword);
                } else {
                    throw new RuntimeException("原始密码错误");
                }
            } else {
                throw new RuntimeException("当前用户会话过期，未找到对应用户");
            }
            result.setMessage("密码修改成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(e.getClass().toString());
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
