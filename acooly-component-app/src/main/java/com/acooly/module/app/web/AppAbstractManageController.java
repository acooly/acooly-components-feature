/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-02-24 04:42 创建
 */
package com.acooly.module.app.web;

import com.acooly.core.common.domain.Entityable;
import com.acooly.core.common.service.EntityService;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.utils.Strings;
import com.acooly.module.app.AppProperties;
import com.acooly.module.ofile.OFileProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * @author acooly
 */
@Slf4j
public abstract class AppAbstractManageController<T extends Entityable, M extends EntityService<T>>
        extends AbstractJQueryEntityController<T, M> {

    @Autowired
    protected OFileProperties oFileProperties;

    @Autowired
    protected AppProperties appProperties;

    /**
     * 获取APP模块存储路径
     *
     * @return
     */
    protected String getStorageRoot() {
        String home = oFileProperties.getStorageRoot() + File.separator + getAppStorageRoot();
        File file = new File(home);
        if (!file.exists()) {
            file.mkdirs();
        }
        return home;
    }

    @Override
    protected UploadConfig getUploadConfig() {
        UploadConfig config = super.getUploadConfig();
        String storageRoot = getStorageRoot();
        config.setStorageRoot(storageRoot);
        config.setStorageNameSpace(oFileProperties.getStorageNameSpace());
        config.setUseMemery(false);
        config.setAllowExtentions("jpg,gif,png");
        return config;
    }

    /**
     * APP模块的存储相对路径
     *
     * @return
     */
    protected String getAppStorageRoot() {
        return appProperties.getStoragePath();
    }

    protected String getDatabasePath(UploadResult uploadResult) {
        String filePath = uploadResult.getFile().getPath();
        filePath = Strings.replace(filePath, "\\", "/");
        String rootPath = new File(oFileProperties.getStorageRoot()).getPath();
        rootPath = Strings.replace(rootPath, "\\", "/");
        String relativePath = StringUtils.substringAfter(filePath, rootPath);
        if (!Strings.startsWith(relativePath, "/")) {
            relativePath = "/" + relativePath;
        }
        return relativePath;
    }

    public static String convertCharSet(HttpServletRequest request, String parameter) {
        String parameterValue = request.getParameter(parameter);
        try {
            String ssoEnable = request.getServletContext().getInitParameter("ssoEnable");
            if (StringUtils.isNotEmpty(ssoEnable) && Boolean.TRUE.toString().equals(ssoEnable)) {
                byte[] parameterValueBytes = parameterValue.getBytes("iso-8859-1");
                String tt = new String(parameterValueBytes, "utf-8");
                parameterValue = tt;
            }
        } catch (UnsupportedEncodingException e) {
            log.error("转字符集失败", e.getMessage());
        }
        return parameterValue;
    }
}
