/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2019-03-10 16:53
 */
package com.acooly.module.eav.dto;

import com.acooly.core.common.facade.InfoBase;
import com.acooly.module.eav.entity.EavAttribute;
import com.acooly.module.eav.enums.AttributeTypeEnum;
import lombok.Data;

/**
 * @author zhangpu
 * @date 2019-03-10 16:53
 */
@Data
public class EavAttributeInfo extends InfoBase {

    private String name;

    private String displayName;

    private Object value;

    private String tag;

    private EavAttribute attribute;


    public Object getFormatValue() {
        if (this.attribute.getAttributeType() == AttributeTypeEnum.ENUM) {
            return this.attribute.getOptionMapping().get(this.value);
        } else {
            return this.value;
        }

    }

    public EavAttributeInfo() {
    }

    public EavAttributeInfo(EavAttribute attribute, Object value) {
        this.value = value;
        this.attribute = attribute;
        this.name = attribute.getName();
        this.displayName = attribute.getDisplayName();
        this.tag = attribute.getTag();
    }
}
