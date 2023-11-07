package com.acooly.module.security.web;

import com.acooly.core.common.web.support.JsonResult;
import com.acooly.module.security.config.FrameworkProperties;
import com.acooly.module.security.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统框架参数
 *
 * @author zhangpu
 * @see SystemController#init()
 */
@Deprecated
@Controller
@RequestMapping(value = "/security/config")
public class SecurityConfigController {

    @Autowired
    private FrameworkProperties frameworkProperties;

    /**
     * 授权功能顶级菜单
     *
     * @return
     */
    @RequestMapping("index")
    @ResponseBody
    public JsonResult authorisedMenus() {
        JsonResult result = new JsonResult();
        result.appendData("config", frameworkProperties);
        result.appendData("username", ShiroUtils.getCurrentUser().getUsername());
        return result;
    }
}
