package com.jzg.framework.utils.number;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数字格式化工具类
 */
public class NumberFormatUtils {

	/**
	 * 格式化工具集合
	 */
	private static final Map<String, NumberFormat> fmtMap = new HashMap<String, NumberFormat>();

	/**
	 * @param format
	 *            格式表达式
	 * @return 数字格式化工具
	 */
	private static NumberFormat getFormat(String format) {
		NumberFormat fmt = fmtMap.get(format);
		if (fmt == null) {
			fmt = new DecimalFormat(format);
			fmtMap.put(format, fmt);
		}
		return fmt;
	}

	/**
	 * 格式化
	 * 
	 * @param obj
	 *            对象
	 * @param format
	 *            格式表达式
	 * @return 数字字符串
	 */
	public static String format(Object obj, String format) {
		return getFormat(format).format(obj);
	}

	/**
	 * 格式化
	 * 
	 * @param number
	 *            数字
	 * @param format
	 *            格式表达式
	 * @return 数字字符串
	 */
	public static String format(double number, String format) {
		return getFormat(format).format(number);
	}

	/**
	 * 格式化
	 * 
	 * @param number
	 *            数字
	 * @param format
	 *            格式表达式
	 * @return 数字字符串
	 */
	public static String format(long number, String format) {
		return getFormat(format).format(number);
	}

	/**
	 * 解析
	 * 
	 * @param source
	 *            数字字符串
	 * @param format
	 *            格式表达式
	 * @return 数字
	 * @throws ParseException
	 */
	public static Number parse(String source, String format) throws ParseException {
		return getFormat(format).parse(source);
	}

}
