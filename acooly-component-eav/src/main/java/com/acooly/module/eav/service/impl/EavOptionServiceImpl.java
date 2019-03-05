/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-03-05
 */
package com.acooly.module.eav.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.eav.service.EavOptionService;
import com.acooly.module.eav.dao.EavOptionDao;
import com.acooly.module.eav.entity.EavOption;

/**
 * 属性选项 Service实现
 *
 * Date: 2019-03-05 18:52:41
 *
 * @author zhangpu
 *
 */
@Service("eavOptionService")
public class EavOptionServiceImpl extends EntityServiceImpl<EavOption, EavOptionDao> implements EavOptionService {

}
