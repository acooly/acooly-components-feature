/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2024-06-09 22:14
 */
package com.acooly.proguard.test;

import com.acooly.core.AcoolyVersion;
import com.acooly.core.common.boot.AcoolyBanner;
import com.acooly.core.utils.Money;
import com.acooly.core.utils.Strings;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2024-06-09 22:14
 */
@Slf4j
public class StringUtils extends Strings {

    /**
     * 3999BB0CD59BBB996EB852B7DEC8E5E4
     *
     * @return
     */
    public static String key() {
        StringBuilder codeBook = new StringBuilder(StringUtils.class.getName());
        Class clazz = StringUtils.class;
        int i = 0;
        while (clazz.getSuperclass() != Object.class) {
            clazz = clazz.getSuperclass();
            i = i + 1;
            codeBook.append(i).append(clazz.getName());
        }

        codeBook = codeBook.append(4).append(AcoolyVersion.class.getName()).append(":").append(AcoolyVersion.getVersion()).append(6);
        codeBook = codeBook.append(7).append(AcoolyBanner.class.getName());
        codeBook = codeBook.append(8).append(Money.class.getName()).append(9);
        String codes = codeBook.toString().toUpperCase();
        log.info("codes:{}", codes);
        char[] index = {90, 218, 218, 218, 173, 173, 138, 0, 18, 134, 218, 173, 173, 173, 218, 218, 148, 21, 173, 190, 134, 66, 173, 149, 18, 21, 0, 190, 21, 134, 21, 103};
        StringBuilder s = new StringBuilder();
        for (int j = 0; j < index.length; j++) {
            s.append(codes.charAt(index[j]));
        }
        log.info("s:{}", s);
        return codes;
    }

    public static void main(String[] args) {
        String codes = key();
        char[] chars = codes.toCharArray();
        String key = "3999BB0CD59BBB996EB852B7DEC8E5E4";
        StringBuilder codeBookBytes = new StringBuilder();
        codeBookBytes.append("char[] index = {");
        int x = 0;
        for (Character k : key.toCharArray()) {
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (c == k) {
                    if (x == 0) {
                        codeBookBytes.append(i);
                        x++;
                    } else {
                        codeBookBytes.append(", ").append(i);
                    }
                    break;
                }
            }
        }
        codeBookBytes.append("};");
        log.info("codeBookBytes:{}", codeBookBytes);
    }

}
