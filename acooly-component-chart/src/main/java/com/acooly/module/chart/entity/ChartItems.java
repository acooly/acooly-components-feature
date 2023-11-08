/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 */
package com.acooly.module.chart.entity;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.chart.enums.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Map;

/**
 * 图表-图表选项 Entity
 *
 * @author acooly Date: 2018-10-10 11:15:20
 */
@Entity
@Table(name = "c_chart_items")
@Getter
@Setter
public class ChartItems extends AbstractEntity {

    public static String redisKey = "chart_itemsId_";

    /**
     * 主题id
     */
    @NotNull
    private Long chartId;

    /**
     * 标题
     */
    @NotBlank
    @Size(max = 64)
    private String title;


    /**
     * 图表类型
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private ChartTypeEnum type;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private ChartItemsStatusEnum status;

    /**
     * 是否显示数据值
     */
    @Enumerated(EnumType.STRING)
    private DataShowEnum isShow;

    /**
     * 列表数据和下载
     */
    @Enumerated(EnumType.STRING)
    private DataShowEnum isDataListShow;

    /**
     * 表头数据项
     */
    @Enumerated(EnumType.STRING)
    private DataXYEnum dataListShowXY;

    /**
     * 数据缓存时间
     */
    private long dataCacheTime=0L;

    /**
     * 高
     */
    @NotNull
    private Long height = 50L;

    /**
     * 宽
     */
    @NotNull
    private Long width = 50L;

    /**
     * 循环时间
     */
    @NotNull
    private Long loopTime = 0L;

    /**
     * x轴
     */
    @NotBlank
    private String xShaft;

    /**
     * y轴
     */
    @NotBlank
    private String yShaft;

    /**
     * 排序
     */
    private Date orderTime;

    /**
     * 备注
     */
    private String comments;

    @Transient
    private String sqlData;

    @Transient
    private String fieldMapped;

    @Transient
    private JSONObject fieldMappedJson;

    @Transient
    private Map<String, Object> fieldMappedMap;

    /**
     * 查询条件-json to String
     */
    @Transient
    private String whereDataJson;

    /**
     * 图表样式
     * <p>
     * 标题是否隐藏
     * private DataShowEnum titleShow;
     * <p>
     * 标题颜色
     * private String titleColor;
     * <p>
     * 标题位置
     * private DataAlignEnum titleAlign;
     * <p>
     * 图表数据项标签位置
     * private DataTopEnum legendTop;
     * <p>
     * 图表数据项标签对齐
     * private DataAlignEnum legendAlign;
     ***/
    private String itemsStyle;

    /**
     * 标题是否隐藏
     **/
    @Transient
    @Enumerated(EnumType.STRING)
    private DataShowEnum titleShow = DataShowEnum.YES;

    /**
     * 标题颜色
     **/
    @Transient
    private String titleColor = "#000000";

    /**
     * 标题位置
     **/
    @Transient
    @Enumerated(EnumType.STRING)
    private DataAlignEnum titleAlign = DataAlignEnum.center;

    /**
     * 图表数据项标签位置
     **/
    @Transient
    @Enumerated(EnumType.STRING)
    private DataTopEnum legendTop = DataTopEnum.top;

    /**
     * 图表数据项标签对齐
     **/
    @Transient
    @Enumerated(EnumType.STRING)
    private DataAlignEnum legendAlign = DataAlignEnum.left;


    public DataShowEnum getTitleShow() {
        JSONObject itemsStyle = getItemsStyleJson();
        if (itemsStyle != null) {
            String titleShowStr = itemsStyle.get("titleShow").toString();
            return DataShowEnum.find(titleShowStr);
        }
        return titleShow;
    }

    public String getTitleColor() {
        JSONObject itemsStyle = getItemsStyleJson();
        if (itemsStyle != null) {
            String titleColorStr = itemsStyle.get("titleColor").toString();
            return titleColorStr;
        }
        return titleColor;
    }

    public DataAlignEnum getTitleAlign() {
        JSONObject itemsStyle = getItemsStyleJson();
        if (itemsStyle != null) {
            String titleAlignStr = itemsStyle.get("titleAlign").toString();
            return DataAlignEnum.find(titleAlignStr);
        }
        return titleAlign;
    }

    public DataTopEnum getLegendTop() {
        JSONObject itemsStyle = getItemsStyleJson();
        if (itemsStyle != null) {
            String legendTopStr = itemsStyle.get("legendTop").toString();
            return DataTopEnum.find(legendTopStr);
        }
        return legendTop;
    }

    public DataAlignEnum getLegendAlign() {
        JSONObject itemsStyle = getItemsStyleJson();
        if (itemsStyle != null) {
            String legendAlignStr = itemsStyle.get("legendAlign").toString();
            return DataAlignEnum.find(legendAlignStr);
        }
        return legendAlign;
    }

    public JSONObject getItemsStyleJson() {
        return JSON.parseObject(getItemsStyle());
    }


    //---------------
    public String getxShaft() {
        return xShaft;
    }

    public void setxShaft(String xShaft) {
        this.xShaft = xShaft;
    }

    public String getyShaft() {
        return yShaft;
    }

    public void setyShaft(String yShaft) {
        this.yShaft = yShaft;
    }


}
