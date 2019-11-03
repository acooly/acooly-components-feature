///*
//* acooly.cn Inc.
//* Copyright (c) 2019 All Rights Reserved.
//* create by zhangpu
//* date:2019-11-03
//*/
//package com.acooly.module.treetype.message;
//
//
//import com.acooly.openapi.framework.common.annotation.OpenApiField;
//import com.acooly.openapi.framework.common.message.ApiRequest;
//
//import org.hibernate.validator.constraints.*;
//import javax.validation.constraints.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.Date;
//
///**
// * 树形分类 ApiMessage
// * 注意：本类自定生成，不能直接使用，请开发人员根据业务选择下面的属性用于快速定义OpenApi的报文，
// * 主要减轻定义@OpenApiField和JSR303的工作。
// *
// * @author zhangpu
// * Date: 2019-11-03 08:46:48
// */
//@Getter
//@Setter
//public class TreeTypeApiRequest extends ApiRequest {
//    /**
//     * 主题
//     */
//	@Size(max = 45)
//    @OpenApiField(desc = "主题", constraint = "主题", demo = "", ordinal = 2)
//    private String theme;
//
//    /**
//     * 父类型ID
//     */
//	@Max(2147483646)
//    @OpenApiField(desc = "父类型ID", constraint = "父类型ID", demo = "", ordinal = 3)
//    private Long parentId;
//
//    /**
//     * 搜索路径
//     */
//	@Size(max = 128)
//    @OpenApiField(desc = "搜索路径", constraint = "搜索路径", demo = "", ordinal = 4)
//    private String path;
//
//    /**
//     * 排序值
//     */
//	@Max(2147483646)
//    @OpenApiField(desc = "排序值", constraint = "排序值", demo = "", ordinal = 5)
//    private Long sortTime;
//
//    /**
//     * 类型编码
//     */
//	@Size(max = 64)
//    @OpenApiField(desc = "类型编码", constraint = "类型编码", demo = "", ordinal = 6)
//    private String code;
//
//    /**
//     * 类型名称
//     */
//    @NotEmpty
//	@Size(max = 32)
//    @OpenApiField(desc = "类型名称", constraint = "类型名称", demo = "", ordinal = 7)
//    private String name;
//
//    /**
//     * 子节点数量
//     */
//	@Max(999999999)
//    @OpenApiField(desc = "子节点数量", constraint = "子节点数量", demo = "", ordinal = 8)
//    private Integer subCount;
//
//    /**
//     * 备注
//     */
//	@Size(max = 128)
//    @OpenApiField(desc = "备注", constraint = "备注", demo = "", ordinal = 9)
//    private String comments;
//
//}
