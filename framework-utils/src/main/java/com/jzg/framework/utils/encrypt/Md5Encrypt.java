package com.jzg.framework.utils.encrypt;

import java.security.MessageDigest;

/**
 * @description:
 * @author: JZG
 * @date: 2016/12/1 19:46
 */
public class Md5Encrypt {
    private static char[] HEXCHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * MD5加密
     * @param source  待加密字符串
     * @param charset UTF-8
     * @return
     */
    public static byte[] encrypt(String source, String charset) {
        try {
            byte[] res = source.getBytes(charset);
            MessageDigest mdTemp = MessageDigest.getInstance("MD5".toUpperCase());
            mdTemp.update(res);
            byte[] hash = mdTemp.digest();
            return hash;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 转化为两位16进制
     * @param b
     * @return
     */
    public static String toHexString(byte[] b) {

        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEXCHAR[((b[i] & 0xF0) >>> 4)]);
            sb.append(HEXCHAR[(b[i] & 0xF)]);
        }
        return sb.toString();
    }

    /**
     * 转化为两位16进制  大写
     * @param b
     * @return
     */
    public static String toHexStringUpper(byte[] b) {

        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEXCHAR[((b[i] & 0xF0) >>> 4)]);
            sb.append(HEXCHAR[(b[i] & 0xF)]);
        }
        return sb.toString().toUpperCase();
    }

}
