package com.acooly.module.ofile.dao;

import com.acooly.module.jpa.EntityJpaDao;
import com.acooly.module.ofile.domain.OnlineFile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * app_banner JPA Dao
 *
 * <p>Date: 2015-05-12 13:39:31
 *
 * @author Acooly Code Generator
 */
@Repository
public interface OnlineFileDao extends EntityJpaDao<OnlineFile, Long> {

    List<OnlineFile> findByObjectIdIn(List<String> objectId);

    List<OnlineFile> findByBucketName(String BucketName);




}
