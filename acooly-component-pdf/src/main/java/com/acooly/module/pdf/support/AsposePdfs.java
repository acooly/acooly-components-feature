/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2023-11-27 20:39
 */
package com.acooly.module.pdf.support;

import com.acooly.core.utils.Strings;
import com.aspose.words.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Modifier;

/**
 * Aspose PDF工具类
 *
 * @author zhangpu
 * @date 2023-11-27 20:39
 */
@Slf4j
public class AsposePdfs {

    public static void wordToPdf(@NotBlank InputStream wordInputStream, @NotBlank OutputStream pdfOutputStream) {
        wordToPdf(wordInputStream, pdfOutputStream, null);
    }

    public static void wordToPdf(@NotBlank String wordFile, @NotBlank String pdfFile) {
        wordToPdf(wordFile, pdfFile, null);
    }

    /**
     * word转pdf
     *
     * @param wordFile word源文件完整path
     * @param pdfFile  转换后输出的pdf文件path
     * @param fontsDir 字体目录, 可选，对于中文乱码或格式问题，可以从windows中拷贝word中使用的常用字体到linux服务对应的目录，
     *                 然后通过该参数指定字体目录。一般常用字体包括：雅黑：msyh.ttc,msyhbd.ttc,msyhl.ttc；宋体：simsun.ttc；
     *                 仿宋：simfang.ttf；黑体：simhei.ttf；楷体：simkai.ttf。
     */
    public static void wordToPdf(@NotBlank String wordFile, @NotBlank String pdfFile, String fontsDir) {
        File file = new File(pdfFile);
        try (FileOutputStream os = new FileOutputStream(file);) {
            crackWord21_6();
            setFontsSources(fontsDir);
            Document doc = new Document(wordFile);
            doc.save(os, SaveFormat.PDF);
        } catch (Exception e) {
            throw new RuntimeException("转换失败", e);
        }
    }

    public static void wordToPdf(@NotBlank InputStream wordInputStream, @NotBlank OutputStream pdfOutputStream, String fontsDir) {
        try (InputStream is = wordInputStream; OutputStream os = pdfOutputStream;) {
            crackWord21_6();
            setFontsSources(fontsDir);
            Document doc = new Document(is);
            doc.save(os, SaveFormat.PDF);
        } catch (Exception e) {
            throw new RuntimeException("转换失败", e);
        }
    }


    private static void setFontsSources(String fontsDir) {
        if (Strings.isNotBlank(fontsDir)) {
            //将用户目录字体添加到字体源中
            FontSourceBase[] originalFontSources = FontSettings.getDefaultInstance().getFontsSources();
            FolderFontSource folderFontSource = new FolderFontSource(fontsDir, true);
            FontSourceBase[] updatedFontSources = new FontSourceBase[originalFontSources.length + 1];
            updatedFontSources[0] = folderFontSource;
            System.arraycopy(originalFontSources, 0, updatedFontSources, 1, originalFontSources.length);
            FontSettings.getDefaultInstance().setFontsSources(updatedFontSources);
        }
    }


    /**
     * 破解 word 21.6版本
     */
    private static void crackWord21_6() {
        try {
            Class<?> aClass = Class.forName("com.aspose.words.zzXyu");
            java.lang.reflect.Field zzZXG = aClass.getDeclaredField("zzZXG");
            zzZXG.setAccessible(true);

            java.lang.reflect.Field modifiersField = zzZXG.getClass().getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(zzZXG, zzZXG.getModifiers() & ~Modifier.FINAL);
            zzZXG.set(null, new byte[]{76, 73, 67, 69, 78, 83, 69, 68});
        } catch (Exception e) {
            log.error("aspose word 破解异常");
        }
    }
}
