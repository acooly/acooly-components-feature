package com.acooly.module.pdf;

import com.acooly.module.pdf.vo.DocumentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author xiyang@acooly.cn
 * @date 2018-09-25 15:23
 */
@Slf4j
@Service("pdfGeneratorService")
public class PdfGeneratorServiceProxy implements PdfGeneratorService, ApplicationContextAware {
    private ApplicationContext applicationContext;

    private PdfGeneratorService pdfGeneratorService;

    @Autowired
    private PdfProperties pdfProperties;

    @Override
    public void generate(String templateName, Map<String, Object> params, File outputFile) throws IOException {
        getPdfGeneratorService().generate(templateName, params, outputFile);
    }

    @Override
    public void generate(String templateName, DocumentVo documentVo, File outputFile) throws IOException {
        getPdfGeneratorService().generate(templateName, documentVo, outputFile);
    }

    @Override
    public void generate(String templateName, Map<String, Object> params, OutputStream outputStream) throws IOException {
        getPdfGeneratorService().generate(templateName,params,outputStream);
    }

    @Override
    public void generate(String templateName, DocumentVo documentVo, OutputStream outputStream) throws IOException {
        getPdfGeneratorService().generate(templateName,documentVo,outputStream);
    }

    public PdfGeneratorService getPdfGeneratorService() {
        if (pdfGeneratorService == null) {
            pdfGeneratorService =
                    (PdfGeneratorService)
                            this.applicationContext.getBean(pdfProperties.getTemplateType().code());
        }
        return pdfGeneratorService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
