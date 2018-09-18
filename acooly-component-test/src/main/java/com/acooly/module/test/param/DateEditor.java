package com.acooly.module.test.param;

import com.acooly.core.utils.Dates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * @author qiuboboy@qq.com
 * @date 2018-09-10 17:45
 */
@Slf4j
public class DateEditor extends PropertyEditorSupport {
    @Override
    public String getAsText() {
        return Dates.format((Date) getValue());
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            setValue(Dates.parse(text));
        } else {
            setValue(null);
        }
    }
}
