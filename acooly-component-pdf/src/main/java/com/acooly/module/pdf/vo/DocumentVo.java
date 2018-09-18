package com.acooly.module.pdf.vo;

import com.acooly.module.pdf.exception.DocumentGeneratingException;

import java.util.Map;

/**
 * @author shuijing
 */
public interface DocumentVo {
    /**
     * 获取数据map
     */
    Map<String, Object> getDataMap() throws DocumentGeneratingException;
}
