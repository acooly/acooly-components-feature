package com.acooly.module.app.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.app.dao.AppCrashDao;
import com.acooly.module.app.domain.AppCrash;
import com.acooly.module.app.service.AppCrashService;
import org.springframework.stereotype.Service;

@Service("appCrashService")
public class AppCrashServiceImpl extends EntityServiceImpl<AppCrash, AppCrashDao>
        implements AppCrashService {
}
