/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-10-11 12:10 创建
 */
package com.acooly.core.test.appservice;

import com.acooly.core.common.exception.OrderCheckException;
import com.acooly.core.common.facade.DtoBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author qiubo@yiji.com
 */
@Getter
@Setter
public class AppDto extends DtoBase {

    @NotBlank(groups = Test1.class)
    private String a1;

    @NotBlank
    private String a2;

    @NotBlank(groups = Test2.class)
    private String a3;

    @NotBlank(groups = {Test1.class, Test2.class})
    private String a4;

    private String a5;

    /**
     * 当Test1校验组被执行时，调用此方法
     */
    public void checkOnTest1(OrderCheckException e) {
        if (a1 != null) {
            if (a1.startsWith("a")) {
                e.addError("a1", "不能以a开头");
            }
        }
    }

    /**
     * 当Test2校验组被执行时，调用此方法
     */
    public void checkOnTest2(OrderCheckException e) {
        //
    }

    interface Test1 {
    }

    interface Test2 {
    }
}
