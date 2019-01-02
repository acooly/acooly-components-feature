/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-03-31 18:53 创建
 */
package com.acooly.module.cms.dto;

import com.acooly.openapi.framework.common.annotation.OpenApiField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 内容信息
 *
 * @author zhangpu
 * @date 2019-01-01
 */
@Data
public class ContentInfo {

    @NotNull
    @OpenApiField(desc = "Id", constraint = "1")
    private Long id;

    @NotEmpty
    @Length(min = 8, max = 64)
    @OpenApiField(desc = "编码", constraint = "唯一编码")
    private String code;

    @NotEmpty
    @Length(min = 6, max = 60)
    @OpenApiField(desc = "标题", constraint = "标题")
    private String title;

    @NotEmpty
    @Length(min = 8, max = 64)
    @OpenApiField(desc = "简介", constraint = "内容摘要信息")
    private String subject;

    @NotEmpty
    @Length(min = 8, max = 64)
    @OpenApiField(desc = "封面", constraint = "封面图片路径，全路径可直接访问")
    private String cover;

    @NotEmpty
    @Length(min = 8, max = 64)
    @OpenApiField(desc = "发布时间", constraint = "内容首次发布时间")
    private String pubTime;


    @NotEmpty
    @OpenApiField(desc = "内容信息", constraint = "多媒体内容详情（HTML）")
    private String content;

    @NotEmpty
    @OpenApiField(desc = "内容类型编码", constraint = "自定义分类编码")
    private String typeCode;

    @NotEmpty
    @OpenApiField(desc = "内容类型名称", constraint = "自定义分类名称")
    private String typeName;

    @OpenApiField(desc = "SEO描述信息", constraint = "专用SEO的description")
    private String description;

    @OpenApiField(desc = "SEO关键字", constraint = "专用SEO的keywords")
    private String keywords;

    @OpenApiField(desc = "SEO title", constraint = "专用SEO的title")
    private String webTitle;

    @OpenApiField(desc = "外链链接", constraint = "专用与链接跳转场景")
    private String link;

}
