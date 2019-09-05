package com.acooly.module.eav.validator;

import com.acooly.core.common.exception.OrderCheckException;
import com.acooly.core.utils.enums.WhetherStatus;
import com.acooly.module.eav.entity.EavAttribute;
import lombok.Getter;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author qiuboboy@qq.com
 * @date 2018-06-27 11:10
 */
@Getter
public abstract class ValueValidator implements Function<Object, Object> {
    protected EavAttribute eavAttribute;

    public ValueValidator(EavAttribute eavAttribute) {
        this.eavAttribute = eavAttribute;
    }

    @Override
    public Object apply(Object value) {
        if (value == null) {
            if (eavAttribute.getNullable() == null || eavAttribute.getNullable() == WhetherStatus.yes) {
                return null;
            } else {
                throw new OrderCheckException(eavAttribute.getName(), "不能为空");
            }
        }
        try {
            return doValidate(value);
        } catch (Exception e) {
            if (e instanceof OrderCheckException) {
                throw e;
            }
            throw new OrderCheckException(eavAttribute.getName(), e.getMessage());
        }
    }


    protected abstract Object doValidate(Object value);

    protected void state(boolean expression, Supplier<String> message) {
        if (!expression) {
            throw new IllegalStateException(message.get());
        }
    }

    protected void state(boolean expression, String format, Object... params) {
        if (!expression) {
            throw new IllegalStateException(String.format(format, params));
        }
    }
}
