package com.acooly.module.eav.validator;

import com.acooly.module.eav.entity.EavAttribute;

/**
 * @author qiuboboy@qq.com
 * @date 2018-06-27 14:22
 */
public class DoubleValueValidator extends ValueValidator {
    public DoubleValueValidator(EavAttribute eavAttribute) {
        super(eavAttribute);
    }

    @Override
    protected Object doValidate(Object value) {
        Double d = Double.parseDouble(value.toString());
        if (eavAttribute.getMinimum() != null) {
            state(d >= eavAttribute.getMinimum(), "值[%s]小于最小值:%s", value, eavAttribute.getMinimum());
        }
        if (eavAttribute.getMaximum() != null) {
            state(d < eavAttribute.getMaximum(), "值[%s]大于或等于最大值:%s", value, eavAttribute.getMaximum());
        }
        return d;
    }
}
