package com.acooly.module.app.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.app.domain.AppStartGuide;

import java.util.List;

/**
 * app_start_guide Service
 *
 * <p>Date: 2015-05-22 14:44:16
 *
 * @author Acooly Code Generator
 */
public interface AppStartGuideService extends EntityService<AppStartGuide> {
    List<AppStartGuide> loadValidGuides();
}
