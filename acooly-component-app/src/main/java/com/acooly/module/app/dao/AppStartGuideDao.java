package com.acooly.module.app.dao;

import com.acooly.module.app.domain.AppStartGuide;
import com.acooly.module.app.enums.EntityStatus;
import com.acooly.module.jpa.EntityJpaDao;
import org.springframework.data.jpa.repository.Query;

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


    @Query(value = "select * from app_start_guide where sort_order >= ?1 and id <> ?2 limit 1", nativeQuery = true)
    AppStartGuide findBeforeOne(Long sortTime, Long id);
}
