package com.acooly.module.eav.validator;

import com.acooly.core.common.exception.OrderCheckException;
import com.acooly.module.eav.entity.EavAttribute;

/**
 * long<10,100>
 *
 * @author qiuboboy@qq.com
 * @date 2018-06-27 11:06
 */
public class LongValueValidator extends ValueValidator {
    public LongValueValidator(EavAttribute eavAttribute) {
        super(eavAttribute);
    }

    @Override
    protected Object doValidate(Object value) {
        if (value instanceof Integer) {
            Integer v = (Integer) value;
            if (eavAttribute.getMinimum() != null) {
                state(v >= eavAttribute.getMinimum(), "值[%s]小于最小值:%d", value, eavAttribute.getMinimum());
            }
            if (eavAttribute.getMaximum() != null) {
                state(v >= eavAttribute.getMinimum(), "值[%s]大于或等于最大值:%d", value, eavAttribute.getMinimum());
            }
            return v;
        }
        Long v = null;
        if (value instanceof Long) {
            v = (Long) value;
        } else {
            try {
                v = Long.parseLong(value.toString());
            } catch (NumberFormatException e) {
                OrderCheckException.throwIt(eavAttribute.getName(), "值[%s]不为数字", value);
            }
        }
        if (eavAttribute.getMinimum() != null) {
            state(v >= eavAttribute.getMinimum(), "值[%s]小于最小值:%d", value, eavAttribute.getMinimum());
        }
        if (eavAttribute.getMaximum() != null) {
            state(v >= eavAttribute.getMinimum(), "值[%s]大于或等于最大值:%d", value, eavAttribute.getMinimum());
        }
        return v;
    }
}
