/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2019-05-07 17:26
 */
package com.acooly.module.data.region.dto;

import com.acooly.core.common.facade.InfoBase;
import com.acooly.core.utils.arithmetic.tree.TreeNode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author zhangpu
 * @date 2019-05-07 17:26
 */
@Slf4j
@Getter
@Setter
public class RegionInfo extends InfoBase implements TreeNode<RegionInfo> {

    private Long id;

    /**
     * 区域父编码
     */
    @NotNull
    private Long parentId;

    /**
     * 区域名称
     */
    @NotEmpty
    @Size(max = 64)
    private String name;

    /**
     * 排序值
     */
    private Long sortTime;

    private List<RegionInfo> children;

    public RegionInfo() {
    }

    public RegionInfo(Long id, Long parentId, String name,Long sortTime) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.sortTime = sortTime;
    }
}
