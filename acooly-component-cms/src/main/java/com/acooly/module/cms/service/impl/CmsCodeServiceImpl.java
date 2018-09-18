/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-04-26
 */
package com.acooly.module.cms.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.cms.dao.CmsCodeDao;
import com.acooly.module.cms.domain.CmsCode;
import com.acooly.module.cms.service.CmsCodeService;
import org.springframework.stereotype.Service;

/**
 * 编码 Service实现
 *
 * <p>Date: 2017-04-26 17:16:38
 *
 * @author acooly
 */
@Service("cmsCodeService")
public class CmsCodeServiceImpl extends EntityServiceImpl<CmsCode, CmsCodeDao>
        implements CmsCodeService {
}
