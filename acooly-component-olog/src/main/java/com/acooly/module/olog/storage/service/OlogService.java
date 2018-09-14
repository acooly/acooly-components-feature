package com.acooly.module.olog.storage.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.olog.storage.domain.OlogEntity;

import java.util.Date;

/**
 * 操作日志 Service
 *
 * <p>Date: 2013-02-28 22:59:16
 *
 * @author Acooly Code Generator
 */
public interface OlogService extends EntityService<OlogEntity> {

    void cleanup(Date beforeDate);

    void insert(OlogEntity olog);

    void archive();
}
