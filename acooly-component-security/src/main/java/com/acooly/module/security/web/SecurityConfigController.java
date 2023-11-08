package com.acooly.module.security.web;

import com.acooly.module.security.config.FrameworkProperties;
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
    public FrameworkProperties authorisedMenus() {
        return frameworkProperties;
    }
}
