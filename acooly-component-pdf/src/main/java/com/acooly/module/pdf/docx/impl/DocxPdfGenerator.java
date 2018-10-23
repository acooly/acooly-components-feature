package com.acooly.module.pdf.docx.impl;

import com.acooly.core.utils.Exceptions;
import com.acooly.module.pdf.PdfGeneratorService;
import com.acooly.module.pdf.PdfProperties;
import com.acooly.module.pdf.factory.ITextRendererObjectFactory;
import com.acooly.module.pdf.vo.DocumentVo;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;
import fr.opensagres.xdocreport.itext.extension.font.ITextFontRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.awt.*;
import java.io.*;
import java.util.Map;

/**
 * @author xiyang@acooly.cn
 * @date 2018-09-25 13:39
 */
@Slf4j
@Service("docxPdfGenerator")
public class DocxPdfGenerator implements PdfGeneratorService {

    @Autowired
    private PdfProperties pdfProperties;

    @Override
    public void generate(String templateName, Map<String, Object> params, OutputStream outputStream) throws IOException {
        log.info("开始使用word模版生成pdf，templateName={}", templateName);
        ITextRenderer iTextRenderer = null;
        GenericObjectPool<ITextRenderer> objectPool =
                ITextRendererObjectFactory.getObjectPool(pdfProperties);
        Resource resource = pdfProperties.getResourceLoader().getResource(pdfProperties.getTemplatePath()  + templateName);
        InputStream in = resource.getInputStream();
        try {
            iTextRenderer = objectPool.borrowObject();
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);
            Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);

            PdfOptions subOptions = PdfOptions.create();
            options.subOptions(subOptions);
            // Customize Font provider for Chinese characters
            subOptions.fontProvider(new IFontProvider() {
                public Font getFont(String familyName, String encoding, float size, int style, Color color) {
                    try {
                        String baseFontPath = ITextRendererObjectFactory.FONTS_PATH + "SourceHanSerifSC-Regular.otf";
                        BaseFont bfChinese = BaseFont.createFont(baseFontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                        Font fontChinese = new Font(bfChinese, size, style, color);
                        if (familyName != null)
                            fontChinese.setFamily(familyName);
                        return fontChinese;
                    } catch (Throwable e) {
                        e.printStackTrace();
                        // An error occurs, use the default font provider.
                        return ITextFontRegistry.getRegistry().getFont(familyName, encoding, size, style, color);
                    }
                }
            });
            IContext context = report.createContext();
            context.putMap(params);
            report.convert(context, options, outputStream);
            log.info("pdf文档生成完成！");
        } catch (IOException e) {
            log.error("pdf文档生成失败！", e);
            throw Exceptions.rethrowBusinessException(e);
        } catch (XDocReportException e) {
            log.error("pdf文档生成失败！", e);
            throw Exceptions.rethrowBusinessException(e);
        } catch (Exception e) {
            log.error("pdf文档生成失败！", e);
            throw Exceptions.rethrowBusinessException(e);
        } finally {
            if (iTextRenderer != null) {
                try {
                    objectPool.returnObject(iTextRenderer);
                } catch (Exception ex) {
                    log.error("ITextRenderer对象池回收对象失败.", ex);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    //ig
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    //ig
                }
            }
        }
    }

    @Override
    public void generate(String templateName, Map<String, Object> params, File outputFile) throws IOException {
        OutputStream out = new FileOutputStream(outputFile);
        this.generate(templateName, params, out);
    }

    @Override
    public void generate(String templateName, DocumentVo documentVo, File outputFile) throws IOException {
        OutputStream out = new FileOutputStream(outputFile);
        this.generate(templateName, documentVo.getDataMap(), out);
    }

    @Override
    public void generate(String templateName, DocumentVo documentVo, OutputStream outputStream) throws IOException {
        this.generate(templateName, documentVo.getDataMap(), outputStream);
    }
}
