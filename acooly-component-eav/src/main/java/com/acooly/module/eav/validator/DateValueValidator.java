package com.acooly.module.eav.validator;

import com.acooly.core.common.exception.OrderCheckException;
import com.acooly.core.utils.Dates;
import com.acooly.module.eav.entity.EavAttribute;

import java.util.Date;

import static com.acooly.core.utils.Dates.CHINESE_DATETIME_FORMAT_LINE;
import static com.acooly.core.utils.Dates.CHINESE_DATE_FORMAT_LINE;

/**
 * @author qiuboboy@qq.com
 * @date 2018-06-27 14:22
 */
public class DateValueValidator extends ValueValidator {
    public DateValueValidator(EavAttribute eavAttribute) {
        super(eavAttribute);
    }

    @Override
    protected Object doValidate(Object value) {
        if (value instanceof Date) {
            return Dates.format((Date) value);
        }
        String v = value.toString();
        try {
            Dates.parse(v, CHINESE_DATE_FORMAT_LINE);
        } catch (Exception e) {
            try {
                Dates.parse(v, CHINESE_DATETIME_FORMAT_LINE);
            } catch (Exception ex) {
                OrderCheckException.throwIt(eavAttribute.getName(), "值[%s]日期格式不匹配:%s 或者 %s", value, CHINESE_DATE_FORMAT_LINE, CHINESE_DATETIME_FORMAT_LINE);
            }
        }
        return v;
    }
}
