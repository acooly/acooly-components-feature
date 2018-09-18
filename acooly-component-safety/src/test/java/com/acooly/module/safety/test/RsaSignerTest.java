/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-10-08 00:46 创建
 */
package com.acooly.module.safety.test;

import com.acooly.module.safety.signature.RsaSigner;
import com.acooly.module.safety.support.CodecEnum;
import com.acooly.module.safety.support.KeyPair;
import org.junit.Test;

/**
 * @author zhangpu 2017-10-08 00:46
 */
public class RsaSignerTest {

    String plain = "为是人民服务";


    @Test
    public void testPublicProviteKey() {
        // key来自文件
//        KeyPair keyPair = new KeyPair("classpath:keystore/pbpr/pbkey.key",
//                "classpath:keystore/pbpr/prkey.key");
        // key来自字符串
        KeyPair keyPair = new KeyPair("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAowX9EDKLvdc5lSito+A24s9YjQ04f5SzoF5NvJ3oKxaGRwxC0ANohw7Pl8+eIbsfaa/VTUFa4tYbKhefiXIneNk2bfrDhEByBI89wWNCnkGssb1/OIBNWy50GXN4+raEQIj7daJDhkKF98PuMF0pjhwro9oEvmHEiRadOdkRiNaiSXJt/nD0mnScwxB5Qb0xEdvitmzfl2HYL7k5m1CCvoQtXq4DukzvXKwz6RireRxEl4N46vy/QcMcrOxROcozciAkUDvpAb2ggnJ0E7nmyU60VveJEn/utrNMlDXkIqkZeUNNoyVQkbDRCzx6JYOeJrPameFc19vvK0woEAUaKwIDAQAB",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCjBf0QMou91zmVKK2j4Dbiz1iNDTh/lLOgXk28negrFoZHDELQA2iHDs+Xz54hux9pr9VNQVri1hsqF5+Jcid42TZt+sOEQHIEjz3BY0KeQayxvX84gE1bLnQZc3j6toRAiPt1okOGQoX3w+4wXSmOHCuj2gS+YcSJFp052RGI1qJJcm3+cPSadJzDEHlBvTER2+K2bN+XYdgvuTmbUIK+hC1ergO6TO9crDPpGKt5HESXg3jq/L9Bwxys7FE5yjNyICRQO+kBvaCCcnQTuebJTrRW94kSf+62s0yUNeQiqRl5Q02jJVCRsNELPHolg54ms9qZ4VzX2+8rTCgQBRorAgMBAAECggEBAKCSvXi+0LXk0GsC7kWEC4vL/8SvSP8hUOyc9qTaRFxsi0Zf5U9OjB67j+5QKcm/rmlDMC4wOX1Etuz258VSE18NVHdvkDUr5SJolMNvKacHuTo5FJ5qgcdWMjT/cCJCtHF8e3KaqTgNPjeVXX91xACd7r4teI6tbhA5nhPko0jLzfyiWDAWm8y/hGH2Vg7QxH0hSxoJG7tMFgH5X5itti+vNP+opg0R0IbDPRwFoAfnhQq0+LDvtuBrRt8Wucim5y+NjdS37cZouByRxn2hlvryWlwYFODE8FQDsVZ6lJ8rMo0EeZHGTwIHgjdp5uOeRgi7F2SGfxxDFwL2Mv3cT0ECgYEAz+PkYjum4ZvbKPuNZVtm83gtYeYkhdYaDYDozS96kNcylvFU4C1/3FQsJQ5Ni1jHX1AyKLDcGRgKxGp5C+KC4KI713Rfi49yC2Gbh9G9vsLKnETLBeJuomQjCUUBIxKdE2xrjbq9NRI/t73r+pSXtvhP8ewGsY2le80p7Ly/++0CgYEAyMAJgXzehbQn/otpWPq1uU9KyUuN95OAAXNEJ7Gc14yflYsVyvqjZTvhyqVa/Qv3D6f62Hflq6augy0LZTJYra64DWWoQq0O9ZZQlNJ/jHP/lW2RXd/K7Loqdqpvf8NNNuhsvgLd755xv41qxkMjxEEi1nAfgDntqEuFettpG3cCgYBq4lGGzAKmFjrc3FC7Xv4UiaJTpj767LRiYvfOnu2WUH47KwMna11Ey6YSu0LVNST0dUgEscHHyRuoFtH7cU827VJ1GOkS2QKapWmQsB6tWHtRUvFLKjsVlQSKfIb0IFrQJLs7mFo1UheegbA1GnbSZOK9kEt0uE3v0/ENPZI7lQKBgBKHbxktjlhm7X4YHgwhb95/zBZSS/M7AQihO/wx75kRmLUz1joa8jPo2AldQ82xagde6M9MGb3DL75ZzQB1Kiw1wTUzWdl1objXO+7QkwdCuadwKoewnUlGUZ8MUihHvXvA7jCHkTaJuFQG2IEeN1Ho9S83wCCUuDPdbJ+PWt83AoGACPRgryOLIjTHuNKM81hKhe2LlngwtLCK9l4DCNfc/afc3FY+818a35VUrOXqCaaI52zdNYTRqQX9Qj4YnnpuTwI8zcnq3hSJvb2D2b2LfXfhLxVtb1uvJPNxXSGo7iszZqVCgwafG5eJ7ezXnQQN7FRWeCZgMgOX5Z2j6BLOutY=");

        keyPair.setSignatureCodec(CodecEnum.HEX);

        RsaSigner signer = new RsaSigner();

        String plain = "为是人民服务";
        System.out.println("plain: " + plain);
        String signature = signer.sign(plain, keyPair);
        System.out.println("signature: " + signature);
        boolean verifyResult = false;
        try {
            signer.verify(plain, keyPair, signature);
            verifyResult = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("verify: " + verifyResult);
    }


}
