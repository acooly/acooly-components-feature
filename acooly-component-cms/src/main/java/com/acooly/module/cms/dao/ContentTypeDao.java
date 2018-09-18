package com.acooly.module.cms.dao;

import com.acooly.module.cms.domain.ContentType;
import com.acooly.module.jpa.EntityJpaDao;
import org.springframework.data.jpa.repository.Query;

/**
 * 内容类型 JPA Dao Date: 2013-07-12 15:06:45
 *
 * @author Acooly Code Generator
 */
public interface ContentTypeDao extends EntityJpaDao<ContentType, Long> {

    @Query("select Max(code) from ContentType where parent is null")
    String getLevelMax(Long parentId);
}
