package com.jzg.framework.utils.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类.
 */
public final class HtmlUtils {

    /**
     * logger.
     */
    private static Logger logger = LoggerFactory.getLogger(HtmlUtils.class);

    /**
     * 私有构造方法,将该工具类设为单例模式.
     */
    private HtmlUtils() {
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为3位 ，当字符串为空时返回0.
     *
     * @param str 字符串 .
     * @return 字符串的长度 .
     */
    public static int strLengthCn(String str) {
        if (StringUtils.isEmpty(str)) {
            return 0;
        }
        int valueLength = 0;
        final String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int num = 0; num < str.length(); num++) {
            /* 获取一个字符 */
            final String temp = str.substring(num, num + 1);
			/* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
				/* 中文字符长度为3 */
                valueLength += 3;
            } else {
				/* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 去除html代码(HTML过滤还可以使用jsoup工具包进行处理).
     *
     * @param inputString 含html标签的字符串 .
     * @return 文本字符串 .
     */
    public static String htmlToText(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        try {
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String patternStr = "\\s+";
            // 过滤script标签
            Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            Matcher m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll("");
            // 过滤style标签
            Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            Matcher m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll("");
            // 过滤html标签
            Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            Matcher m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll("");
            // 过滤空格
            Pattern p_ba = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
            Matcher m_ba = p_ba.matcher(htmlStr);
            htmlStr = m_ba.replaceAll("");
        } catch (Exception e) {
            logger.error("=== HtmlToText exception: " + e.getMessage(), e);
        }
        return htmlStr; // 返回文本字符串
    }
}
