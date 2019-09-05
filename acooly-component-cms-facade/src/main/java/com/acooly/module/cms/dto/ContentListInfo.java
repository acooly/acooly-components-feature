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
    @OpenApiField(desc = "Id", constraint = "物理编码", demo = "1", ordinal = 1)
    private Long id;

    @NotEmpty
    @Length(min = 8, max = 64)
    @OpenApiField(desc = "编码", constraint = "唯一编码", demo = "20160909122220001", ordinal = 2)
    private String code;

    @NotEmpty
    @Length(min = 6, max = 60)
    @OpenApiField(desc = "标题", constraint = "标题", demo = "最新好消息,融资10000000", ordinal = 3)
    private String title;

    @NotEmpty
    @Length(min = 8, max = 128)
    @OpenApiField(desc = "封面", constraint = "封面图片路径，全路径可直接访问",
            demo = "https://cdn.images.xxx.com/112/11.jpg", ordinal = 4)
    private String cover;

    @Length(min = 6, max = 128)
    @OpenApiField(desc = "app封面", constraint = "App内容专用封面", demo = "2016-09-09 12:12:12", ordinal = 5)
    private String appCover;

    @Length(max = 128)
    @OpenApiField(desc = "外链链接", constraint = "专用与链接跳转场景", demo = "https://xxx.sss.com/sss/ss.html", ordinal = 6)
    private String link;

    @NotEmpty
    @Length(min = 8, max = 64)
    @OpenApiField(desc = "简介", constraint = "内容摘要信息", demo = "最新好消息,融资10000000", ordinal = 7)
    private String subject;

    @NotEmpty
    @Length(max = 64)
    @OpenApiField(desc = "发布时间", constraint = "内容首次发布时间", demo = "2016-09-09 12:12:12", ordinal = 8)
    private Date pubDate;


}
