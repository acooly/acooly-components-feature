package com.acooly.module.eav.dto;

import com.acooly.core.common.facade.InfoBase;
import com.acooly.module.eav.entity.EavAttribute;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

/**
 * @author qiuboboy@qq.com
 * @date 2018-06-26 21:38
 */
@Getter
@Setter
public class EavSchemaDto extends InfoBase {
    private Long id;
    private String name;
    private String memo;
    private Map<String,EavAttribute> attributes;
    private Date createTime;
    private Date updateTime;
}
