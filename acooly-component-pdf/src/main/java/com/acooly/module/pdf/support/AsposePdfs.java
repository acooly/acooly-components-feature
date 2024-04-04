/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2023-11-27 20:39
 */
package com.acooly.module.pdf.support;

import com.acooly.module.pdf.support.office.ExcleToPdf;
import com.acooly.module.pdf.support.office.WordToPdf;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Aspose PDF工具类
 *
 * @author zhangpu
 * @date 2023-11-27 20:39
 * <p>
 * <p>
 * fontsDir :字体目录, 可选，对于中文乱码或格式问题，可以从windows中拷贝word中使用的常用字体到linux服务对应的目录，
 * *                 然后通过该参数指定字体目录。一般常用字体包括：雅黑：msyh.ttc,msyhbd.ttc,msyhl.ttc；宋体：simsun.ttc；
 * *                 仿宋：simfang.ttf；黑体：simhei.ttf；楷体：simkai.ttf。
 */
@Slf4j
public class AsposePdfs {

    /**
     * word转pdf
     */
    public static void wordToPdf(@NotBlank InputStream wordInputStream, @NotBlank OutputStream pdfOutputStream) {
        WordToPdf.wordToPdf(wordInputStream, pdfOutputStream, null);
    }

    /**
     * word转pdf
     */
    public static void wordToPdf(@NotBlank String wordFile, @NotBlank String pdfFile) {
        WordToPdf.wordToPdf(wordFile, pdfFile, null);
    }

    /**
     * word转pdf
     *
     * @param wordFile word源文件完整path
     * @param pdfFile  转换后输出的pdf文件path
     * @param fontsDir
     */
    public static void wordToPdf(@NotBlank String wordFile, @NotBlank String pdfFile, String fontsDir) {
        WordToPdf.wordToPdf(wordFile, pdfFile, fontsDir);
    }

    /**
     * word转pdf
     */
    public static void wordToPdf(@NotBlank InputStream wordInputStream, @NotBlank OutputStream pdfOutputStream, String fontsDir) {
        WordToPdf.wordToPdf(wordInputStream, pdfOutputStream, fontsDir);
    }


    /**
     * excle 转 PDF
     */
    public static void excleToPdf(@NotBlank InputStream excleInputStream, @NotBlank OutputStream pdfOutputStream, String fontsDir) {
        ExcleToPdf.excleToPdf(excleInputStream, pdfOutputStream, fontsDir);
    }

    /**
     * excle 转 PDF
     *
     * @param excleInputStream
     * @param pdfOutputStream
     * @see #excelToPdf(InputStream, OutputStream) excelToPdf
     */
    @Deprecated
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
     * excle 转 PDF
     */
    public static void excleToPdf(@NotBlank String excleFile, @NotBlank String pdfFile, String fontsDir) {
        ExcleToPdf.excleToPdf(excleFile, pdfFile, fontsDir);
    }



}
