package com.acooly.module.pdf;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.exception.AppConfigException;
import com.acooly.core.utils.FreeMarkers;
import com.acooly.module.pdf.exception.DocumentGeneratingException;
import com.acooly.module.pdf.factory.ITextRendererObjectFactory;
import com.acooly.module.pdf.vo.DocumentVo;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * pdf 生成器
 *
 * @author shuijing
 */
@Slf4j
public class PDFService {

    private PdfProperties pdfProperties;

    //缓存 baseFont
    private Map baseFontMap = new HashMap(4);
    private Map<String, String> pdfTemplates = Maps.newConcurrentMap();

    public PDFService(PdfProperties pdfProperties) {
        this.pdfProperties = pdfProperties;
    }

    /**
     * 生成pdf
     *
     * @param templateName   模板名(带后缀)
     * @param documentVo     数据对象
     * @param outputFilePath 文件
     * @throws DocumentGeneratingException
     * @throws FileNotFoundException
     */
    public void generate(String templateName, DocumentVo documentVo, String outputFilePath)
            throws DocumentGeneratingException, FileNotFoundException {
        File outputFile = new File(outputFilePath);
        this.generate(templateName, documentVo, outputFile);
    }

    /**
     * 生成pdf
     *
     * @param templateName 模板名(带后缀)
     * @param documentVo   数据对象
     * @param outputFile   输出流
     * @throws DocumentGeneratingException
     * @throws FileNotFoundException
     */
    public void generate(String templateName, DocumentVo documentVo, File outputFile)
            throws DocumentGeneratingException, FileNotFoundException {
        generate(templateName, documentVo, new FileOutputStream(outputFile));
    }

    /**
     * 生成pdf
     *
     * @param templateName           模板名(带后缀)
     * @param documentVo             数据对象
     * @param outputFileOutputStream 输出流
     * @throws DocumentGeneratingException
     */
    public void generate(
            String templateName, DocumentVo documentVo, FileOutputStream outputFileOutputStream)
            throws DocumentGeneratingException {
        try {
            Map<String, Object> dataMap = documentVo.getDataMap();
            String htmlContent = this.parseTemplate(templateName, dataMap);
            this.generate(htmlContent, outputFileOutputStream);
        } catch (Exception e) {
            String error = "pdf文档生成失败!";
            log.error(error);
            throw new DocumentGeneratingException(error, e);
        } finally {
            if (outputFileOutputStream != null) {
                try {
                    outputFileOutputStream.close();
                } catch (IOException e) {
                    //ig
                }
            }
        }
    }

    /**
     * 生成pdf
     *
     * @param templateName 模板名(带后缀)
     * @param params       模板参数
     * @param outputFile   输出文件
     * @throws DocumentGeneratingException
     */
    public void generate(String templateName, Map<String, Object> params, File outputFile)
            throws DocumentGeneratingException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(outputFile);
            String htmlContent = this.parseTemplate(templateName, params);
            this.generate(htmlContent, os);
        } catch (Exception e) {
            String error = "pdf文档生成失败!";
            log.error(error);
            throw new DocumentGeneratingException(error, e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    //ig
                }
            }
        }
    }

    /**
     * 生成pdf
     *
     * @param templateName 模板名(带后缀)
     * @param params       模板参数
     * @param outputStream 输出流
     * @throws DocumentGeneratingException
     */
    public void generate(String templateName, Map<String, Object> params, OutputStream outputStream)
            throws DocumentGeneratingException {
        try {
            String htmlContent = this.parseTemplate(templateName, params);
            this.generate(htmlContent, outputStream);
        } catch (Exception e) {
            String error = "pdf文档生成失败!";
            log.error(error);
            throw new DocumentGeneratingException(error, e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    //ig
                }
            }
        }
    }

    /**
     * 生成pdf
     *
     * @param htmlContent html文本
     * @param outputFile  输出文件
     * @throws DocumentGeneratingException
     * @throws FileNotFoundException
     */
    public void generate(String htmlContent, File outputFile)
            throws DocumentGeneratingException, FileNotFoundException {
        OutputStream os = null;
        try {
            if (outputFile != null && !outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdir();
            }
            os = new FileOutputStream(outputFile);
            this.generate(htmlContent, os);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    //ig
                }
            }
        }
    }

    /**
     * 生成pdf
     *
     * @param htmlContent  html文本
     * @param outputStream 输出流
     * @throws DocumentGeneratingException
     */
    public void generate(String htmlContent, OutputStream outputStream)
            throws DocumentGeneratingException {
        OutputStream out = outputStream;
        ITextRenderer iTextRenderer = null;
        GenericObjectPool<ITextRenderer> objectPool =
                ITextRendererObjectFactory.getObjectPool(pdfProperties);
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(htmlContent.getBytes("UTF-8")));
            //获取对象池中对象
            iTextRenderer = objectPool.borrowObject();
            try {
                iTextRenderer.setDocument(doc, iTextRenderer.getSharedContext().getBaseURL());
                iTextRenderer.layout();
                iTextRenderer.createPDF(out);
            } catch (Exception e) {
                objectPool.invalidateObject(iTextRenderer);
                iTextRenderer = null;
                throw new DocumentGeneratingException(e);
            }
            out.flush();
            log.info("pdf文档生成成功!");
        } catch (Exception e) {
            log.error("pdf文档生成失败!", e);
        } finally {
            if (iTextRenderer != null) {
                try {
                    objectPool.returnObject(iTextRenderer);
                } catch (Exception ex) {
                    log.error("ITextRenderer对象池回收对象失败.", ex);
                }
            }
        }
    }

    /**
     * pdf加水印
     *
     * @param src     需要加水印pdf路径,替换原来文件
     * @param markStr 水印文字
     * @throws IOException
     * @throws DocumentException
     */
    public void addWatermark(String src, String markStr) throws IOException, DocumentException {
        this.addWatermark(new File(src), null, null, null, null, null, null, null, null, markStr);
    }

    /**
     * pdf加水印
     *
     * @param src                需要加水印pdf文件,替换原来文件
     * @param watermarkImagePath 水印图片路径
     * @throws IOException
     * @throws DocumentException
     */
    public void addWatermark(File src, String watermarkImagePath)
            throws IOException, DocumentException {
        this.addWatermark(src, null, null, null, null, null, null, null, watermarkImagePath, null);
    }

    /**
     * pdf加水印所有页
     *
     * @param src                需要加水印pdf文件,替换原来文件
     * @param watermarkImagePath 水印图片路径
     * @throws IOException
     * @throws DocumentException
     */
    public void addWatermark(File src, File desc, String watermarkImagePath)
            throws IOException, DocumentException {
        this.addWatermark(
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
    public void addWatermark(File src, File desc, String watermarkImagePath, List<Integer> pages)
            throws IOException, DocumentException {
        this.addWatermark(
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
    public void addWatermark(
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
            this.addWatermark(
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
    public void addWatermark(
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
            this.addWatermark(
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
    public void addWatermark(
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

    private BaseFont getBaseFontByCache(String fontName) throws IOException, DocumentException {
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

    /**
     * 解析freeMarker模板
     */
    protected String parseTemplate(String tempateName, Map<String, Object> data) {
        String template = getTemplatesString(tempateName);
        return FreeMarkers.rendereString(template, data);
    }

    /**
     * 缓存模板
     *
     * @param key 模板名
     * @return 模板内容
     */
    private String getTemplatesString(String key) {
        if (Apps.isDevMode()) {
            pdfTemplates.clear();
        }
        String t = pdfTemplates.get(key);
        if (Strings.isNullOrEmpty(t)) {
            String ptp = pdfProperties.getTemplatePath() + key;
            Resource resource = pdfProperties.getResourceLoader().getResource(ptp);
            if (resource.exists()) {
                try {
                    t = Resources.toString(resource.getURL(), Charsets.UTF_8);
                    pdfTemplates.put(key, t);
                } catch (IOException e) {
                    throw new AppConfigException("pdf模板不存在:" + ptp, e);
                }
            } else {
                throw new AppConfigException("pdf模板不存在:" + ptp);
            }
        }
        return t;
    }
}
