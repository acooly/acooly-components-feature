package com.acooly.module.security.dao;

import com.acooly.module.jpa.EntityJpaDao;
import com.acooly.module.security.domain.Portallet;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 桌面管理 JPA Dao
 *
 * <p>Date: 2013-05-02 15:40:57
 *
 * @author Acooly Code Generator
 */
public interface PortalletDao extends EntityJpaDao<Portallet, Long> {

    @Query("from Portallet where userName is null or userName = ?1")
    List<Portallet> queryByUserName(String userName);
}
