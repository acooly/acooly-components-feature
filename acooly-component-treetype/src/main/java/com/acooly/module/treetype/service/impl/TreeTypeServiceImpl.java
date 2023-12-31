/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-11-03
 */
package com.acooly.module.treetype.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.exception.CommonErrorCodes;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Asserts;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Exceptions;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.arithmetic.tree.QuickTree;
import com.acooly.module.treetype.dao.TreeTypeDao;
import com.acooly.module.treetype.entity.TreeType;
import com.acooly.module.treetype.entity.TreeTypeErrorCode;
import com.acooly.module.treetype.service.TreeTypeService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 树形分类 Service实现
 * <p>
 * Date: 2019-11-03 08:46:48
 *
 * @author zhangpu
 */
@Slf4j
@Service("treeTypeService")
public class TreeTypeServiceImpl extends EntityServiceImpl<TreeType, TreeTypeDao> implements TreeTypeService {

    private static final String TREE_CACH_NAME = "acooly.compoment.treetype.tree";

    /**
     * 新增节点
     *
     * @param o
     * @throws BusinessException
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void save(TreeType o) throws BusinessException {

        // 设置parentId默认值
        if (o.getParentId() == null) {
            o.setParentId(TreeType.TOP_PARENT_ID);
        }

        // 设置默认theme
        if (Strings.isBlank(o.getTheme())) {
            o.setTheme(TreeType.DEFAULT_THEME);
        }

        // 检查code是否唯一（theme+code）
        if (findByCode(o.getTheme(), o.getCode()) != null) {
            log.error("多级分类 方案({})的编码({})不唯一", o.getTheme(), o.getCode());
            throw new BusinessException(CommonErrorCodes.OBJECT_NOT_UNIQUE);
        }

        // 更新父节点计数
        TreeType parent = null;
        if (o.getParentId() > TreeType.TOP_PARENT_ID) {
            parent = get(o.getParentId());
            parent.setSubCount(parent.getSubCount() + 1);
            update(parent);
        }

        // 设置当前节点的path(以目录服务)
        if (Strings.isBlank(o.getPath())) {
            // 有parent
            if (parent != null) {
                String parentPath = parent.getPath();
                if (Strings.isBlank(parentPath)) {
                    parentPath = TreeType.TOP_PARENT_PATH;
                }
                o.setPath(parentPath + parent.getId() + TreeType.TOP_PARENT_PATH);

            } else {
                o.setPath(TreeType.TOP_PARENT_PATH);
            }
        }
        // 设置当前时间为新节点的排序值
        if (o.getSortTime() == null) {
            o.setSortTime(System.currentTimeMillis());
        }
        super.save(o);
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void removeById(Serializable id) throws BusinessException {
        TreeType treeType = get(id);
        // 检查是否有子节点
        if (treeType.getSubCount() > 0) {
            log.info("删除节点 [失败] 原因: {}, 节点: {}", TreeTypeErrorCode.EXIST_SUB_NODE_CONFLICT
                    , treeType);
            Exceptions.rethrow(TreeTypeErrorCode.EXIST_SUB_NODE_CONFLICT);
        }
        // 更新父节点计数
        if (!TreeType.TOP_PARENT_ID.equals(treeType.getParentId())) {
            TreeType parent = get(treeType.getParentId());
            parent.setSubCount(parent.getSubCount() - 1);
            update(parent);
        }
        // 删除本节点
        super.removeById(id);
    }

    /**
     * 批量删除
     * <p>
     * 考虑后台维护场景，性能要求不大，删除逻辑较多，直接重构为循环删除
     *
     * @param ids
     * @throws BusinessException
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void removes(Serializable... ids) throws BusinessException {
        List<TreeType> treeTypes = getEntityDao().find("IN_id", Lists.newArrayList(ids));
        Long firstParent = Collections3.getFirst(treeTypes).getParentId();
        for (TreeType treeType : treeTypes) {
            if (!firstParent.equals(treeType.getParentId())) {
                log.info("删除节点 [失败] 原因:{}, 节点: {}", TreeTypeErrorCode.SAME_LEVEL_BATCH_DELETE_CONFLICT
                        , treeType);
                Exceptions.rethrow(TreeTypeErrorCode.SAME_LEVEL_BATCH_DELETE_CONFLICT);
            }
        }
        for (Serializable id : ids) {
            removeById(id);
        }
    }


    @Override
    public List<TreeType> level(Long parentId) {
        return level(null, parentId);
    }

    @Override
    public List<TreeType> level(String theme, Long parentId) {
        Long queryParentId = Strings.isBlankDefault(parentId, TreeType.TOP_PARENT_ID);
        String queryTheme = Strings.isBlankDefault(theme, TreeType.DEFAULT_THEME);
        Map<String, Object> map = Maps.newHashMap();
        map.put("EQ_theme", queryTheme);
        map.put("EQ_parentId", queryParentId);
        Map<String, Boolean> sortMap = Maps.newLinkedHashMap();
        sortMap.put("sortTime", false);
        sortMap.put("id", false);
        return query(map, sortMap);
    }

    @Override
    public List<TreeType> tree(String theme, String rootPath, Comparator comparator) {
        Map<String, Object> params = Maps.newHashMap();
        theme = Strings.isBlankDefault(theme, TreeType.DEFAULT_THEME);
        rootPath = Strings.isBlankDefault(rootPath, TreeType.TOP_PARENT_PATH);
        params.put("EQ_theme", theme);
        params.put("RLIKE_path", rootPath);
        List<TreeType> treeTypes = query(params, null);
        return doTree(treeTypes, comparator);
    }

    @Override
    public List<TreeType> tree(@NotNull String theme, String rootPath) {
        return tree(theme, rootPath, null);
    }


    @Override
    public List<TreeType> tree(String theme, Long rootId, Comparator comparator) {
        String path = null;
        if (rootId != null && !rootId.equals(TreeType.TOP_PARENT_ID)) {
            TreeType rootTreeType = get(rootId);
            if (rootTreeType == null) {
                path = rootTreeType.getPath();
            }
        }
        return tree(theme, path, comparator);
    }

    @Override
    public List<TreeType> tree(@NotNull String theme, Long rootId) {
        return tree(theme, rootId, null);
    }

    @Override
    public List<TreeType> tree(String rootPath) {
        return tree(TreeType.DEFAULT_THEME, rootPath, null);
    }

    @Override
    public List<TreeType> tree(String rootPath, Comparator comparator) {
        return tree(TreeType.DEFAULT_THEME, rootPath, comparator);
    }

    @Override
    public List<TreeType> tree(Long rootId) {
        return tree(TreeType.DEFAULT_THEME, rootId, null);
    }

    @Override
    public List<TreeType> tree(Long rootId, Comparator comparator) {
        return tree(TreeType.DEFAULT_THEME, rootId, comparator);
    }

    @Override
    public TreeType findByCode(String typeCode) {
        return findByCode(null, typeCode);
    }

    @Override
    public TreeType findByCode(String theme, String typeCode) {
        theme = Strings.defaultString(theme, TreeType.DEFAULT_THEME);
        Asserts.notEmpty(theme, "主题");
        Asserts.notEmpty(typeCode, "类型编码");
        return getEntityDao().findByThemeAndCode(theme, typeCode);
    }

    /**
     * 排序处理
     * 每层内排序规则：sortTime desc, id desc
     */
    protected List<TreeType> doTree(List<TreeType> treeTypeLists) {
        return doTree(treeTypeLists, null);
    }

    protected List<TreeType> doTree(List<TreeType> treeTypeLists, Comparator comparator) {
        if (comparator == null) {
            comparator = Comparator.nullsLast(Comparator.comparing((TreeType t) -> -t.getSortTime())
                    .thenComparing(t -> -t.getId()));
        }
        return QuickTree.quickTree(treeTypeLists, TreeType.TOP_PARENT_ID, comparator);
    }

}
