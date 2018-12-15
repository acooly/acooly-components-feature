package com.acooly.module.pdf;

import com.acooly.module.pdf.vo.DocumentVo;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author xiyang@acooly.cn
 * <p>
 * 扩展的pdf生成器，支持docx和freemaker模版两种形式，新开发请使用此接口
 * @date 2018-09-21 17:37
 */
public interface PdfGeneratorService {

    /**
     * @param templateName（带后缀）
     * @param params（填充模版的数据）
     * @param outputFile（输出文件）
     */
    void generate(String templateName, Map<String, Object> params, File outputFile) throws IOException;

    /**
     * @param templateName（带后缀）
     * @param documentVo（填充模版的数据对象）
     * @param outputFile（输出文件）
     */
    void generate(String templateName, DocumentVo documentVo, File outputFile) throws IOException;

    /**
     * @param templateName（带后缀）
     * @param params（填充模版的数据）
     * @param outputStream（输出文件）
     */
    void generate(String templateName, Map<String, Object> params, OutputStream outputStream) throws IOException;

    /**
     * @param templateName（带后缀）
     * @param documentVo（填充模版的数据对象）
     * @param outputStream（输出文件）
     */
    void generate(String templateName, DocumentVo documentVo, OutputStream outputStream) throws IOException;

}
