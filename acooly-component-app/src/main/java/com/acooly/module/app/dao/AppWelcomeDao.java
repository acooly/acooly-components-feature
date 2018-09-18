package com.acooly.module.app.dao;

import com.acooly.module.app.domain.AppWelcome;
import com.acooly.module.jpa.EntityJpaDao;
import org.springframework.data.jpa.repository.Query;

/**
 * app_welcome JPA Dao
 *
 * <p>Date: 2015-05-12 13:39:30
 *
 * @author Acooly Code Generator
 */
public interface AppWelcomeDao extends EntityJpaDao<AppWelcome, Long> {

    @Query(value = "select * from app_welcome order by id desc limit 0,1", nativeQuery = true)
    AppWelcome getLatestOne();
}
