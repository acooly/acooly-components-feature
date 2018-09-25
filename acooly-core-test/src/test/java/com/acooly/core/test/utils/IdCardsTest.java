package com.acooly.core.test.utils;

import com.acooly.core.common.enums.Zodiac;
import com.acooly.core.utils.IdCards;
import org.junit.Test;

/**
 * @author qiuboboy@qq.com
 * @date 2018-07-31 13:30
 */
public class IdCardsTest {
    @Test
    public void name() {
        String cardNo = "500234198612251859";
        System.out.println( IdCards.verify(cardNo));
        System.out.println( IdCards.parse(cardNo));
        System.out.println( Zodiac.to(12,23));
        System.out.println( Zodiac.to(1,19));
        System.out.println( Zodiac.to(1,20));

    }
}
