package com.acooly.module.security.web;

import com.acooly.module.security.config.FrameworkProperties;
import com.acooly.module.security.config.FrameworkPropertiesHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统框架参数
 *
 * @author zhangpu
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
    public FrameworkProperties authorisedMenus(
            HttpServletRequest request, HttpServletResponse response) {
        return frameworkProperties;
    }
}
