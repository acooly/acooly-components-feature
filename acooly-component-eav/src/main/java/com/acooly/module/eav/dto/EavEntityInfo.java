/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2019-03-10 16:01
 */
package com.acooly.module.eav.dto;

import com.acooly.core.common.facade.InfoBase;
import com.acooly.core.common.type.DBMap;
import com.google.common.collect.Lists;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author zhangpu
 * @date 2019-03-10 16:01
 */
@Data
public class EavEntityInfo extends InfoBase {

    private Long id;

    /**
     * 方案id
     */
    @NotNull
    private Long schemeId;

    /**
     * 方案标题
     * （表中文名，label）
     */
    @NotEmpty
    @Size(max = 128)
    private String schemeTitle;

    /**
     * 方案名称（表名）
     */
    @NotEmpty
    @Size(max = 128)
    private String schemeName;


    /**
     * 备注
     */
    @Size(max = 128)
    private String memo;

    private DBMap value;

    private String jsonValue;


    private List<EavAttributeInfo> eavAttributeInfos = Lists.newArrayList();

}
