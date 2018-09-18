package com.acooly.module.cms.service;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.service.EntityService;
import com.acooly.module.cms.domain.Content;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 内容主表 Service
 *
 * <p>Date: 2013-07-12 15:06:46
 *
 * @author Acooly Code Generator
 */
public interface ContentService extends EntityService<Content> {

    void updateStatusBatch(Integer status, Serializable... ids);

    PageInfo<Content> search(PageInfo<Content> pageInfo, String q, int status);

    void delects(Serializable... ids);

    void moveTop(Long valueOf);

    void moveUp(Long valueOf);

    List<Content> topByTypeCode(String typeCode, int count);

    List<Content> topByTypeCodeNoLazy(String typeCode, int count);

    Content getLatestByTypeCode(String typeCode);

    Content getLatestByTypeCode(String typeCode, String carrierCode);

    Content getLatestByTypeCodeNoLazy(String typeCode);

    Content getByKeycode(String keycode);

    Content getByKeycodeNoLazy(String keycode);

    Content getByIdNoLazy(Long id);

}
