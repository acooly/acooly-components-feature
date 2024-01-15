/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2023-11-27 20:39
 */
package com.acooly.module.pdf.support.office;

import com.acooly.core.utils.Strings;
import com.aspose.cells.FolderFontSource;
import com.aspose.cells.FontConfigs;
import com.aspose.cells.FontSourceBase;
import com.aspose.cells.Workbook;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Aspose excle PDF工具类
 */
@Slf4j
public class ExcleToPdf {


    /**
     * excle 转 PDF
     *
     * @param excleInputStream
     * @param pdfOutputStream
     */
    public static void excleToPdf(@NotBlank InputStream excleInputStream, @NotBlank OutputStream pdfOutputStream) {
        ExcleToPdf.excleToPdf(excleInputStream, pdfOutputStream, null);
    }


    /**
     * excle 转 PDF
     *
     * @param excleFile
     * @param pdfFile
     */
    public static void excleToPdf(@NotBlank String excleFile, @NotBlank String pdfFile) {
        ExcleToPdf.excleToPdf(excleFile, pdfFile, null);
    }


    /**
     * excle转pdf
     *
     * @param excleFile excle源文件完整path
     * @param pdfFile   转换后输出的pdf文件path
     * @param fontsDir  字体目录, 可选，对于中文乱码或格式问题，可以从windows中拷贝excle中使用的常用字体到linux服务对应的目录，
     *                  然后通过该参数指定字体目录。一般常用字体包括：雅黑：msyh.ttc,msyhbd.ttc,msyhl.ttc；宋体：simsun.ttc；
     *                  仿宋：simfang.ttf；黑体：simhei.ttf；楷体：simkai.ttf。
     */
    public static void excleToPdf(@NotBlank String excleFile, @NotBlank String pdfFile, String fontsDir) {
        File file = new File(pdfFile);
        try (FileOutputStream os = new FileOutputStream(file);) {
            crackExcle22_6();
            setFontsSources(fontsDir);
            Workbook workbook = new Workbook(excleFile);
            workbook.save(os, com.aspose.cells.SaveFormat.PDF);
        } catch (Exception e) {
            throw new RuntimeException("excle转换失败", e);
        }
    }

    public static void excleToPdf(@NotBlank InputStream excleInputStream, @NotBlank OutputStream pdfOutputStream, String fontsDir) {
        try (InputStream is = excleInputStream; OutputStream os = pdfOutputStream;) {
            crackExcle22_6();
            setFontsSources(fontsDir);
            Workbook workbook = new Workbook(is);
            workbook.save(os, com.aspose.cells.SaveFormat.PDF);
        } catch (Exception e) {
            throw new RuntimeException("excle转换失败", e);
        }
    }


    private static void setFontsSources(String fontsDir) {
        if (Strings.isNotBlank(fontsDir)) {
            FontSourceBase[] originalFontSources = FontConfigs.getFontSources();
            FontSourceBase folderFontSource = new FolderFontSource(fontsDir, true);
            FontSourceBase[] updatedFontSources = new FontSourceBase[originalFontSources.length + 1];
            updatedFontSources[0] = folderFontSource;
            System.arraycopy(originalFontSources, 0, updatedFontSources, 1, originalFontSources.length);
            FontConfigs.setFontSources(updatedFontSources);
        }
    }


    /**
     * 破解 Excle 22.6版本
     */
    private static void crackExcle22_6() {
        try {
            String licenseExpiry = "20991231";
            // License
            Class<?> licenseClass = Class.forName("com.aspose.cells.License");
            Field a = licenseClass.getDeclaredField("a");
            a.setAccessible(true);
            a.set(null, licenseExpiry);

            // k65
            Class<?> k65Class = Class.forName("com.aspose.cells.k65");
            Field k65A = k65Class.getDeclaredField("a");
            k65A.setAccessible(true);

            Constructor<?> constructor = k65Class.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            Object k65Instance = constructor.newInstance();
            k65A.set(null, k65Instance);

            Field k56C = k65Class.getDeclaredField("c");
            k56C.setAccessible(true);
            k56C.set(k65Instance, licenseExpiry);

            // e0n
            Class<?> e0nClass = Class.forName("com.aspose.cells.e0n");
            Field e0nA = e0nClass.getDeclaredField("a");
            e0nA.setAccessible(true);
            e0nA.set(null, false);
        } catch (Exception e) {
            log.error("aspose excle 22.6 破解异常");
        }
    }
}
