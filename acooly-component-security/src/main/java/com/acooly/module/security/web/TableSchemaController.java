/*
 * www.prosysoft.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * shuijing 2018-05-21 18:27 创建
 */
package com.acooly.module.security.web;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractFileOperationController;
import com.acooly.core.common.web.MappingMethod;
import com.acooly.module.ds.check.dic.Column;
import com.acooly.module.ds.check.loader.TableLoader;
import com.acooly.module.ds.check.loader.TableLoaderProvider;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.acooly.module.ds.check.DatabaseTableChecker.getMysqlschema;

/**
 * @author shuijing
 */
@Slf4j
@RequestMapping(value = "/manage/tableSchema/")
@Controller
public class TableSchemaController extends AbstractFileOperationController {

    @Autowired
    private DataSource dataSource;

    @ResponseBody
    @RequestMapping(value = {"exportXls", "exportExcel"})
    public String exportExcel(HttpServletRequest request, HttpServletResponse response, Model model) {
        allow(request, response, MappingMethod.exports);
        try {
            doExportExcel(request, response);
        } catch (Exception e) {
            log.warn("导出excel失败", e);
        }
        return "export success!";
    }

    protected String getExportFileName(HttpServletRequest request) {
        return "allSchema";
    }


    protected void doExportExcelBody(
            HttpServletRequest request, HttpServletResponse response, int batchSize) {
        SXSSFWorkbook workbook = null;
        OutputStream out = null;
        try {
            workbook = new SXSSFWorkbook(batchSize);
            workbook.setCompressTempFiles(true);
            Sheet sheet = workbook.createSheet();

            for (int i = 0; i < 5; i++) {
                ((SXSSFSheet) sheet).trackColumnForAutoSizing(i);
            }

            int rowNum = 0;

            Map<String, List<Column>> columns = getColumns();

            for (Map.Entry<String, List<Column>> entry : columns.entrySet()) {
                Row row = sheet.createRow(rowNum);

                String tableName = entry.getKey();
                List<Column> cs = entry.getValue();

                //表名
                getCellCumstomStyle(workbook, row.createCell(1));
                getCellCumstomStyle(workbook, row.createCell(2));
                getCellCumstomStyle(workbook, row.createCell(3));
                getCellCumstomStyle(workbook, row.createCell(4));
                //合并单元格，但加上后生成excel速度减慢
//                sheet.addMergedRegion(new CellRangeAddress(
//                        row.getRowNum(), //first row (0-based)
//                        row.getRowNum(), //last row  (0-based)
//                        0, //first column (0-based)
//                        4  //last column  (0-based)
//                ));
                Cell cell0 = getCellCumstomStyle(workbook, row.createCell(0));
                cell0.setCellValue(tableName);

                //表标题
                rowNum++;
                Row rowColumn = sheet.createRow(rowNum);
                rowColumn.createCell(0).setCellValue("字段名");
                rowColumn.createCell(1).setCellValue("数据类型");
                rowColumn.createCell(2).setCellValue("默认值");
                rowColumn.createCell(3).setCellValue("允许为空");
                rowColumn.createCell(4).setCellValue("备注");
                rowNum++;

                //字段
                for (int i = 0; i < cs.size(); i++) {
                    Column column = cs.get(i);
                    Row rowColumnV = sheet.createRow(rowNum);

                    rowColumnV.createCell(0).setCellValue(column.getName());
                    rowColumnV.createCell(1).setCellValue(column.getDataType());
                    rowColumnV.createCell(2).setCellValue(String.valueOf(column.getDefaultValue()));
                    rowColumnV.createCell(3).setCellValue(String.valueOf(column.isNullable()));
                    rowColumnV.createCell(4).setCellValue(column.getCommon());

                    rowNum++;
                }
            }
            //自动适应内容
            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (Exception e) {
            log.warn("do export excel failure -> " + e.getMessage(), e);
            throw new BusinessException("执行导出过程失败[" + e.getMessage() + "]");
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(workbook);
            if (workbook != null) {
                workbook.dispose();
            }
        }
    }

    private Cell getCellCumstomStyle(SXSSFWorkbook workbook, Cell cell) {

        CellStyle backgroundStyle = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setColor(HSSFColor.RED.index);
        backgroundStyle.setFont(font);

        backgroundStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        backgroundStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        backgroundStyle.setAlignment(HorizontalAlignment.CENTER);

        cell.setCellStyle(backgroundStyle);
        return cell;
    }

    public Map<String, List<Column>> getColumns() {
        Map<String, List<Column>> allTables = Maps.newHashMap();
        try {
            TableLoader tableLoader = TableLoaderProvider.getTableLoader(dataSource);
            String schema = getMysqlschema(dataSource);

            allTables = tableLoader.loadAllTables(schema);

        } catch (SQLException e) {
            log.error("查询表结构异常", e);
        }
        return allTables;
    }

}
