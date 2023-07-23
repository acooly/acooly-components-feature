package com.acooly.module.ofile.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.ofile.domain.OnlineFile;

import java.util.List;

/**
 * app_banner Service
 *
 * <p>Date: 2015-05-12 13:39:31
 *
 * @author Acooly Code Generator
 */
public interface OnlineFileService extends EntityService<OnlineFile> {

    /**
     * 通过id查询，返回结果会按存储方式填充对应的可访问路径
     *
     * @param id
     * @return
     */
    OnlineFile findById(Long id);


    /**
     * 通过objectId查询，返回结果会按存储方式填充对应的可访问路径
     *
     * @param objectId
     * @return
     */
    OnlineFile findByObjectId(String objectId);


    /**
     * 通过objectId查询，返回结果会按存储方式填充对应的可访问路径
     *
     * @param objectIds
     * @return
     */
    List<OnlineFile> findByObjectIds(List<String> objectIds);

    /**
     * 替换为 findByObjectIds
     * @param objectId
     * @return
     */
    @Deprecated
    List<OnlineFile> findByobjectId(List<String> objectId);

    /**
     * 通过ids查询，返回结果会按存储方式填充对应的可访问路径
     *
     * @param ids
     * @return
     */
    public List<OnlineFile> findById(List<Long> ids);

    /**
     * 通过文件路径，桶名称（当使用obs且操作多桶时建议传入，本地存储和单桶时可为空），返回唯一一条记录
     *
     * @param filePath
     * @param bucketName
     * @return
     */
    OnlineFile findByFilePathAndBucket(String filePath, String bucketName);
}
