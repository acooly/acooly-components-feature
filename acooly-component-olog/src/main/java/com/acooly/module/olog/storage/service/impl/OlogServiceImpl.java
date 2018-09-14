package com.acooly.module.olog.storage.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.olog.storage.dao.OlogDao;
import com.acooly.module.olog.storage.domain.OlogEntity;
import com.acooly.module.olog.storage.service.OlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OlogServiceImpl extends EntityServiceImpl<OlogEntity, OlogDao> implements OlogService {
    @Autowired
    private OlogArchiveService ologArchiveService;

    @Transactional
    @Override
    public void cleanup(Date beforeDate) {
        getEntityDao().cleanup(beforeDate);
    }

    @Override
    public void insert(OlogEntity olog) {
        getEntityDao().save(olog);
    }

    @Override
    public void archive() {
        ologArchiveService.archive();
    }
}
