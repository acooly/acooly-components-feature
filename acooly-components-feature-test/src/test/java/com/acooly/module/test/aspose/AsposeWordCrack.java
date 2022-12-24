/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-12-23 19:24
 */
package com.acooly.module.test.aspose;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Modifier;

/**
 * @author zhangpu
 * @date 2022-12-23 19:24
 */
@Slf4j
public class AsposeWordCrack {

    public static void main(String[] args) {
        crack21_6();
    }

    /**
     * 破解
     */
    public static void crack21_6() {
        try {
            Class<?> aClass = Class.forName("com.aspose.words.zzXyu");
            java.lang.reflect.Field zzZXG = aClass.getDeclaredField("zzZXG");
            zzZXG.setAccessible(true);

            java.lang.reflect.Field modifiersField = zzZXG.getClass().getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(zzZXG, zzZXG.getModifiers() & ~Modifier.FINAL);
            zzZXG.set(null, new byte[]{76, 73, 67, 69, 78, 83, 69, 68});
        } catch (Exception e) {
            e.printStackTrace();
            log.error("apose word 破解异常");
        }
    }

}
