package com.acooly.module.chart;

import com.acooly.core.common.boot.component.ComponentInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author cuifuqiang
 */
public class ChartComponentInitializer implements ComponentInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        setPropertyIfMissing("acooly.security.xss.exclusions.chart", "/manage/module/chart/chartItems/*.html");

        setPropertyIfMissing("acooly.ds.dbPatchs.c_chart_items[0].columnName", "is_show");
        setPropertyIfMissing("acooly.ds.dbPatchs.c_chart_items[0].patchSql",
                " ALTER TABLE `c_chart_items`  ADD COLUMN `is_show`  varchar(40) NULL DEFAULT 'NO' COMMENT '是否显示数据值' AFTER `loop_time`;");


        setPropertyIfMissing("acooly.ds.dbPatchs.c_chart_items[1].columnName", "is_data_list_show");
        setPropertyIfMissing("acooly.ds.dbPatchs.c_chart_items[1].patchSql",
                " ALTER TABLE `c_chart_items` ADD COLUMN `is_data_list_show` varchar(40) NULL DEFAULT 'NO' COMMENT '是否显示列表数据和下载' AFTER `is_show`;");


        setPropertyIfMissing("acooly.ds.dbPatchs.c_chart_items[2].columnName", "items_style");
        setPropertyIfMissing("acooly.ds.dbPatchs.c_chart_items[2].patchSql",
                " ALTER TABLE `c_chart_items` ADD COLUMN `items_style` varchar(2048) NULL COMMENT '图形表样式' AFTER `is_data_list_show`;");


        setPropertyIfMissing("acooly.ds.dbPatchs.c_chart_items[3].columnName", "data_list_show_x_y");
        setPropertyIfMissing("acooly.ds.dbPatchs.c_chart_items[3].patchSql",
                " ALTER TABLE `c_chart_items` ADD COLUMN `data_list_show_x_y` varchar(8) NULL DEFAULT 'Y' COMMENT '表头图表数据项标签' AFTER `is_data_list_show`;");

        setPropertyIfMissing("acooly.ds.dbPatchs.c_chart_items[4].columnName", "data_cache_time");
        setPropertyIfMissing("acooly.ds.dbPatchs.c_chart_items[4].patchSql",
                " ALTER TABLE  `c_chart_items` ADD COLUMN `data_cache_time` bigint NULL DEFAULT 5 COMMENT '数据缓存时间' AFTER `data_list_show_x_y`;");


        setPropertyIfMissing("acooly.ds.dbPatchs.c_chart_data[0].columnName", "where_data");
        setPropertyIfMissing("acooly.ds.dbPatchs.c_chart_data[0].patchSql",
                "ALTER TABLE `c_chart_data` ADD COLUMN `where_data` varchar(4096) NULL COMMENT 'where条件数据' AFTER `items_id`;");


    }
}
