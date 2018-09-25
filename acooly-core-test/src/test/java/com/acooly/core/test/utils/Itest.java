package com.acooly.core.test.utils;

import org.junit.Test;
import sun.misc.JavaLangAccess;
import sun.misc.SharedSecrets;

/**
 * @author qiuboboy@qq.com
 * @date 2018-08-30 08:50
 */
public class Itest {
    @Test
    public void name() {
        JavaLangAccess javaLangAccess = SharedSecrets.getJavaLangAccess();
        System.out.println("x");
    }
}
