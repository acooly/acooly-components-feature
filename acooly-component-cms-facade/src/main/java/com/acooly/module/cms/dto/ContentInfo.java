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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 内容信息
 *
 * @author zhangpu
 * @date 2019-01-01
 */
@Data
public class ContentInfo {

    @NotNull
    @OpenApiField(desc = "Id", constraint = "物理编码", demo = "1", ordinal = 1)
    private Long id;

    @NotBlank
    @Length(min = 8, max = 64)
    @OpenApiField(desc = "编码", constraint = "唯一编码", demo = "20160909122220001", ordinal = 2)
    private String code;

    @NotBlank
    @Length(min = 6, max = 60)
    @OpenApiField(desc = "标题", constraint = "标题", demo = "最新好消息,融资10000000", ordinal = 3)
    private String title;

    @NotBlank
    @Length(min = 8, max = 64)
    @OpenApiField(desc = "简介", constraint = "内容摘要信息", demo = "最新好消息,融资10000000", ordinal = 4)
    private String subject;

    @NotBlank
    @Length(min = 8, max = 64)
    @OpenApiField(desc = "封面", constraint = "封面图片路径，全路径可直接访问",
            demo = "https://cdn.images.xxx.com/112/11.jpg", ordinal = 5)
    private String cover;

    @NotBlank
    @Length(min = 8, max = 64)
    @OpenApiField(desc = "发布时间", constraint = "内容首次发布时间", demo = "2016-09-09 12:12:12", ordinal = 6)
    private String pubTime;


    @NotBlank
    @Size(max = 1024 * 1024 * 5)
    @OpenApiField(desc = "内容信息", constraint = "多媒体内容详情（HTML）,最大5M", demo = "最新好消息,融资10000000xxxx", ordinal = 7)
    private String content;

    @NotBlank
    @OpenApiField(desc = "内容类型编码", constraint = "自定义分类编码，来自后端内容分类管理模块", demo = "news", ordinal = 8)
    private String typeCode;

    @NotBlank
    @OpenApiField(desc = "内容类型名称", constraint = "自定义分类名称，来自后端内容分类管理模块", demo = "新闻", ordinal = 9)
    private String typeName;

    @OpenApiField(desc = "SEO描述信息", constraint = "专用SEO的description", demo = "最新好消息,融资成功", ordinal = 10)
    private String description;

    @OpenApiField(desc = "SEO关键字", constraint = "专用SEO的keywords", demo = "xxx 好消息 融资", ordinal = 11)
    private String keywords;

    @OpenApiField(desc = "SEO title", constraint = "专用SEO的title", demo = "最新好消息,融资", ordinal = 12)
    private String webTitle;

    @OpenApiField(desc = "外链链接", constraint = "专用与链接跳转场景", demo = "https://xxx.sss.com/sss/ss.html", ordinal = 13)
    private String link;

}
