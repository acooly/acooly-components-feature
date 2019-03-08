package com.acooly.module.eav.validator;

import com.acooly.core.utils.Money;
import com.acooly.module.eav.entity.EavAttribute;

/**
 * @author zhangpu
 * @date 2019-03-08 14:22
 */
public class MoneyValueValidator extends ValueValidator {
    public MoneyValueValidator(EavAttribute eavAttribute) {
        super(eavAttribute);
    }

    @Override
    protected Object doValidate(Object value) {
        Money money = Money.amout(value.toString());
        if (eavAttribute.getMinimum() != null) {
            state(money.getCent() >= eavAttribute.getMinimum(), "值[%s]小于最小值:%s", money, Money.cent(eavAttribute.getMinimum()));
        }
        if (eavAttribute.getMaximum() != null) {
            state(money.getCent() < eavAttribute.getMaximum(), "值[%s]大于或等于最大值:%s", money, Money.cent(eavAttribute.getMaximum()));
        }
        return money;
    }
}
