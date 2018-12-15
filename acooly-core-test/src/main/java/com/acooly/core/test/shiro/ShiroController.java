package com.acooly.core.test.shiro;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiubo@yiji.com
 */
@RestController
@RequestMapping(value = "/shiro")
public class ShiroController {

    @RequestMapping("testPermission")
    public String testPermission() {
        if (SecurityUtils.getSubject().isPermitted("user:create")) {
            return "allow";
        } else {
            return "deny";
        }
    }
}
