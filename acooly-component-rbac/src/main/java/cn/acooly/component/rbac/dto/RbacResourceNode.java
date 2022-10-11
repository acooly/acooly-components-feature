/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-11 21:24
 */
package cn.acooly.component.rbac.dto;

import cn.acooly.component.rbac.enums.ResourceShowMode;
import cn.acooly.component.rbac.enums.ResourceType;
import com.acooly.core.utils.arithmetic.tree.TreeNode;
import com.acooly.core.utils.enums.WhetherStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhangpu
 * @date 2022-10-11 21:24
 */
@Getter
@Setter
@NoArgsConstructor
public class RbacResourceNode implements TreeNode<RbacResourceNode> {

    private Long id;

    private Long parentId;
    /**
     * 资源名称
     */
    @NotBlank
    @Size(max = 64)
    private String name;

    /**
     * 资源值
     */
    @Size(max = 256)
    private String value;
    /**
     * 资源类型
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private ResourceType type;

    /**
     * 加载方式
     */
    @Enumerated(EnumType.STRING)
    private ResourceShowMode showMode;

    /**
     * 排序时间
     */
    @NotNull
    private Long orderTime;

    /**
     * 图标
     */
    @Size(max = 64)
    private String icon;

    private String iconSkin;

    /**
     * 描述
     */
    @Size(max = 256)
    private String memo;

    /**
     * 是否显示
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private WhetherStatus showStatus;

    private boolean checked = false;

    private List<RbacResourceNode> children = new LinkedList<RbacResourceNode>();


    @Override
    public void addChild(RbacResourceNode treeNode) {
        this.children.add(treeNode);
    }
}
