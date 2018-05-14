package com.jzg.framework.utils.string;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 汉字转拼音类
 */
public final class PinyinUtils {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(PinyinUtils.class);

    private PinyinUtils() {
    }

    /**
     * 汉字转拼音
     * 例如：建外SOHO => jianwaiSOHO
     *
     * @param str 汉字
     * @return 拼音
     */
    public static String convertToPinyinString(String str) {
        String result = null;
        try {
            result = PinyinHelper.convertToPinyinString(str, "", PinyinFormat.WITHOUT_TONE);
        } catch (PinyinException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 汉字转化为短拼音
     * 例如： 建外 => jw
     *
     * @param str 汉字
     * @return 短拼音
     */
    public static String getShortPinyin(String str) {
        String result = null;
        try {
            result = PinyinHelper.getShortPinyin(str);
        } catch (PinyinException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}
