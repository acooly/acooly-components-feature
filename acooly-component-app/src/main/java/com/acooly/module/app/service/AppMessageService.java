package com.acooly.module.app.service;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.service.EntityService;
import com.acooly.module.app.domain.AppMessage;

/**
 * 群发消息 Service
 *
 * <p>Date: 2015-11-04 13:30:36
 *
 * @author Acooly Code Generator
 */
public interface AppMessageService extends EntityService<AppMessage> {

    PageInfo<AppMessage> query(PageInfo<AppMessage> pageInfo, String userName);
}
