package com.acooly.module.pdf.vo;

import com.acooly.module.pdf.exception.DocumentGeneratingException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 模板视图抽象类
 *
 * @author shuijing
 */
public abstract class AbstractDocumentVo implements DocumentVo {
    /**
     * VO转换为Map
     */
    @Override
    public Map<String, Object> getDataMap() throws DocumentGeneratingException {
        DocumentVo vo = this.getDocumentVo();
        Map<String, Object> map;
        try {
            map = toKeyValuePairs(vo);
        } catch (Exception e) {
            throw new DocumentGeneratingException(e);
        }
        return map;
    }

    /**
     * java bean转换属性为map对象，暂时不支持bean父类feild
     */
    protected Map<String, Object> toKeyValuePairs(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .collect(
                        Collectors.toMap(
                                Field::getName,
                                field -> {
                                    try {
                                        field.setAccessible(true);
                                        Object result = field.get(object);
                                        return result != null ? result : "";
                                    } catch (Exception e) {
                                        return "";
                                    }
                                }));
    }

    private DocumentVo getDocumentVo() {
        return this;
    }
}
