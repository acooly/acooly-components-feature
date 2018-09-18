package com.acooly.module.app.service.impl;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.app.dao.AppMessageDao;
import com.acooly.module.app.domain.AppMessage;
import com.acooly.module.app.service.AppMessageService;
import org.springframework.stereotype.Service;

@Service("appMessageService")
public class AppMessageServiceImpl extends EntityServiceImpl<AppMessage, AppMessageDao>
        implements AppMessageService {

    @Override
    public PageInfo<AppMessage> query(PageInfo<AppMessage> pageInfo, String userName) {
        return getEntityDao().pageQueryUserMessages(pageInfo, userName);
    }
}
