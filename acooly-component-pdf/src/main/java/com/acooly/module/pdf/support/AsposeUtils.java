/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2023-11-27 20:39
 */
package com.acooly.module.pdf.support;

import com.acooly.module.pdf.support.office.WordUtils;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;

/**
 * Aspose 工具类
 */
@Slf4j
public class AsposeUtils {

    /**
     * word格式转换（doc转Docx）
     *
     * @param docFilePath  输入：格式doc的文件路径
     * @param docxFilePath 输出：格式docx的文件路径
     */
    public static void docToDocx(@NotBlank String docFilePath, @NotBlank String docxFilePath) {
        WordUtils.docToDocx(docFilePath, docxFilePath);
    }


}
