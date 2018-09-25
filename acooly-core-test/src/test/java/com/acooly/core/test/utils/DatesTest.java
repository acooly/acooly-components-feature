/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-25 16:50 创建
 */
package com.acooly.core.test.utils;

import com.acooly.core.utils.Dates;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * @author zhangpu 2018-07-25 16:50
 */
@Slf4j
public class DatesTest {

    @Test
    public void testSub() throws Exception{

        Date birthday = Dates.parse("1982-07-15");
        System.out.println(Dates.sub(new Date(),birthday,Calendar.YEAR));

    }

}
