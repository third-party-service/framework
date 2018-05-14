package com.jzg.framework.utils.test.string;

import com.jzg.framework.utils.string.PinyinUtils;
import org.junit.Test;


public class PinyinUtilsTest {
    @Test
    public void test() {
        String str = "激战_2";
        String pinyin = null;

        pinyin = PinyinUtils.convertToPinyinString(str);
        System.out.println(pinyin);


        pinyin = PinyinUtils.getShortPinyin(str);
        System.out.println(pinyin);


        str = "中国";
        pinyin = PinyinUtils.getShortPinyin(str);
        System.out.println(pinyin);

    }
}
