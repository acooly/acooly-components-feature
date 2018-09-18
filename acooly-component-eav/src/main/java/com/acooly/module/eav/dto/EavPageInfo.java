package com.acooly.module.eav.dto;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.module.eav.entity.EavEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author qiuboboy@qq.com
 * @date 2018-07-05 20:25
 */
@Getter
@Setter
public class EavPageInfo extends PageInfo<EavEntity> {
    private String eavSort;
    private String eavOrder;
}
