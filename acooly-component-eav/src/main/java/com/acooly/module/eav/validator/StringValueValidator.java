package com.acooly.module.eav.validator;

import com.acooly.module.eav.entity.EavAttribute;
import com.google.common.base.Strings;

import java.util.regex.Pattern;

/**
 * @author qiuboboy@qq.com
 * @date 2018-06-27 14:30
 */
public class StringValueValidator extends ValueValidator {
    private Pattern pattern;

    public StringValueValidator(EavAttribute eavAttribute) {
        super(eavAttribute);
        if (!Strings.isNullOrEmpty(eavAttribute.getRegex())) {
            pattern = Pattern.compile(eavAttribute.getRegex());
        }
    }

    @Override
    protected Object doValidate(Object value) {
        String v = value.toString();
        if (eavAttribute.getMinLength() != null) {
            state(v.length() >= eavAttribute.getMinLength(), "值[%s]小于最小长度:%d", value, eavAttribute.getMinLength());
        }
        if (eavAttribute.getMaxLength() != null) {
            state(v.length() < eavAttribute.getMaxLength(), "值[%s]大于或等于最长度:%d", value, eavAttribute.getMaxLength());
        }
        if (pattern != null) {
            state(pattern.matcher(v).matches(), "值[%s]不符合正则表达式验证:%s", value, eavAttribute.getRegex());
        }
        return v;
    }
}
