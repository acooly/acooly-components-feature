/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2023-07-05 20:06
 */
package com.acooly.module.test.pdf;

import com.acooly.module.pdf.PdfProperties;
import com.acooly.module.pdf.docx.impl.DocxPdfGenerator;
import com.acooly.module.test.pdf.vo.AssetManagementRulesVo;
import com.acooly.module.test.pdf.vo.AssetShop;
import com.acooly.module.test.pdf.vo.Saler;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.util.List;

/**
 * @author zhangpu
 * @date 2023-07-05 20:06
 */
@Slf4j
public class Docx2PdfTest {

    private DocxPdfGenerator pdfGeneratorService;

    @Before
    public void before() {
        PdfProperties pdfProperties = new PdfProperties();
        pdfProperties.setEnable(true);
        pdfProperties.setTemplateType(PdfProperties.TemplateType.docx);
        pdfProperties.setTemplatePath("classpath:/pdf/templates/");
        pdfProperties.setImagePath("classpath:/pdf/images/");
        pdfProperties.setFontsPath("classpath:/pdf/fonts/");
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        pdfProperties.setResourceLoader(resourceLoader);

        pdfGeneratorService = new DocxPdfGenerator();
        pdfGeneratorService.setPdfProperties(pdfProperties);
    }

    @Test
    public void testDocx2Pdf() throws Exception {
        File file = new File("/Users/zhangpu/Downloads/p-yygsgz.pdf");
        AssetManagementRulesVo vo = new AssetManagementRulesVo();
        vo.setContractNo("JD-LJWPYY20181022001");

        List<String> pageInfos = Lists.newArrayList();
        pageInfos.add("1");
        pageInfos.add("2");
        pageInfos.add("3");
        pageInfos.add("4");
        pageInfos.add("5");
        pageInfos.add("6");
        pageInfos.add("7");

        List<Saler> list = Lists.newArrayList();
        list.add(new Saler("gz0001"));
        list.add(new Saler("gz0002"));
        list.add(new Saler("gz0003"));
        list.add(new Saler("gz0004"));
        vo.setSalers(list);
        vo.setHoldingPersonId("LJXJ1230092344dd2123");
        vo.setHoldingPerson("枭洪");
        vo.setLegalPerson("小小洪");
        vo.setLegalPersonAddress("重庆市渝北区鸳鸯街道美利山公园城市189-1-1");
        vo.setOperationCompany("重庆市冲天炮科技有限公司");
        vo.setHoldCity("重庆市");

        AssetShop assetShop = new AssetShop();
        assetShop.setCertAddress("重庆市渝北区两江星界3栋11-8");
        assetShop.setCertId("LJXJ1230092344dd2123");
        vo.setAssetShop(assetShop);

        pdfGeneratorService.generate("assetManagementRules.docx", vo, file);
    }


}

