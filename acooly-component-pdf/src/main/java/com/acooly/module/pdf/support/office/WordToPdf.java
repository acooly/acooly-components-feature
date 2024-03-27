package com.acooly.module.pdf.support.office;

import com.acooly.core.utils.Strings;
import com.aspose.words.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class WordToPdf {


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
        } finally {
            try {
                wordInputStream.close();
            } catch (Exception e) {
                log.error("关闭输入流失败", e);
            }
            try {
                pdfOutputStream.close();
            } catch (Exception e) {
                log.error("关闭输出流失败", e);
            }
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
     * <p>
     * 请使用【WordUtils.crackWord21_6();】
     */
    @Deprecated
    public static void crackWord21_6() {
        WordUtils.crackWord21_6();
    }

}
