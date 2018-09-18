package com.acooly.module.app.dao;

import com.acooly.module.app.domain.AppStartGuide;
import com.acooly.module.app.enums.EntityStatus;
import com.acooly.module.jpa.EntityJpaDao;

import java.util.List;

/**
 * app_start_guide JPA Dao
 *
 * <p>Date: 2015-05-22 14:44:16
 *
 * @author Acooly Code Generator
 */
public interface AppStartGuideDao extends EntityJpaDao<AppStartGuide, Long> {
    List<AppStartGuide> findByStatusOrderBySortOrderAsc(EntityStatus status);
}
