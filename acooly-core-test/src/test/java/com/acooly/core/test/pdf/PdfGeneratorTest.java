package com.acooly.core.test.pdf;

import com.acooly.module.pdf.PDFService;
import com.acooly.module.pdf.PdfProperties;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.File;

import static com.acooly.core.common.boot.Apps.DEV_MODE_KEY;
import static com.acooly.core.test.web.PdfServletTest.getVO;

@Slf4j
public class PdfGeneratorTest {

    private PDFService pdfService;

    @Before
    public void setUp() throws Exception {
        PdfProperties pdfProperties = new PdfProperties();
        pdfProperties.setResourceLoader(new DefaultResourceLoader());
        pdfService = new PDFService(pdfProperties);
        System.setProperty(DEV_MODE_KEY, "true");
    }

    @Test
    public void test() throws Exception {
        //模板名
        String template = "pdftest.html";
        // 生成pdf路径
        File outputFile = File.createTempFile("PdfGeneratorTest", ".pdf");
        // 生成pdf
        pdfService.generate(template, getVO(), outputFile);
        log.info("生成pdf路径:{}", outputFile.getAbsolutePath());
    }

    @Test
    public void testWartermark() throws Exception {
        pdfService.addWatermark(
                new File("D:\\tmp\\1492763802969.pdf"),
                new File("D:\\tmp\\1492763802969___.pdf"),
                "D:\\tmp\\logo.png",
                Lists.newArrayList(3, 4));
    }
}
