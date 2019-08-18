package com.acooly.module.app.dao;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.module.app.domain.AppMessage;

/**
 * 推送消息 JPA Dao
 *
 * <p>Date: 2014-11-02 22:34:32
 *
 * @author Acooly Code Generator
 */
public interface AppMessageDaoCustom {

    PageInfo<AppMessage> pageQueryUserMessages(PageInfo<AppMessage> pageInfo, String userName);
}
