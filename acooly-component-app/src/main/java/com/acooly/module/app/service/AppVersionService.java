package com.acooly.module.app.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.app.domain.AppVersion;

/**
 * 手机客户端版本 Service
 *
 * <p>Date: 2014-10-25 23:16:15
 *
 * @author Acooly Code Generator
 */
public interface AppVersionService extends EntityService<AppVersion> {

    AppVersion getLatest(String appCode, String deviceType);
}
