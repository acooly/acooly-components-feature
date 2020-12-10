/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * shuijing 2018-03-29 11:10 创建
 */
package com.acooly.module.security.shiro.freemarker.tags;

import com.acooly.module.security.shiro.freemarker.SecurityBaseTag;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Map;

/**
 * @author shuijing
 */
@Slf4j
public class PrincipalTag extends SecurityBaseTag {

    String getType(Map params) {
        return getParameter(params, "type");
    }

    String getProperty(Map params) {
        return getParameter(params, "property");
    }

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body)
            throws IOException, TemplateException {
        String result = null;
        if (getSubject() != null) {
            Object principal;
            if (getType(params) == null) {
                principal = getSubject().getPrincipal();
            } else {
                principal = getPrincipalFromClassName(params);
            }
            if (principal != null) {
                String property = getProperty(params);
                if (property == null) {
                    result = principal.toString();
                } else {
                    result = getPrincipalProperty(principal, property);
                }
            }
            if (result != null) {
                try {
                    env.getOut().write(result);
                } catch (IOException ex) {
                    throw new TemplateException("Error writing [" + result + "] to Freemarker.", ex, env);
                }
            }
        }
    }

    private Object getPrincipalFromClassName(Map param) {
        String type = getType(param);
        try {
            Class<?> typeClass = Class.forName(type);
            return getSubject().getPrincipals().oneByType(typeClass);
        } catch (ClassNotFoundException e) {
            log.error("Unable to find class for name {}", type, e);
        }
        return null;
    }

    String getPrincipalProperty(Object principal, String property) throws TemplateModelException {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(principal.getClass());
            for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
                if (descriptor.getName().equals(property)) {
                    Object value = descriptor.getReadMethod().invoke(principal, (Object[]) null);
                    return String.valueOf(value);
                }
            }
            throw new TemplateModelException(
                    "Property ["
                            + property
                            + "] not found in principal of type ["
                            + principal.getClass().getName()
                            + "]");

        } catch (Exception e) {
            throw new TemplateModelException(
                    "Error reading property ["
                            + property
                            + "] from principal of type ["
                            + principal.getClass().getName()
                            + "]",
                    e);
        }
    }
}
