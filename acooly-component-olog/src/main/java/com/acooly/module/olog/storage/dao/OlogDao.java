package com.acooly.module.olog.storage.dao;

import com.acooly.module.jpa.EntityJpaDao;
import com.acooly.module.olog.storage.domain.OlogEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * 操作日志 JPA Dao
 *
 * <p>Date: 2013-02-28 22:59:16
 *
 * @author Acooly Code Generator
 */
public interface OlogDao extends EntityJpaDao<OlogEntity, Long> {

    @Modifying
    @Query("delete from OlogEntity where operateTime < ?1")
    void cleanup(Date beforeDate);
}
