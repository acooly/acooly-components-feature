package com.acooly.module.cms.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.cms.dao.ContentBodyDao;
import com.acooly.module.cms.domain.ContentBody;
import com.acooly.module.cms.service.ContentBodyService;
import org.springframework.stereotype.Service;

@Service("contentBodyService")
public class ContentBodyServiceImpl extends EntityServiceImpl<ContentBody, ContentBodyDao>
        implements ContentBodyService {
}
