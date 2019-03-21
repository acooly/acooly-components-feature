/**
 * copyright: acooly.cn
 * 2019-03-05
 */
package com.acooly.module.eav.dto;

import com.acooly.core.utils.arithmetic.tree.TreeNode;
import lombok.Data;

import java.util.List;

/**
 * @author zhangpu
 * @date 2019-03-05 01:53
 */
@Data
public class EavOption implements TreeNode<EavOption> {

    private Long id;

    private Long parentId;

    private String key;

    private String value;

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public Long getParentId() {
        return null;
    }

    @Override
    public List<EavOption> getChildren() {
        return null;
    }
}
