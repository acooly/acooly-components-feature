package com.acooly.module.pdf.html.impl;

import com.acooly.module.pdf.PDFService;
import com.acooly.module.pdf.PdfGeneratorService;
import com.acooly.module.pdf.vo.DocumentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author xiyang@acooly.cn
 * @date 2018-09-25 14:35
 */
@Slf4j
@Service("htmlPdfGenerator")
public class HtmlPdfGenerator implements PdfGeneratorService {

    @Autowired
    private PDFService pdfService;

    @Override
    public void generate(String templateName, Map<String, Object> params, File outputFile) throws FileNotFoundException {
        pdfService.generate(templateName, params, outputFile);
    }

    @Override
    public void generate(String templateName, DocumentVo documentVo, File outputFile) throws FileNotFoundException {
        pdfService.generate(templateName, documentVo, outputFile);
    }

    @Override
    public void generate(String templateName, Map<String, Object> params, OutputStream outputStream) throws IOException {
        pdfService.generate(templateName, params, outputStream);
    }

    @Override
    public void generate(String templateName, DocumentVo documentVo, OutputStream outputStream) throws IOException {
        pdfService.generate(templateName, documentVo.getDataMap(), outputStream);
    }
}
