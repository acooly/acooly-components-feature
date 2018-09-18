package com.acooly.module.test.param;

import com.acooly.core.utils.Money;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

public class MoneyEditor extends PropertyEditorSupport {
    @Override
    public String getAsText() {
        return getValue().toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            Money m = new Money();
            m.setCent(Long.parseLong(text));
            setValue(m);
        } else {
            setValue(null);
        }
    }
}
