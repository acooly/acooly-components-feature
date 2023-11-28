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
import com.aspose.words.*;
import com.aspose.words.FolderFontSource;
import com.aspose.words.FontSourceBase;
import com.aspose.words.SaveFormat;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;

/**
 * @author zhangpu
 * @date 2022-12-12 20:32
 */
@Slf4j
public class AsposePdfTest {

    String wordFile = "/Users/zhangpu/temp/project.doc";
    String excelFile = "/Users/zhangpu/temp/excel.xlsx";
    String pdfFile = "/Users/zhangpu/temp/word.pdf";
    String pdfExcelFile = "/Users/zhangpu/temp/excel.pdf";

//    //移除文字水印
//    public static boolean removeWatermark(File file) {
//        try {
//            //通过文件名加载文档
//            PDDocument document = Loader.loadPDF(file);
//            PDPageTree pages = document.getPages();
//            Iterator<PDPage> iter = pages.iterator();
//            while (iter.hasNext()) {
//                PDPage page = iter.next();
//                //去除文字水印
//                replaceText(page, "Evaluation Only. Created with Aspose.Words. Copyright 2003-2021 Aspose", "");
//                replaceText(page, "Pty Ltd.", "");
//                replaceText(page, "Created with an evaluation copy of Aspose.Words. To discover the full", "");
//                replaceText(page, "versions of our APIs please visit: https://products.aspose.com/words/", "");
//                replaceText(page, "This document was truncated here because it was created in the Evaluation", "");
//                //去除图片水印
//                removeImage(page, "X1");
//            }
//            document.removePage(document.getNumberOfPages() - 1);
//            file.delete();
//            document.save(file);
//            document.close();
//            return true;
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return false;
//        }
//
//    }
//
//    //替换pdf文本内容
//    public static void replaceText(PDPage page, String searchString, String replacement) throws IOException {
//        PDFStreamParser parser = new PDFStreamParser(page);
//        List<?> tokens = parser.parse();
//        for (int j = 0; j < tokens.size(); j++) {
//            Object next = tokens.get(j);
//            if (next instanceof Operator) {
//                Operator op = (Operator) next;
//                String pstring = "";
//                int prej = 0;
//                if (op.getName().equals("Tj")) {
//                    COSString previous = (COSString) tokens.get(j - 1);
//                    String string = previous.getString();
//                    string = string.replaceFirst(searchString, replacement);
//                    previous.setValue(string.getBytes());
//                } else if (op.getName().equals("TJ")) {
//                    COSArray previous = (COSArray) tokens.get(j - 1);
//                    for (int k = 0; k < previous.size(); k++) {
//                        Object arrElement = previous.getObject(k);
//                        if (arrElement instanceof COSString) {
//                            COSString cosString = (COSString) arrElement;
//                            String string = cosString.getString();
//
//                            if (j == prej) {
//                                pstring += string;
//                            } else {
//                                prej = j;
//                                pstring = string;
//                            }
//                        }
//                    }
//                    if (searchString.equals(pstring.trim())) {
//                        COSString cosString2 = (COSString) previous.getObject(0);
//                        cosString2.setValue(replacement.getBytes());
//                        int total = previous.size() - 1;
//                        for (int k = total; k > 0; k--) {
//                            previous.remove(k);
//                        }
//                    }
//                }
//            }
//        }
//        List<PDStream> contents = new ArrayList<>();
//        Iterator<PDStream> streams = page.getContentStreams();
//        while (streams.hasNext()) {
//            PDStream updatedStream = streams.next();
//            OutputStream out = updatedStream.createOutputStream(COSName.FLATE_DECODE);
//            ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
//            tokenWriter.writeTokens(tokens);
//            contents.add(updatedStream);
//            out.close();
//        }
//        page.setContents(contents);
//    }
//
//    //移除图片水印
//    public static void removeImage(PDPage page, String cosName) {
//        PDResources resources = page.getResources();
//        COSDictionary dict1 = resources.getCOSObject();
//        resources.getXObjectNames().forEach(e -> {
//            if (resources.isImageXObject(e)) {
//                COSDictionary dict2 = dict1.getCOSDictionary(COSName.XOBJECT);
//                if (e.getName().equals(cosName)) {
//                    dict2.removeItem(e);
//                }
//            }
//            page.setResources(new PDResources(dict1));
//        });
//    }

    /**
     * 设置字体资源
     * 不设置会导致word转pdf的时候字体丢失
     */
    public static void setFontsSources() {
        String fontsDir = "/usr/share/fonts";
        fontsDir = "/System/Library/Fonts";
        fontsDir = "/Users/zhangpu/workspace/acooly/v5.2/acooly-components-feature/acooly-components-feature-test/src/main/resources/pdf/fonts";
        fontsDir = "/Users/zhangpu/temp/fonts";

        //将用户目录字体添加到字体源中
        FontSourceBase[] originalFontSources = FontSettings.getDefaultInstance().getFontsSources();
        FolderFontSource folderFontSource = new FolderFontSource(fontsDir, true);

        // originalFontSources[0],
        FontSourceBase[] updatedFontSources = {folderFontSource};
        FontSettings.getDefaultInstance().setFontsSources(updatedFontSources);
    }

    @Test
    public void doc2Pdf() {
        long old = System.currentTimeMillis();
        try {
            //新建一个pdf文档
            String pdfPath = wordFile.substring(0, wordFile.lastIndexOf(".")) + ".pdf";
            File file = new File(pdfPath);
            FileOutputStream os = new FileOutputStream(file);
            //Address是将要被转化的word文档
            Document doc = new Document(wordFile);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, SaveFormat.PDF);
            os.close();
            //去除水印
//            removeWatermark(new File(pdfPath));
            //转化用时
            long now = System.currentTimeMillis();
            System.out.println("Word 转 Pdf 共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            System.out.println("Word 转 Pdf 失败...");
            e.printStackTrace();
        }
    }


    @Test
    public void testWordToPdf() {
        FileOutputStream os = null;
        try {

            AsposeWordCrack.crack21_6();
//            setFontsSources();
            //生成一个空的PDF文件
            File file = new File(pdfFile);
            os = new FileOutputStream(file);
            //要转换的word文件
            Document doc = new Document(wordFile);
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
        String fontsDir = "/Users/zhangpu/temp/fonts";
        AsposePdfs.wordToPdf(wordFile, pdfFile, fontsDir = "/Users/zhangpu/temp/fonts");
    }

    @Test
    public void testToPdfWithStream() throws IOException {
        String fontsDir = "/Users/zhangpu/temp/fonts";
        InputStream is = new FileInputStream(new File(wordFile));
        OutputStream os = new FileOutputStream(new File(pdfFile));
        AsposePdfs.wordToPdf(is, os, fontsDir = "/Users/zhangpu/temp/fonts");
    }


//    public void testExcelToPdf() {
//        try {
//            AsposeWordCrack.crack21_6();
//            Workbook workbook = new Workbook(excelFile);
//            PdfSaveOptions pdfSaveOpt = new PdfSaveOptions(SaveFormat.PDF);
//            PdfSecurityOptions securityOptions = new PdfSecurityOptions();
//            securityOptions.setAccessibilityExtractContent(false);
//            pdfSaveOpt.setSecurityOptions(securityOptions);
//            workbook.save(pdfExcelFile, pdfSaveOpt);
//        } catch (Exception e) {
//            throw new RuntimeException("转换失败", e);
//        }
//    }

}
