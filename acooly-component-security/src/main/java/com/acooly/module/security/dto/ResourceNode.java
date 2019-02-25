package com.acooly.module.security.dto;

import com.acooly.core.utils.Strings;
import com.acooly.module.security.config.FrameworkProperties;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ResourceNode {

    private Long id;
    private Long parentId;
    private String name;
    private String type;
    private int showMode = FrameworkProperties.SHOW_MODE_AJAXLOAD;
    private Date orderTime;
    private String value;
    private String icon;
    private String iconSkin;
    private int showState = FrameworkProperties.SHOW_STATE_YES;
    private boolean checked = false;
    private List<ResourceNode> children = new LinkedList<ResourceNode>();

    public ResourceNode() {
        super();
    }

    public ResourceNode(
            Long id, Long parentId, String name, String type, int showMode, String value, String icon) {
        super();
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.type = type;
        this.showMode = showMode;
        this.value = value;
        this.icon = icon;
    }

    public int getShowMode() {
        return showMode;
    }

    public void setShowMode(int showMode) {
        this.showMode = showMode;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void addChild(ResourceNode node) {
        this.children.add(node);
    }

    public List<ResourceNode> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceNode> children) {
        this.children = children;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public int getShowState() {
        return showState;
    }

    public void setShowState(int showState) {
        this.showState = showState;
    }


    public String getIconSkin() {
        if (Strings.isBlank(this.icon) || !Strings.startsWith(this.icon, "icon")) {
            return this.icon;
        }
        return null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ResourceNode other = (ResourceNode) obj;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("ResourceNode: {hashCode():%s}", hashCode());
    }

    public static class ResourceNodeComparator implements Comparator<ResourceNode> {
        @Override
        public int compare(ResourceNode node1, ResourceNode node2) {

            long showState1 = node1.getShowState();
            long showState2 = node2.getShowState();
            long orderTime1 = node1.getOrderTime().getTime();
            long orderTime2 = node2.getOrderTime().getTime();

            // 判断大小，相等则使用下一个属性对比
            if (showState1 > showState2) {
                return 1;
            } else if (showState1 < showState2) {
                return -1;
            }
            return orderTime1 > orderTime2 ? -1 : (orderTime1 == orderTime2 ? 0 : 1);
        }
    }
}
