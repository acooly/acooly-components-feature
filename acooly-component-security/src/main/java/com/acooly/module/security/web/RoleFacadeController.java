package com.acooly.module.security.web;

import com.acooly.module.security.config.SecurityProperties;
import com.acooly.module.security.service.SSOAuthzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * role facade
 *
 * @author shuijing
 */
@Slf4j
@Controller
@RequestMapping(value = "/role/facade")
@ConditionalOnProperty(
        value = SecurityProperties.PREFIX + ".shiro.auth.enable",
        matchIfMissing = true
)
public class RoleFacadeController {

    @Autowired
    private SSOAuthzService ssoAuthzService;

    @RequestMapping("isPermitted")
    @ResponseBody
    public Boolean isPermitted(String uri, String username) {
        try {
            return ssoAuthzService.permitted(uri, username);
        } catch (Exception e) {
            log.error("检查权限异常", e);
            return false;
        }
    }

    @RequestMapping("hasAnyRoles")
    @ResponseBody
    public Boolean hasAnyRoles(String roleNames, String username) {
        try {
            return ssoAuthzService.hasAnyRoles(roleNames, username);
        } catch (Exception e) {
            log.error("SSO 检查角色异常", e);
            return false;
        }
    }
}
