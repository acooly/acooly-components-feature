package com.acooly.module.cms.dao;

import com.acooly.module.cms.domain.Content;
import com.acooly.module.jpa.EntityJpaDao;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * 内容主表 JPA Dao Date: 2013-07-12 15:06:46
 *
 * @author Acooly Code Generator
 */
public interface ContentDao extends EntityJpaDao<Content, Long> {

    @Query("select count(*) from Content where pubDate > ?1 and status = " + Content.STATUS_ENABLED)
    long getCountByGtPubDate(Date pubDate);

    @Query(value = "select t1.* from cms_content t1,cms_content_type t2 where t1.TYPE = t2.ID and t2.CODE = ?1 and t1.status = " + Content.STATUS_ENABLED + " order by t1.PUB_DATE desc limit 0,?2", nativeQuery = true)
    List<Content> top(String typeCode, int count);

    Content findByKeycode(String keycode);
}
