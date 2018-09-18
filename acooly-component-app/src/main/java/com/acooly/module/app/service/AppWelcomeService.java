package com.acooly.module.app.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.app.domain.AppWelcome;

/**
 * app_welcome Service
 *
 * <p>Date: 2015-05-12 13:39:30
 *
 * @author Acooly Code Generator
 */
public interface AppWelcomeService extends EntityService<AppWelcome> {

    AppWelcome getLatestOne();
}
