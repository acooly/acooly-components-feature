package com.acooly.module.app.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.app.dao.AppVersionDao;
import com.acooly.module.app.domain.AppVersion;
import com.acooly.module.app.service.AppVersionService;
import org.springframework.stereotype.Service;

@Service("appVersionService")
public class AppVersionServiceImpl extends EntityServiceImpl<AppVersion, AppVersionDao>
        implements AppVersionService {

    @Override
    public AppVersion getLatest(String appCode, String deviceType) {
        return getEntityDao().getLatest(appCode, deviceType);
    }
}
