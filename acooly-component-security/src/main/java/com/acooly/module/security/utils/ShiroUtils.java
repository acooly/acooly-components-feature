package com.acooly.module.security.utils;

import com.acooly.module.security.domain.User;
import org.apache.shiro.SecurityUtils;

public class ShiroUtils {
    public static User getCurrentUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
}
