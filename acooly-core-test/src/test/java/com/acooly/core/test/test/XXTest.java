package com.acooly.core.test.test;

import com.acooly.core.utils.Money;
import com.acooly.module.test.param.CsvParameter;
import junitparams.JUnitParamsRunner;
import junitparams.naming.TestCaseName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

@RunWith(JUnitParamsRunner.class)
@Slf4j
public class XXTest {

    @Test
    //读取csv文件，csv文件第一行为头信息
    @CsvParameter(value = "test.csv")
    @TestCaseName("id={0}")
    public void test1(int id, int age, String name, Money m,Date date) {
        log.info("id={},age={},name={},money={},date={}", id, age, name, m,date);
    }

    @Test
    @CsvParameter(value = "test.csv")
    @TestCaseName("id={0}")
    //转换csv文件内容为对象
    public void test2(ParamDTO dto) {
        log.info("{}", dto);
    }

    @Test
    @CsvParameter(value = "test1.csv")
    @TestCaseName("id={0}")
    //转换csv文件内容为对象
    public void test3(int id) {
        log.info("{}", id);
    }

    @Test
    @CsvParameter(value = "test2.csv")
    @TestCaseName("id={0}")
    //转换csv文件内容为对象
    public void test4(Integer id, String name) {
        log.info("{}->{}", id, name);
    }

    @Data
    public static class ParamDTO {
        private Long id;
        private int age;
        private String name;
        private Money m;
        private Date date;
    }
}
