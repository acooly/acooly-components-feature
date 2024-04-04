/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-12-12 20:32
 */
package com.acooly.module.test.aspose;

import com.acooly.module.pdf.support.AsposePdfs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author cuifuq
 * @date 2024-01-04
 */
@Slf4j
public class AsposeExcelTest {

    /**
     * 转化前-xlsx 文件
     */
    String pptFile = "/Users/zhangpu/temp/desgin_task_list_orginal.xlsx";

    /**
     * 转化后-pdf 文件
     */
    String pdfFile = "/Users/zhangpu/temp/desgin_task_list_orginal.pdf";

    /**
     * 根据你的开发机器的字体目录，设置字体目录
     * 如果是windows，你可以不管这个，因为aspose会自动加载系统字体
     * 如果是mac，你需要从windows拷贝这些字体，然后制定字体目录
     */
//    String fontsDir = "/Users/cuifuqiang/Desktop/fonts/";
    String fontsDir = "/Users/zhangpu/software/fonts";

    @Test
    public void testExcelToPdfWithFile() {
        AsposePdfs.excleToPdf(pptFile, pdfFile, fontsDir);
    }

    @Test
    public void testExcelToPdfWithStream() throws Exception {
        InputStream excelInputStream = new FileInputStream(pptFile);
        OutputStream pdfOutputStream = new FileOutputStream(pdfFile);
        AsposePdfs.excleToPdf(excelInputStream, pdfOutputStream, fontsDir);
        IOUtils.closeQuietly(excelInputStream);
        IOUtils.closeQuietly(pdfOutputStream);
    }

}
