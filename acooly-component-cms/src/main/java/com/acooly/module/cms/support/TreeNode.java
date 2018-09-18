package com.acooly.module.cms.support;

/**
 * ZTree 节点抽象
 *
 * <p>Created by zhangpu on 2015/10/24.
 */
public class TreeNode {

    private Long id;
    private Long parentId;
    private String name;
    private String value;
    private String icon;

    public TreeNode() {
    }

    public TreeNode(Long id, Long parentId, String name, String value) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
