package com.acooly.module.eav.validator;

import com.acooly.module.eav.entity.EavAttribute;
import com.google.common.base.Splitter;

import java.util.List;

/**
 * @author qiuboboy@qq.com
 * @date 2018-06-27 14:24
 */
public class EnumValueValidator extends ValueValidator {
    private List<String> values;

    public EnumValueValidator(EavAttribute eavAttribute) {
        super(eavAttribute);
        values = Splitter.on(",").trimResults().splitToList(eavAttribute.getEnumValue());
    }

    @Override
    protected Object doValidate(Object value) {
        state(values.contains(value), "值[%s]应该为:%s", value, eavAttribute.getEnumValue());
        return value.toString();
    }
}
