/*
 * acooly.cn Inc.
 * Copyright (c) 2016 All Rights Reserved.
 * create by acooly
 * date:2016-12-19
 *
 */
package com.acooly.core.test.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.test.dao.AppDao;
import com.acooly.core.test.domain.App;
import com.acooly.core.test.service.AppService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * app Service实现
 *
 * <p>Date: 2016-12-19 21:09:09
 *
 * @author acooly
 */
@Service("appService")
@Transactional
public class AppServiceImpl extends EntityServiceImpl<App, AppDao> implements AppService {
}
