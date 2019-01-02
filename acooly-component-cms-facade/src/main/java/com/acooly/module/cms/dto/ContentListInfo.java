/**
 * create by zhangpu
 * date:2015年5月24日
 */
package com.acooly.module.cms.dto;

import com.acooly.openapi.framework.common.annotation.OpenApiField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * @author zhangpu
 */
@Data
public class ContentListInfo {

    @NotNull
    @OpenApiField(desc = "ID", constraint = "通过ID访问详情")
    private Long id;

    @NotEmpty
    @Length(min = 8, max = 64)
    @OpenApiField(desc = "编码")
    private String code;

    @NotEmpty
    @Length(min = 6, max = 60)
    @OpenApiField(desc = "标题")
    private String title;

    @Length(min = 6, max = 128)
    @OpenApiField(desc = "链接")
    private String link;

    @Length(min = 6, max = 128)
    @OpenApiField(desc = "封面")
    private String cover;

    @NotEmpty
    @Length(min = 8, max = 64)
    @OpenApiField(desc = "简介")
    private String subject;

    @NotEmpty
    @OpenApiField(desc = "发布时间")
    private Date pubDate;

    @Length(min = 6, max = 128)
    @OpenApiField(desc = "app封面")
    private String appCover;
    
}
