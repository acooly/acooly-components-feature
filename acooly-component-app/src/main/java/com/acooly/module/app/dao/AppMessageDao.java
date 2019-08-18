package com.acooly.module.app.dao;

import com.acooly.module.app.domain.AppMessage;
import com.acooly.module.jpa.EntityJpaDao;

/**
 * 群发消息 JPA Dao
 *
 * <p>Date: 2015-11-04 13:30:36
 *
 * @author Acooly Code Generator
 */
public interface AppMessageDao extends EntityJpaDao<AppMessage, Long>, AppMessageDaoCustom {
}
