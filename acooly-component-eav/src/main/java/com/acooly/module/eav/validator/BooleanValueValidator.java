package com.acooly.module.eav.validator;

import com.acooly.module.eav.entity.EavAttribute;

/**
 * @author qiuboboy@qq.com
 * @date 2018-06-27 14:22
 */
public class BooleanValueValidator extends ValueValidator {

    public static final String FALSE_STRING = "0";
    public static final String TRUE_STRING = "1";

    public BooleanValueValidator(EavAttribute eavAttribute) {
        super(eavAttribute);
    }

    @Override
    protected Object doValidate(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value ? 1 : 0;
        }
        if (value instanceof Number) {
            int i = ((Number) value).intValue();
            if (i == 0 || i == 1) {
                return value;
            }
        }
        String v = value.toString();
        if(v.equals(FALSE_STRING)){
            return 0;
        }
        if(v.equals(TRUE_STRING)){
            return 1;
        }
        state(v.equals("false") || v.equals("true"), "值[%s]应该为布尔[0,1]", value);
        return Boolean.valueOf(v) ? 1 : 0;
    }
}
