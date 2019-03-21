package com.acooly.module.eav.dto;

import com.acooly.core.common.facade.InfoBase;
import com.acooly.module.eav.EavConstants;
import com.acooly.module.eav.entity.EavAttribute;
import com.acooly.module.eav.enums.AttributePermissionEnum;
import com.acooly.module.eav.enums.SchemePermissionEnum;
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
public class EavSchemeDto extends InfoBase {
    private Long id;
    private String name;
    private String title;
    private int panelWidth = EavConstants.PANEL_WIDTH_DEFAULT;
    private int panelHeight = EavConstants.PANEL_HEIGHT_DEFAULT;
    private int permission = SchemePermissionEnum.LIST.getCode();
    private String memo;
    private Map<String,EavAttribute> attributes;
    private Date createTime;
    private Date updateTime;
}
