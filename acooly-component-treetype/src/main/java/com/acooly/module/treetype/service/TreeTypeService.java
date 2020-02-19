/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-11-03
 *
 */
package com.acooly.module.treetype.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.treetype.entity.TreeType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 树形分类 Service接口
 * <p>
 * Date: 2019-11-03 08:46:48
 *
 * @author zhangpu
 */
public interface TreeTypeService extends EntityService<TreeType> {


    /**
     * 树形结构
     * 默认主题为：theme
     *
     * @param rootPath 根节点Path（为空，则默认：/）
     * @return
     */
    List<TreeType> tree(String rootPath);

    /**
     * 树形结构
     * 默认主题为：theme
     *
     * @param rootId 根节点Id（为空，则默认：0）
     * @return
     */
    List<TreeType> tree(Long rootId);

    /**
     * 树形结构
     *
     * @param theme    主题（为空，则默认：default）
     * @param rootPath 根节点Path（为空，则默认：/）
     * @return
     */
    List<TreeType> tree(@NotEmpty String theme, String rootPath);

    /**
     * 树形结构
     *
     * @param theme  主题（为空，则默认：default）
     * @param rootId 根节点Id（为空，则默认：0）
     * @return
     */
    List<TreeType> tree(@NotEmpty String theme, Long rootId);

    /**
     * 单层查询
     * （默认主题：default）
     *
     * @param parentId
     * @return
     */
    List<TreeType> level(Long parentId);

    /**
     * 单层查询
     *
     * @param parentId 为空：顶层（parentId=TOP_PARENT_ID）
     * @param theme    为空则：默认主题（theme=DEFAULT_THEME）
     * @return
     */
    List<TreeType> level(Long parentId, String theme);

    /**
     * 根据编码查询类型
     *
     * @param typeCode
     * @return
     */
    TreeType findByCode(String typeCode);

}