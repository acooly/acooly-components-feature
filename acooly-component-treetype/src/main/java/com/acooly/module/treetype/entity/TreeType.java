/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-11-03
 */
package com.acooly.module.treetype.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.domain.Sortable;
import com.acooly.core.utils.arithmetic.tree.TreeNode;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 树形分类 Entity
 *
 * @author zhangpu
 * Date: 2019-11-03 08:46:48
 */
@Entity
@Table(name = "ac_tree_type")
@Getter
@Setter
public class TreeType extends AbstractEntity implements TreeNode<TreeType>, Sortable {

    public static final Long TOP_PARENT_ID = 0L;
    public static final String TOP_PARENT_PATH = "/";

    public static final String DEFAULT_THEME = "default";

    /**
     * 主题
     */
    @Size(max = 45)
    private String theme = DEFAULT_THEME;

    /**
     * 父类型ID
     */
    private Long parentId = TOP_PARENT_ID;

    /**
     * 搜索路径
     */
    @Size(max = 128)
    private String path;

    /**
     * 排序值
     */
    private Long sortTime;

    /**
     * 类型编码
     */
    @Size(max = 64)
    private String code;

    /**
     * 类型名称
     */
    @NotEmpty
    @Size(max = 32)
    private String name;

    /**
     * 子节点数量
     */
    private Integer subCount = 0;

    /**
     * 备注
     */
    @Size(max = 128)
    private String comments;

    /**
     * TreeNode操作非持久化临时属性
     */
    @Transient
    private List<TreeType> children = Lists.newArrayList();

    /**
     * easyui的treegrid专用属性
     */
    @Transient
    public String getState() {
        return subCount > 0 ? "closed" : "open";
    }

    /**
     * name的别名
     * 方便前端组件在树形结构时直接使用
     *
     * @return
     */
    @Transient
    public String getText() {
        return this.name;
    }

    @Override
    public List<TreeType> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<TreeType> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(theme)
                .add("id", getId())
                .add("pid", parentId)
                .add("code", code)
                .add("name", name)
                .toString();
    }
}
