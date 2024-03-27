package com.acooly.module.pdf.support.office;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Modifier;

@Slf4j
public class WordUtils {


    /**
     * word格式转换（doc转Docx）
     *
     * @param docFilePath  输入：格式doc的文件路径
     * @param docxFilePath 输出：格式docx的文件路径
     */
    public static void docToDocxConverter(@NotBlank String docFilePath, @NotBlank String docxFilePath) {
        try {
            //去除 水印
            crackWord21_6();
            // 加载DOC文件
            Document doc = new Document(docFilePath);
            // 保存为DOCX文件
            doc.save(docxFilePath, SaveFormat.DOCX);
        } catch (Exception e) {
            throw new RuntimeException("docToDocx转换失败", e);
        }
    }

    /**
     * 破解 word 21.6版本
     * 去除 水印
     */
    public static void crackWord21_6() {
        try {
            Class<?> aClass = Class.forName("com.aspose.words.zzXyu");
            java.lang.reflect.Field zzZXG = aClass.getDeclaredField("zzZXG");
            zzZXG.setAccessible(true);

            java.lang.reflect.Field modifiersField = zzZXG.getClass().getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(zzZXG, zzZXG.getModifiers() & ~Modifier.FINAL);
            zzZXG.set(null, new byte[]{76, 73, 67, 69, 78, 83, 69, 68});
        } catch (Exception e) {
            log.error("aspose word 破解异常");
        }
    }

}
