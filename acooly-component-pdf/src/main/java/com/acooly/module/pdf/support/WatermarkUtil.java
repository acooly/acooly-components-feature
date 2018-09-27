package com.acooly.module.pdf.support;

import com.acooly.module.pdf.exception.DocumentGeneratingException;
import com.acooly.module.pdf.factory.ITextRendererObjectFactory;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiyang@acooly.cn
 * @date 2018-09-26 17:55
 */
@Slf4j
public class WatermarkUtil {

    //缓存 baseFont
    private static Map baseFontMap = new HashMap(4);

    /**
     * pdf加水印
     *
     * @param src     需要加水印pdf路径,替换原来文件
     * @param markStr 水印文字
     * @throws IOException
     * @throws DocumentException
     */
    public static void addWatermark(String src, String markStr) throws IOException, DocumentException {
        addWatermark(new File(src), null, null, null, null, null, null, null, null, markStr);
    }

    /**
     * pdf加水印
     *
     * @param src                需要加水印pdf文件,替换原来文件
     * @param watermarkImagePath 水印图片路径
     * @throws IOException
     * @throws DocumentException
     */
    public static void addWatermark(File src, String watermarkImagePath)
            throws IOException, DocumentException {
        addWatermark(src, null, null, null, null, null, null, null, watermarkImagePath, null);
    }

    /**
     * pdf加水印所有页
     *
     * @param src                需要加水印pdf文件,替换原来文件
     * @param watermarkImagePath 水印图片路径
     * @throws IOException
     * @throws DocumentException
     */
    public static void addWatermark(File src, File desc, String watermarkImagePath)
            throws IOException, DocumentException {
        addWatermark(
                src, desc, null, null, null, null, null, null, null, watermarkImagePath, null);
    }

    /**
     * pdf加水印所有页
     *
     * @param src                需要加水印pdf文件,替换原来文件
     * @param watermarkImagePath 水印图片路径
     * @param pages              pdf需要加水印的页数
     * @throws IOException
     * @throws DocumentException
     */
    public static void addWatermark(File src, File desc, String watermarkImagePath, List<Integer> pages)
            throws IOException, DocumentException {
        addWatermark(
                src, desc, pages, null, null, null, null, null, null, watermarkImagePath, null);
    }

    /**
     * pdf加水印
     *
     * @param src                需要加水印pdf文件
     * @param pages              pdf需要加水印的页数
     * @param x                  水印横坐标
     * @param y                  水印纵坐标
     * @param fillOpacity        水印透明度0-1f
     * @param fontSize           文字大小
     * @param rotation           字体倾斜度
     * @param scalePercent       图片缩放比例0-100f
     * @param watermarkImagePath 水印图片绝对路径
     * @param markStr            水印文字
     * @throws IOException
     * @throws DocumentException
     */
    public static void addWatermark(
            File src,
            List<Integer> pages,
            Float x,
            Float y,
            Float fillOpacity,
            Float fontSize,
            Float rotation,
            Float scalePercent,
            String watermarkImagePath,
            String markStr)
            throws IOException, DocumentException {
        InputStream is = null;
        OutputStream os = null;
        try {
            File tmpFile = File.createTempFile("pdfWatermarkTmp", ".pdf");
            is = new FileInputStream(src);
            os = new FileOutputStream(tmpFile);
            addWatermark(
                    is,
                    os,
                    pages,
                    x,
                    y,
                    fillOpacity,
                    fontSize,
                    rotation,
                    scalePercent,
                    watermarkImagePath,
                    markStr);
            FileUtils.copyFile(tmpFile, src);
            FileUtils.deleteQuietly(tmpFile);
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * pdf加水印
     *
     * @param src                需要加水印pdf文件
     * @param dest               输出加水印pdf文件
     * @param pages              pdf需要加水印的页数
     * @param x                  水印横坐标
     * @param y                  水印纵坐标
     * @param fillOpacity        水印透明度0-1f
     * @param fontSize           文字大小
     * @param rotation           字体倾斜度
     * @param scalePercent       图片缩放比例0-100f
     * @param watermarkImagePath 水印图片绝对路径
     * @param markStr            水印文字
     * @throws IOException
     * @throws DocumentException
     */
    public static void addWatermark(
            File src,
            File dest,
            List<Integer> pages,
            Float x,
            Float y,
            Float fillOpacity,
            Float fontSize,
            Float rotation,
            Float scalePercent,
            String watermarkImagePath,
            String markStr)
            throws IOException, DocumentException {
        InputStream is = null;
        OutputStream os = null;
        try {
            os = new FileOutputStream(dest);
            is = new FileInputStream(src);
            addWatermark(
                    is,
                    os,
                    pages,
                    x,
                    y,
                    fillOpacity,
                    fontSize,
                    rotation,
                    scalePercent,
                    watermarkImagePath,
                    markStr);
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * pdf加水印
     *
     * @param inputStream        需要加水印输入流
     * @param outputStream       加水印后的输出流
     * @param pages              pdf需要加水印的页数
     * @param x                  水印横坐标
     * @param y                  水印纵坐标
     * @param fillOpacity        水印透明度0-1f
     * @param fontSize           文字大小
     * @param rotation           字体倾斜度
     * @param scalePercent       图片缩放比例0-100f
     * @param watermarkImagePath 水印图片绝对路径
     * @param markStr            水印文字
     * @throws IOException
     * @throws DocumentException
     */
    public static void addWatermark(
            InputStream inputStream,
            OutputStream outputStream,
            List<Integer> pages,
            Float x,
            Float y,
            Float fillOpacity,
            Float fontSize,
            Float rotation,
            Float scalePercent,
            String watermarkImagePath,
            String markStr)
            throws IOException, DocumentException {
        PdfReader reader = null;
        PdfStamper stamper = null;
        BaseFont base = getBaseFontByCache("sourceHanSerifSC-Regular");
        PdfContentByte content;

        //字体倾斜度
        float rot = rotation == null ? 30 : rotation;
        //字体大小
        float fs = fontSize == null ? 30 : rotation;
        //水印透明度0-1f
        float fo = fillOpacity == null ? 0.3f : rotation;
        //图片缩放比例0-100f
        float sp = scalePercent == null ? 50f : scalePercent;
        try {
            reader = new PdfReader(inputStream);
            stamper = new PdfStamper(reader, outputStream);

            int pageNum = reader.getNumberOfPages();
            if (pages != null && !pages.isEmpty()) {
                pages.forEach(
                        p -> {
                            if (p > pageNum) {
                                log.error("加水印页数参数不能大于pdf最大页{}", pages);
                                throw new DocumentGeneratingException("加水印页数参数不能大于pdf最大页");
                            }
                        });
            }
            //页数不传，所有页均加水印
            for (int i = 1; i <= pageNum; i++) {
                if (pages != null && !pages.isEmpty()) {
                    if (!pages.contains(i)) {
                        continue;
                    }
                }
                reader.getPageSize(i);
                content = stamper.getOverContent(i);

                float xx = x == null ? reader.getPageSize(i).getWidth() / 2 : x;
                float yy = y == null ? reader.getPageSize(i).getHeight() / 2 : y;

                content.saveState();
                PdfGState gs = new PdfGState();
                gs.setFillOpacity(fo);
                content.setGState(gs);
                //水印字体
                if (!StringUtils.isEmpty(markStr)) {
                    content.beginText();
                    content.setColorFill(Color.GRAY);
                    content.setFontAndSize(base, fs);
                    content.showTextAligned(Element.ALIGN_CENTER, markStr, xx, yy, rot);
                    content.endText();
                }
                //水印图片
                if (!StringUtils.isEmpty(watermarkImagePath)) {
                    com.lowagie.text.Image img =
                            com.lowagie.text.Image.getInstance(new File(watermarkImagePath).toURI().toURL());
                    img.setAbsolutePosition(xx, yy);
                    img.scalePercent(sp);
                    content.addImage(img);
                }
                content.fill();
                content.restoreState();
            }
            stamper.close();
            outputStream.close();
            log.info("pdf加水印成功!");
        } catch (Exception e) {
            throw new DocumentGeneratingException("pdf加水印失败:", e);
        } finally {
            if (stamper != null) {
                try {
                    stamper.close();
                } catch (Exception e) {
                    //ig
                }
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static BaseFont getBaseFontByCache(String fontName) throws IOException, DocumentException {
        BaseFont baseFont = null;
        if (baseFontMap.containsKey(fontName)) {
            baseFont = (BaseFont) baseFontMap.get(fontName);
        } else {
            String baseFontPath = ITextRendererObjectFactory.FONTS_PATH + "SourceHanSerifSC-Regular.otf";
            baseFont = BaseFont.createFont(baseFontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            baseFontMap.put(fontName, baseFont);
        }
        return baseFont;
    }
}
