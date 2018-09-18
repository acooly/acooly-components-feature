package com.acooly.module.sso.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginAuthProcessor<T> {
    T loginAuthentication(
            String authentication,
            String adLoginUrl,
            HttpServletRequest request,
            HttpServletResponse response);
}
