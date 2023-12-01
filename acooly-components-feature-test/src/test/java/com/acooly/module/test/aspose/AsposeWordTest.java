/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-12-12 20:32
 */
package com.acooly.module.test.aspose;

import com.acooly.module.pdf.support.AsposePdfs;
import com.aspose.pdf.GoToAction;
import com.aspose.pdf.OutlineItemCollection;
import com.aspose.words.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author zhangpu
 * @date 2022-12-12 20:32
 */
@Slf4j
public class AsposeWordTest {

    /**
     * 测试前替换为你自己的路径
     */
    String testDir = "/Users/zhangpu/workspace/acooly/v5.2/acooly-components-feature-5.2/acooly-components-feature-test/src/test/resources/pdf";

    String wordFilePath = testDir + "/solution.docx";
    String pdfFilePath = testDir + "/solution.pdf";

    /**
     * 根据你的开发机器的字体目录，设置字体目录
     * 如果是windows，你可以不管这个，因为aspose会自动加载系统字体
     * 如果是mac，你需要从windows拷贝这些字体，然后制定字体目录
     */
    String fontsDir = "/Users/zhangpu/software/fonts";

    /**
     * 设置字体资源
     * 不设置会导致word转pdf的时候字体丢失
     */
    public void setFontsSources() {

        //将用户目录字体添加到字体源中
        FontSourceBase[] originalFontSources = FontSettings.getDefaultInstance().getFontsSources();
        FolderFontSource folderFontSource = new FolderFontSource(fontsDir, true);

        // originalFontSources[0],
        FontSourceBase[] updatedFontSources = {folderFontSource};
        FontSettings.getDefaultInstance().setFontsSources(updatedFontSources);
    }

    @Test
    public void testWordToPdf() {
        FileOutputStream os = null;
        try {

            AsposeWordCrack.crack21_6();
//            setFontsSources();
            //生成一个空的PDF文件
            File file = new File(pdfFilePath);
            os = new FileOutputStream(file);
            //要转换的word文件
            Document doc = new Document(wordFilePath);
            doc.save(os, SaveFormat.PDF);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testToPdf() {
        AsposePdfs.wordToPdf(wordFilePath, pdfFilePath, fontsDir);
    }

    @Test
    public void testToPdfWithStream() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        File wordFile = resourceLoader.getResource(wordFilePath).getFile();
        String wordPdfFilePath = wordFile.getAbsolutePath().replace(".docx", ".pdf");
        log.info("wordFilePath:{}", wordFile.getAbsolutePath());
        log.info("wordPdfFilePath:{}", wordPdfFilePath);
        AsposePdfs.wordToPdf(wordFile.getAbsolutePath(), wordPdfFilePath, fontsDir);
    }

    @Test
    public void testAddBookmarks() throws Exception {
        com.aspose.pdf.Document pdfDocument = new com.aspose.pdf.Document(pdfFilePath);
        // Delete all bookmarks
        pdfDocument.getOutlines().delete();
        // Create a bookmark object
        OutlineItemCollection pdfOutline = new OutlineItemCollection(pdfDocument.getOutlines());
        pdfOutline.setTitle("Test Outline");
        // Set the destination page number
        pdfOutline.setAction(new GoToAction(pdfDocument.getPages().get_Item(2)));
        // Create a child bookmark object
        OutlineItemCollection pdfChildOutline = new OutlineItemCollection(pdfDocument.getOutlines());
        pdfChildOutline.setTitle("Child Outline");
        // Add child bookmark in parent bookmark's collection
        pdfOutline.add(pdfChildOutline);
        // Add a bookmark in the document's outline collection.
        pdfDocument.getOutlines().add(pdfOutline);
        // Save the update document
        pdfDocument.save(pdfFilePath);
    }

}
