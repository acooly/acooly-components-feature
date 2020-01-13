package com.acooly.module.ofile.dao;

import com.acooly.module.jpa.EntityJpaDao;
import com.acooly.module.ofile.domain.OnlineFile;

/**
 * app_banner JPA Dao
 *
 * <p>Date: 2015-05-12 13:39:31
 *
 * @author Acooly Code Generator
 */
public interface OnlineFileDao extends EntityJpaDao<OnlineFile, Long> {

    OnlineFile findByObjectId(String objectId);
}
